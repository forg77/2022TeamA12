from sentence_transformers import SentenceTransformer , util
import torch
import numpy
BERT_PATH = "/home/chenyuchong/NLP-Series-sentence-embeddings/output/sts-sbert-macbert-64-2022-03-05_10-23-37"
ARRAY_PATH = "array.npy"
TEXT_PATH = "news.txt"
vec_array = numpy.load(ARRAY_PATH)
print("finished loading vectors")
device = torch.device('cpu')
model = SentenceTransformer(BERT_PATH,device=device)
text_array = []
with open(TEXT_PATH,'r+',encoding='utf=8') as reading:
    while True:
        line = reading.readline()
        if not line:
            break
        else:
            text_array.append(line)

print("finished reading")
text_len = len(text_array)
while True:
    print("input your question")
    search_str = str(input())
    sentence_len = len(search_str)
    print("length of sentence is ",sentence_len)
    new_str = ""
    if sentence_len > 250:
        new_str += search_str[:125]
        new_str += search_str[-125:]
        search_str = new_str
    vector = model.encode(search_str)
    print("finished encoding")
    p = -1
    maxcosine = 0
    sim_array = []
    count = 0
    for vecs in vec_array:
        cosine_score = util.cos_sim(vecs,vector)
        sim_array.append((cosine_score,count))
        count += 1
    sim_array.sort(reverse=True)
    count = 1
    for items in sim_array:
        if count >= 5:
            break
        print(count,items[0],text_array[items[1]])
        count += 1

