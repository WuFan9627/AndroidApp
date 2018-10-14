from socket import socket
ip = '127.0.0.1'
port1 = 10001
filePath = '/Users/wufan/Desktop/tweets.json' 

sock1 = socket()
sock1.connect((ip, port1))

with open(filePath) as inputData:
    for line in inputData:
        sock1.sendall(line.encode())
    sock1.close
