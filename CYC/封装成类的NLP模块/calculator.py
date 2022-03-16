import socket
import time

HOST = '121.5.47.4'
PORT = 38324
FILE_PATH = 'query.json'
ANS_PATH = 'ans.txt'
s = socket.socket()
s.connect((HOST, PORT))
while True:
    time.sleep(1)
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
    else:
        with open(FILE_PATH, 'w+', encoding='utf-8') as writing:
            now = time.time()
            writing.write(str(now)+'\n')
            writing.write(k)



