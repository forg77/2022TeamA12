import re

file = open('123.txt',encoding='utf8')
fstr = file.read()
flen = len(fstr)
sentence = []
'''
按。！？切成句子
再消除掉，、（）《》；[数字]
'''
sentence_cut = ['。','！','？']

lst = 0
cur = 0
#分句
for chars in fstr:
    for checks in sentence_cut:
        if chars == checks:
            sentence.append(fstr[lst:cur])
            lst = cur + 1
    cur += 1
#去除符号
for items in sentence:
    items = re.sub(r'[，“”,。！!?？…()（）、《》:\n ]+',r'',items)
    items = re.sub(r'\[+[0-9]*\]+',r'',items)#这个是用来删除索引的


