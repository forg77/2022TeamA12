import re
with open('rus_urk_news.txt','r+',encoding='utf-8') as reading:
    solvestr = str(reading.read())
    solvestr = solvestr.replace(" ","")
    #solvestr = solvestr.replace("\n\n","\n")
    solvestr = re.sub(r'\n\n',r'\n',solvestr)
    solvestr = re.sub(r'\u3010\S+\u3011',r'',solvestr)
    solvestr = solvestr.replace("【","")
    solvestr = solvestr.replace("（央视新闻客户端）","")
    solvestr = solvestr.replace("（央视新闻）", "")
    solvestr = solvestr.replace("（央视）", "")
    solvestr = solvestr.replace("\x6536\x8D77","")
    solvestr = re.sub(r'\n\n', r'\n', solvestr)

    with open('news.txt','w',encoding='utf-8') as writing:
        writing.write(solvestr)