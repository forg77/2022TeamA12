import socket
import time

HOST = '121.5.47.4'
PORT = 38324
FILE_PATH = 'query.txt'
ANS_PATH = 'ans.txt'
s = socket.socket()
s.connect((HOST, PORT))
while True:
    k = s.recv(1024).decode(encoding='utf-8')
    print(k)
    if k == 'done':
        with open(ANS_PATH,'r+',encoding='utf-8') as reading:
            rdstr = reading.read().encode('utf-8')
            s.sendall(rdstr)
    else:
        with open(FILE_PATH, 'a+', encoding='utf-8') as writing:
            k = k + "\n"
            writing.write(k)



