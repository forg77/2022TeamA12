from datasets.bert_dataset import BertDataset
from models.modeling_glycebert import GlyceBertModel
from  sklearn.metrics.pairwise import cosine_similarity
import torch
import numpy
from math import e
CHINESEBERT_PATH = "ChineseBERT-base"
tokenizer = BertDataset(CHINESEBERT_PATH)
chinese_bert = GlyceBertModel.from_pretrained(CHINESEBERT_PATH)

sentence1 = '我喜欢猫'
sentence2 = '我也喜欢'
sentence3 = '我喜欢狗'#看了vocabulary.txt 发现里面有假名 这个权当是个对照组了
long_sen1 = '中国特色社会主义是科学社会主义的基本原则与中国实际相结合的产物，具有鲜明的时代特征和中国特色。中国共产党领导是中国特色社会主义最本质的特征。'
long_sen2 = '中国特色社会主义道路，是在中国共产党的领导下，立足基本国情，以经济建设为中心，坚持四项基本原则，坚持改革开放，解放和发展社会生产力，建设中国特色社会主义市场经济、社会主义民主政治、社会主义先进文化、社会主义和谐社会、社会主义生态文明，促进人的全面发展，逐步实现全体人民共同富裕，建设富强、民主、文明、和谐、美丽的社会主义现代化强国。中国特色社会主义道路，是党和人民100年奋斗、创造、积累的根本成就，是引领中国进步、增进人民福祉、实现民族复兴的康庄大道。'

def sentence2vec(sentence):
    input_ids, pinyin_ids = tokenizer.tokenize_sentence(sentence)
    length = input_ids.shape[0]
    input_ids = input_ids.view(1, length)
    pinyin_ids = pinyin_ids.view(1, length, 8)
    output_hidden = chinese_bert.forward(input_ids, pinyin_ids)[0]
    cls = output_hidden[0][0]
    #cls = cls.unsqueeze(0)
    #print(output_hidden)
    num = output_hidden.shape[1] - 1
    #output_hidden = output_hidden.squeeze(0)
    output_hidden = torch.sum(output_hidden ,1)
    output_hidden = output_hidden - cls
    output_hidden = output_hidden/num

    return output_hidden


PARAMETER_MUL = 5.
if __name__ == '__main__':

    #problemID = getProb() #获得题号
    #AnsStr1 , AnsStr2 = getAns(problemID) #用于对照的两个答案
    AnsStr1 = sentence1
    AnsStr2 = sentence1
    optAnS1 = sentence2vec(AnsStr1).detach().numpy()
    optAnS2 = sentence2vec(AnsStr2).detach().numpy()
    AnsSim1 = cosine_similarity(optAnS1,optAnS2) #对照相似度

    #StudentS = getStuAns()
    StudentS = sentence2
    optStudentS = sentence2vec(StudentS).detach().numpy()
    StuAnsSim = max(cosine_similarity(optAnS1,optStudentS),cosine_similarity(optAnS2,optStudentS))
    SimDis = AnsSim1 - StuAnsSim

    if SimDis >= 0.1:
        SimSentiment = 0
    else:
        SimSentiment =  1/((1+e**((100*SimDis-5)))) *100.
    print(SimSentiment)