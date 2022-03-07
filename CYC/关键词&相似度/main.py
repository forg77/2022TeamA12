import gensim
import numpy as np
import jieba
import networkx as nx
from  sklearn.metrics.pairwise import cosine_similarity
import re
#使用维基百科语料库训练
'''
大致过程：清洗数据 
        jieba分词 
        CBOW模型 维度100 窗口5
        简单试了几个词的距离 效果只能说一般
'''
FILE_PATH = 'wikipedia_zh_word2vec.bin'

model = gensim.models.KeyedVectors.load_word2vec_format(FILE_PATH,binary=True)


file = open('123.txt', encoding='utf8')
fstr = file.read()
flen = len(fstr)
sentence = []
'''
按。！？切成句子
再消除掉，、（）《》；[数字]
'''
sentence_cut = ['。', '！', '？']
lst = 0
cur = 0
# 分句
for chars in fstr:
    for checks in sentence_cut:
        if chars == checks:
            sentence.append(fstr[lst:cur])
            lst = cur + 1
    cur += 1
# 去除符号
sentences = []
for items in sentence:
    items = re.sub(r'[，,。！!?？…()（）、《》:\n ]+', r'', items)
    items = re.sub(r'\[+[0-9]*\]+', r'', items)  # 这个是用来删除索引的
    sentences.append(items)
#embedding

word_embedding = {}
count = 0
for item in model.key_to_index:
    word_embedding[item] = model[item]
    count += 1
print(count,"words are included as embedding")

#WordAVG '句向量'
sentence_vec = [] #句向量
sentencec = []#切词
for item in sentences:
    sentencec.append(jieba.cut(item,cut_all=True))

for item in sentencec:
    vec0 = np.zeros(100,)
    cnt0 = 0
    for words in item:
        vec0 = np.add(vec0,word_embedding.get(words,np.zeros(100,)))
        cnt0 += 1
    vec0 = np.divide(vec0,cnt0)
    sentence_vec.append(vec0)
sim_mat = np.zeros((len(sentence_vec),len(sentence_vec)))


for i in range(0,len(sentence_vec)):
    for j in range(0,len(sentence_vec)):
        if i != j:
            sim_mat[i][j] = cosine_similarity(sentence_vec[i].reshape(1,100),sentence_vec[j].reshape(1,100))

nx_graph = nx.from_numpy_array(sim_mat)
scores = nx.pagerank(nx_graph)

sort_sentence = sorted(((scores[i],s) for i,s in enumerate(sentences)), reverse=True)

print(sort_sentence[0])


