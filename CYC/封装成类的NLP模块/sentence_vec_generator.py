from sentence_transformers import SentenceTransformer , util
sentence_list = []
import torch
import numpy
BERT_PATH = "/home/chenyuchong/NLP-Series-sentence-embeddings/output/sts-sbert-macbert-64-2022-03-05_10-23-37"
ARRAY_PATH = "array.npy"
VECTORIZE_PATH = 'news.txt'
device = torch.device('cpu')
model = SentenceTransformer(BERT_PATH,device=device)
with open(VECTORIZE_PATH,'r+',encoding='utf-8') as reading:
    while True:
        line = reading.readline()
        if line:
            sentence_list.append(line)

        else:
            break
list_range = len(sentence_list)
for i in range(0,list_range):
    sentence_len = len(sentence_list[i])
    new_str = ""
    #print(i)
    if sentence_len > 250:
        new_str += sentence_list[i][:125]
        new_str += sentence_list[i][-125:]
        sentence_list[i] = new_str
embeddings =  model.encode(sentence_list,convert_to_numpy=True)
numpy.save(ARRAY_PATH,embeddings)








