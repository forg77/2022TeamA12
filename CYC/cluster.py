import gensim
import numpy as np
import jieba
from sklearn.cluster import KMeans
import re
def main_tops(ans_file_path,top_number):
    FILE_PATH = 'wikipedia_zh_word2vec.bin'
    model = gensim.models.KeyedVectors.load_word2vec_format(FILE_PATH,binary=True)
    file = open(ans_file_path, encoding='utf8')

    fstr = file.read()
    flen = len(fstr)
    sentence = []
    OMP_NUM_THREADS=3
    stopwords = [line.strip() for line in open('stopword.txt',encoding='utf-8').readlines()]
    '''
    按。！？切成句子
    再消除掉，、（）《》；[数字]
    '''
    sentence_cut = ['。', '！', '？']
    lst = 0
    cur = 0
    # 分句
    word_embedding = {}

    for item in model.key_to_index:
        word_embedding[item] = model[item]


    for chars in fstr:
        for checks in sentence_cut:
            if chars == checks:
                sentence.append(fstr[lst:cur])
                lst = cur + 1
        cur += 1
    # 去除符号
    sentences = []
    words = {} #词号
    sdrow = {}
    word_graph = {}#词间的有向图
    Iposition = {}
    for items in sentence:
        items = re.sub(r'[，,。！!?？…()（）、《》·:\n ]+', r'', items)
        items = re.sub(r'\[+[0-9]*\]+', r'', items)  # 这个是用来删除索引的
        sentences.append(items)
    wordno = 0
    for items in sentences:
        items = jieba.cut(items)
        pre = 'null'
        for word in items:
            if word in words:
                pass
            else:
                words[word] = wordno
                sdrow[wordno] = word
                wordno += 1
                Iposition[word] = 1
            if pre != 'null':
                if pre in word_graph:
                    word_graph[pre].append(word)
                else:
                    word_graph[pre] = []
                    word_graph[pre].append(word)
            pre = word
    #去重
    for word in words:
        if word in word_graph:
            word_graph[word] = list(set(word_graph[word]))



    wordvecl = []
    worddict = {}
    for word in words:
        if word not in worddict:
            wordvecl.append(word_embedding.get(word,np.zeros(100,)).astype(float))
            worddict[word] = wordvecl[-1]
    '''
    #绘图
    distances = []
    for i in range(1,20):
        kmeans_model = KMeans(n_clusters=i)
        kmeans_model.fit(wordvecl)
        clusters = kmeans_model.cluster_centers_
        distance = 0
        for word in words:
            predict_cluster = clusters[kmeans_model.predict(worddict[word].reshape((1,100)))]
    
            distance += np.linalg.norm(worddict[word]-predict_cluster)
        distances.append(distance)
    
    x1 = range(1,20)
    plt.plot(x1,distances)
    plt.show()
    '''
    kmeans_model = KMeans(n_clusters=10)
    kmeans_model.fit(wordvecl)
    #计算 VoteWeight
    VoteWeight = {}
    SumClusterDistance = {} #簇号 距离和
    SumClusterDegree = {} #簇号 度数
    clusters = kmeans_model.cluster_centers_
    for word in words:
        predict = kmeans_model.predict(worddict[word].reshape((1,100)))
        predict = predict[0]
        if predict not in SumClusterDegree:
            SumClusterDegree[predict] = 1
        else:
            SumClusterDegree[predict] += 1
        predict_cluster = clusters[predict]
        if predict not in SumClusterDistance:
            SumClusterDistance[predict] = np.linalg.norm(worddict[word]-predict_cluster)
        else:
            SumClusterDistance[predict] += np.linalg.norm(worddict[word] - predict_cluster)
    for word in words:
        predict = kmeans_model.predict(worddict[word].reshape((1,100)))
        predict = predict[0]
        predict_cluster = clusters[predict]
        VoteWeight[word] =  SumClusterDegree[predict]*np.linalg.norm(worddict[word]-predict_cluster)/SumClusterDistance[predict]

    P_cluster = {} #.get 无键默认0
    for word1 in words:
        if word1 not in word_graph:
            continue
        VoteWeightSum = 0
        for word2 in word_graph[word1]:
            VoteWeightSum += VoteWeight[word2]
        for word2 in word_graph[word1]:
            P_cluster[(word1,word2)] = VoteWeight[word2] / VoteWeightSum

    P_cov = {} #.get 无键默认0
    for word1 in words:
        if word1 not in word_graph:
            continue
        listlen = len(word_graph[word1])
        for word2 in word_graph[word1]:
            P_cov[(word1,word2)] = 1.0/listlen

    P_loc = {} #.get 无键默认0
    # 位置影响力 在标题出现 I = 30 否则 I = 1 P=I/sum(I) 标题的处理之后再做
    for word1 in words:
        if word1 not in word_graph:
            continue
        sum2 = 0
        for word2 in word_graph[word1]:
            sum2 += Iposition[word2]
        for word2 in word_graph[word1]:
            P_loc[(word1,word2)] = Iposition[word2]/sum2

    alpha = 0.2 #P_cov权重
    beta = 0.2 #P_loc权重
    delta = 0.6 #P_cluster权重
    D = 0.85 #系数
    matrix_size = wordno
    M = np.zeros((matrix_size,matrix_size))
    for (word1,word2) in P_cluster:
        no1 = words[word1]
        no2 = words[word2]
        M[no1][no2] = alpha*P_cov[(word1,word2)] + beta*P_loc[(word1,word2)] + delta*P_cluster[(word1,word2)]

    eps = 1e-10
    e = np.ones((1,matrix_size))/matrix_size
    B = np.ones((1,matrix_size))/matrix_size
    STOPFLAG = True
    while STOPFLAG:
        B1 = D* np.matmul(B,M) + (1-D)*e
        check = np.linalg.norm(B-B1)
        if(check <= eps):
            STOPFLAG = False
        B = B1
    sortk = []
    nok = 0
    B0 = B[0]
    for nums in B0:
        sortk.append((nums,nok))
        nok += 1
    sortk.sort(reverse=True)
    cnt = 0
    top_list = []
    for i in sortk:
        if sdrow[i[1]] not in stopwords:
            top_list.append(sdrow[i[1]])
            if cnt > top_number:
                break
            cnt += 1
    return top_list