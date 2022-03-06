from transformers import BertTokenizer,BertModel
from  sklearn.metrics.pairwise import cosine_similarity
import torch
import numpy
import sys
from math import e
import gensim
import cluster

'''
sentence1 = '我喜欢猫'
sentence2 = '我也喜欢猫'
'''
long_sen1 = '又是很困的一天。'#标准答案
long_sen2 = '今天又是很困。'#作答
class SimCheck():
    def __init__(self,):
        self.WORD2VEC_PATH = 'wikipedia_zh_word2vec.bin'
        self.BERT_PATH = "/home/chenyuchong/NLP-Series-sentence-embeddings/output/sts-sbert-macbert-64-2022-03-02_18-05-52"
        self.tokenizer = BertTokenizer.from_pretrained(self.BERT_PATH)
        self.model = BertModel.from_pretrained(self.BERT_PATH)
        self.model_word2vec = gensim.models.KeyedVectors.load_word2vec_format(self.WORD2VEC_PATH, binary=True)
        self.KEYWORD_OUTPUT = 4
    def sentence2vec(self,sentence):

        input_ids = self.tokenizer.encode(sentence)
        output = self.model(torch.tensor([input_ids]))
        embeddings = output.last_hidden_state
        sum = torch.sum(embeddings , 1)
        num = embeddings.shape[1]
        sum = sum/num
        return sum




    def Bert_similarity_check(self,long_sen1,long_sen2):
        #problemID = getProb() #获得题号
        #AnsStr1 , AnsStr2 = getAns(problemID) #用于对照的两个答案
        if long_sen1[-4:] == '.txt':
            with open(long_sen1, encoding='utf-8') as f:
                long_sen1 = f.read()
        if long_sen2[-4:] == '.txt':
            with open(long_sen2, encoding='utf-8') as f:
                long_sen2 = f.read()
        AnsStr1 = long_sen1
        optAnS1 = self.sentence2vec(AnsStr1).detach().numpy()
        #StudentS = getStuAns()
        StudentS = long_sen2
        optStudentS = self.sentence2vec(StudentS).detach().numpy()
        bert_sentence_sim = cosine_similarity(optAnS1,optStudentS)
        print("bert similarity",bert_sentence_sim)
    def Word2vec_similarity_check(self,DIRSTR1,DIRSTR2,KEYWORD_OUTPUT):
        ans_key = cluster.main_tops(DIRSTR1, KEYWORD_OUTPUT)
        stu_key = cluster.main_tops(DIRSTR2, KEYWORD_OUTPUT)
        key_word_distance = self.model_word2vec.wmdistance(ans_key, stu_key)
        wor2vec_sim = 1. / (1.0 + key_word_distance)
        print("student's keyword", stu_key)
        print("word2vec similarity", wor2vec_sim)

