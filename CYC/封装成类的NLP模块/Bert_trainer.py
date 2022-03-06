import logging
import math
import random
from datetime import datetime

import torch
from data.dataset import load_STS_data, load_snli_jsonl
from sentence_transformers import InputExample, SentenceTransformer, LoggingHandler
from sentence_transformers import losses
from sentence_transformers.evaluation import EmbeddingSimilarityEvaluator, SimilarityFunction
from sentence_transformers.losses import TripletDistanceMetric
from torch.utils.data import DataLoader

#### Just some code to print debug information to stdout
logging.basicConfig(format='%(asctime)s - %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S',
                    level=logging.INFO,
                    handlers=[LoggingHandler()])

# 训练参数
model_name = '/home/chenyuchong/NLP-Series-sentence-embeddings/chinese_macbert_base'
train_batch_size = 64
num_epochs = 5
max_seq_length = 64
device = torch.device('cpu')
train_dataset = "sts"  # 选择有监督训练的数据集

# 模型保存路径
model_save_path = '/home/chenyuchong/NLP-Series-sentence-embeddings/output/{}-sbert-{}-{}-{}'.format(train_dataset,
                                                                                                 "macbert",
                                                                                                 train_batch_size,
                                                                                                 datetime.now().strftime(
                                                                                                     "%Y-%m-%d_%H-%M-%S"))

# 建立模型
model = SentenceTransformer(model_name, device=device)
model.__setattr__("max_seq_length", max_seq_length)

# 准备训练集

sts_vocab = load_STS_data("/home/chenyuchong/NLP-Series-sentence-embeddings/STS-B/cnsd-sts-train.txt")
sample_sum = []
train_samples = []
total_num = 0
for data in sts_vocab:
    sample_sum.append(InputExample(texts=[data[0], data[1]], label=data[2] / 5.0))
    total_num += 1
sample_sum = random.shuffle(sample_sum)
for_dev_data = int(total_num * 0.3)
for_test_data = int(total_num * 0.1) + for_dev_data
for_train_data = total_num - for_test_data
dev_samples = []
test_samples = []
count = 0
# 准备验证集和测试集 训练6 验证3 测试1
for data in sample_sum:
    if count < for_dev_data:
        dev_samples.append(data)
    elif count < for_test_data:
        test_samples.append(data)
    else:
        train_samples.append(data)
    count += 1

# 初始化评估器
dev_evaluator = EmbeddingSimilarityEvaluator.from_input_examples(dev_samples, batch_size=train_batch_size,
                                                                 name='sts-dev',
                                                                 main_similarity=SimilarityFunction.COSINE)
test_evaluator = EmbeddingSimilarityEvaluator.from_input_examples(test_samples, batch_size=train_batch_size,
                                                                  name='sts-test',
                                                                  main_similarity=SimilarityFunction.COSINE)

# We train our model using the MultipleNegativesRankingLoss
train_dataloader = DataLoader(train_samples, shuffle=True, batch_size=train_batch_size)
train_loss = losses.CosineSimilarityLoss(model)
warmup_steps = math.ceil(len(train_dataloader) * num_epochs * 0.1)  # 10% of train data for warm-up
evaluation_steps = int(len(train_dataloader) * 0.1)  # Evaluate every 10% of the data
logging.info("Training sentences: {}".format(len(train_samples)))
logging.info("Warmup-steps: {}".format(warmup_steps))
logging.info("Performance before training")
dev_evaluator(model)

# 模型训练

model.fit(train_objectives=[(train_dataloader, train_loss)],
          evaluator=dev_evaluator,
          epochs=num_epochs,
          evaluation_steps=evaluation_steps,
          warmup_steps=warmup_steps,
          show_progress_bar=False,
          output_path=model_save_path,
          optimizer_params={'lr': 2e-5},
          use_amp=False  # Set to True, if your GPU supports FP16 cores
          )

# 测试集上的表现
model = SentenceTransformer(model_save_path)
test_evaluator(model, output_path=model_save_path)