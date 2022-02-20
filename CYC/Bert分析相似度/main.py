from datasets.bert_dataset import BertDataset
from models.modeling_glycebert import GlyceBertModel
from  sklearn.metrics.pairwise import cosine_similarity
import torch
import numpy
CHINESEBERT_PATH = "ChineseBERT-base"
tokenizer = BertDataset(CHINESEBERT_PATH)
chinese_bert = GlyceBertModel.from_pretrained(CHINESEBERT_PATH)

sentence1 = '我喜欢猫'
sentence2 = '我也喜欢猫'
sentence3 = '我喜欢狗'#看了vocabulary.txt 发现里面有假名 这个权当是个对照组了
long_sen1 = '必须从对立中把握同一，在同一中把握对立，要用联系的，发展的，全面的观点，特别是用矛盾的观点看问题。深刻认识矛盾双方的对立和同一两方面的关系，二者是不同性质的关系，但又是基于共同本质的。既看到矛盾双方的对立，又看到统一和转化，只有这样才能真正把握住矛盾，把握住事物的发展。'
long_sen2 = '当地时间2月17日至18日，乌东居民区遭到乌政府军炮弹攻击。乌东民间武装力量19日发布总动员令，要求男性加入武装部队，进攻可能在几天内打响。'

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
    AnsStr1 = long_sen1
    AnsStr2 = long_sen1
    optAnS1 = sentence2vec(AnsStr1).detach().numpy()
    optAnS2 = sentence2vec(AnsStr2).detach().numpy()
    AnsSim1 = cosine_similarity(optAnS1,optAnS2) #对照相似度

    #StudentS = getStuAns()
    StudentS = long_sen2
    optStudentS = sentence2vec(StudentS).detach().numpy()
    StuAnsSim = max(cosine_similarity(optAnS1,optStudentS),cosine_similarity(optAnS2,optStudentS))
    SimDis = AnsSim1 - StuAnsSim

    if SimDis >= 0.1:
        SimSentiment = 0
    else:
        SimSentiment = (1.-SimDis)*100.
    print(SimSentiment)