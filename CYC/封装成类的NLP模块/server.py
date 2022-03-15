#121.5.47.4
import socket

HOST = '0.0.0.0'
PORT = 38324
FILE_PATH = 'query.txt'
s = socket.socket()
s.bind((HOST,PORT))

while True:
    s.listen()
    conn, addr = s.accept()
    print(addr)
    while True:
        input_str = str(input())
        sent =str(input()).encode(encoding='utf-8')
        conn.sendall(sent)
        if input_str == 'done':
            break
    ans = conn.recv(1024).decode(encoding='utf-8')
    print(ans)