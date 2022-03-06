from data.dataset import load_STS_data
from  SimCheck import  SimCheck


query1 = SimCheck()
query1.Word2vec_similarity_check('test.txt','test2.txt',4)
query1.Bert_similarity_check('test.txt', 'test2.txt')
query1.Bert_similarity_check('test.txt', 'test2.txt')
query1.Bert_similarity_check('test.txt', 'test2.txt')
query1.Bert_similarity_check('test.txt', 'test2.txt')
query1.Word2vec_similarity_check('test.txt','test2.txt',4)
#给定一个系数W_1 令W_2 = 1 -W_1 根据实际的分数可以求解出对应的W_1 W_2
#为取得最小的误差期望值 将会对对 W_1求平均值 令其为下次评分的权重