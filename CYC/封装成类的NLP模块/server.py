#121.5.47.4
import socket

HOST = '0.0.0.0'
PORT = 38324
FILE_PATH = 'query.txt'
s = socket.socket()
s.bind((HOST,PORT))
FINISH = 'done'
QUERY = 'qery'
ADD_ELEMENT = 'adel'
DELETE_ELEMENT = 'dele'

while True:
    s.listen()
    conn, addr = s.accept()
    print(addr)
    while True:
        input_str = str(input())
        sent = input_str.encode(encoding='utf-8')
        conn.sendall(sent)
        if input_str == 'done':
            break
    #print(conn.recv())
    ans = conn.recv(4096).decode(encoding='utf-8')
    print(ans)
