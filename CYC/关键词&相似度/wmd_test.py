import gensim
import cluster
FILE_PATH = 'wikipedia_zh_word2vec.bin'
model = gensim.models.KeyedVectors.load_word2vec_format(FILE_PATH,binary=True)
ans = cluster.main_tops('ans.txt',10)
#ans.sort()
print(ans)

txt123 = cluster.main_tops('123.txt',10)
#txt123.sort()
wdistance = model.wmdistance(ans,txt123)
sim = 1.0/(1.0+wdistance)
print(sim)
#print(ans)
print(txt123)

