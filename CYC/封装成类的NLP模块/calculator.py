import json
import socket
import time

HOST = '121.5.47.4'
PORT = 38324
FILE_PATH = 'query.json'
ANS_PATH = 'ans.txt'
BANNED_PATH = 'banned_list.txt'
VECTORIZE_PATH ='news.txt'
SENTENCE_MAP_PATH ='map.txt'
s = socket.socket()
s.connect((HOST, PORT))
while True:
    time.sleep(1)
    #print(s.recv())
    k = s.recv(4096).decode(encoding='utf-8')
    print(k)
    if k == 'done':
        with open(ANS_PATH,'r+',encoding='utf-8') as reading:
            rdstr = reading.read()
            rdstr = '[' + rdstr
            rdstr = rdstr[:-1]
            rdstr = rdstr + ']'
            print(rdstr)
            rdstr = rdstr.encode(encoding='utf-8')
            s.sendall(rdstr)
    elif k[:4] == 'qery':
        with open(FILE_PATH, 'w+', encoding='utf-8') as writing:
            now = time.time()
            writing.write(str(now)+'\n')
            writing.write(k[5:])
    elif k[:4] == 'dele':
        with open(BANNED_PATH,'a+',encoding='utf-8') as writing:
            djson = json.loads(k[5:])
            dlist = djson['content']
            dlist = dlist.split(',')
            for items in dlist:
                writing.write(items)
                writing.write('\n')

    elif k[:4] == 'adel':
        ajson = json.loads(k[5:])
        with open(VECTORIZE_PATH,'a+',encoding='utf-8') as writing:
            acontent = ajson['content']
            writing.write(acontent)
            writing.write('\n')
        with open(SENTENCE_MAP_PATH,'a+',encoding='utf-8') as writing:
            aID = ajson['id']
            writing.write(aID)
            writing.write('\n')








