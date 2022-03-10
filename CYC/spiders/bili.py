import re
from  selenium import webdriver
from bs4 import BeautifulSoup
import time
browser =webdriver.Chrome()
FILE_DIR = "rus_urk_news.txt"
class rus_ukr_spider:
    def __init__(self):
        self.TOPICURL = "https://www.bilibili.com/v/topic/detail?topic_id=11363"
        #选用的b站
        self.headers = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36"}

    def run(self):
        browser.get(self.TOPICURL)
        for i in range(1,350):
            browser.execute_script('window.scrollTo(0, document.body.scrollHeight)')
            time.sleep(1)
        #response = requests.get(self.TOPICURL,headers=self.headers)
        html_str = BeautifulSoup(browser.page_source,"lxml")
        html_str = str(html_str)
        #print(html_str)
        count = 0
        wanted_str = re.compile(r'div class="bili-rich-text__content folded line--6"><span>[^<]*</span>')
        get_str = re.findall(wanted_str,html_str)
        for item in get_str:
            item = re.sub(r'div class="bili-rich-text__content folded line--6"><span>','',item)
            item = re.sub(r'</span>','',item)
            item = item.replace("\n","")
            item = item.strip()
            writing.write(item)
            writing.write("\n")
            count += 1
        print(count)
if __name__ == '__main__':
    spd = rus_ukr_spider()
    with open(FILE_DIR,"w+",encoding='utf-8') as writing:
        spd.run()
