import re
from  selenium import webdriver
from bs4 import BeautifulSoup
import time
#browser =webdriver.Chrome()
FILE_DIR = "korea_election_news.txt"
SOURCE_DIR = "source.txt"
class rus_ukr_spider:
    def __init__(self):
        self.TOPICURL = "https://www.zhihu.com/question/521064088"
        #选用的b站
        self.headers = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36"}

    def run(self):
        '''
        browser.get(self.TOPICURL)

        for i in range(1,350):
            browser.execute_script('window.scrollTo(0, document.body.scrollHeight)')
            time.sleep(1)

        #response = requests.get(self.TOPICURL,headers=self.headers)
        html_str = BeautifulSoup(browser.page_source,"lxml")
        html_str = str(html_str)
        print(html_str)
        '''
        html_str = ""
        with open(SOURCE_DIR,'r+',encoding='utf-8') as reading:
            html_str = reading.read()
        count = 0
        wanted_str = re.compile(r'<p data-pid=[^<]*</p>')
        get_str = re.findall(wanted_str,html_str)
        print(get_str)
        for item in get_str:
        	#删除句首和句末的前缀
            rmv_head = re.compile(r'<p data-pid=[^<]*>')
            item = re.sub(rmv_head,'',item)
            item = re.sub(r'</p>','',item)
            #虽然删除了换行 但还有些报道的换行没有完全被删除 wonder why
            item = item.replace("\n\n","\n")
            item = item.replace("\n","")
            item = item.strip()
            if len(item) > 5:
                writing.write(item)
                writing.write("\n")
                count += 1
        print(count)

if __name__ == '__main__':
    spd = rus_ukr_spider()
    with open(FILE_DIR,"w+",encoding='utf-8') as writing:
        spd.run()