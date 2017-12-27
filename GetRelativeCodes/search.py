import requests
import re
import configparser
import os
import sys
import chardet
import time
import datetime
import json
from threading import Thread
from queue import Queue


class MyThread(Thread):
    def __init__(self, func, args=()):
        super(MyThread, self).__init__()
        self.func = func
        self.args = args

    def run(self):
        self.result = self.func(*self.args)

    def get_result(self):
        try:
            return self.result
        except Exception:
            return None


os.chdir(sys.path[0])
cp = configparser.SafeConfigParser()
cp.read('configure.conf')
trytime = int(cp.get('my', 'trytime'))
trypage = int(cp.get('my', 'trypage'))
minlen = int(cp.get('my', 'minlen'))
maxlen = int(cp.get('my', 'maxlen'))
classlim = int(cp.get('my', 'classlim'))
functionlim = int(cp.get('my', 'functionlim'))
username = cp.get('my', 'username')
password = cp.get('my', 'password')
threadnum = int(cp.get('my', 'threads'))


def login() -> object:
    url = 'https://github.com/login'
    head = {
        "user-agent": "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36",
    }
    s = requests.session()
    r = s.get(url, headers=head)
    inside = r.content
    inside = inside.decode(chardet.detect(inside)['encoding'])
    token = re.findall('<input name="authenticity_token" type="hidden" value="(.*?)" />', inside, re.S)
    payload = {
        'commit': 'Sign in',
        'utf8': '%E2%9C%93',
        'authenticity_token': token[0],
        'login': username,
        'password': password
    }
    s.post('https://github.com/session', headers=head, data=payload)
    return s


def parse_date_time(time_string):
    return datetime.datetime.strptime(time_string, "%Y-%m-%dT%H:%M:%SZ")


def get_create_time(s, source_name):
    uri = "https://api.github.com/search/repositories?q=" + source_name
    html = ""
    for i in range(10):
        html = s.get(uri).text
        if html.find("But here's the good news") != -1:
            html = ""
            time.sleep(1)
            continue
    if len(html) == 0:
        print("failed")
        return [False, ""]
    result_list = json.loads(html)
    print(html)
    return [True, parse_date_time(result_list["items"][0]["created_at"])]


def checksearch(html):
    # print(chardet.detect(html))
    try:
        html = html.decode(chardet.detect(html)['encoding'])
    except:
        print("Decode failed")
        return False
    pattern = "We could not perform this search|Whoa"
    check = re.compile(pattern)
    result = re.findall(check, html)
    if len(result) == 0:
        return True
    else:
        return False


def gethtmlpre(s, url):
    for t in range(trytime):
        page = s.get(url)
        html = page.content
        if checksearch(html):
            return html
    return ""


def gethtml(s, url):
    for t in range(trytime):
        page = s.get(url)
        html = page.content
        if checksearch(html):
            return html
    raise Exception("Search Failed")


def gethtmleasy(s, url):
    for t in range(trytime):
        page = s.get(url)
        html = page.content
        if checksearch(html):
            return html, True
    return "", False


def getcodelist(s, url):
    try:
        html, flag = gethtmleasy(s, url)
        if len(html) == 0:
            return []
        html = html.decode(chardet.detect(html)['encoding'])
        pattern = r"<a href=\"([\w/\-\.\%]+)\" title=\""
        getmatch = re.compile(pattern)
        result = re.findall(getmatch, html)
        return result
    except:
        time.sleep(5)
        return []


def threadgetlist():
    s = login()
    codelist = []
    while not queue.empty():
        url = queue.get()
        queue.task_done()
        semilist = getcodelist(s, url)
        codelist.extend(semilist)
        print("find " + url + "," + str(len(semilist)))
    return codelist


def getlist(s, url, source_project):
    global queue
    queue = Queue()
    for t in range(trypage):
        queue.put(url + "&p=" + str(t + 1))
    threads = []
    for i in range(threadnum):
        t = MyThread(threadgetlist)
        threads.append(t)
        t.start()
    queue.join()
    codelist = []
    for t in threads:
        t.join()
        t_list = t.get_result()
        for code_url in t_list:
            if source_project not in code_url:
                codelist.append(code_url)
    return codelist


def getnewstr(s):
    return s.replace("/blob", "");


def getcode(s, codename):
    url = "https://raw.githubusercontent.com" + getnewstr(codename)
    html, flag = gethtmleasy(s, url)
    # (chardet.detect(html))
    if flag == False:
        return ""
    html = html.decode(chardet.detect(html)['encoding'])
    return html


def qualify(code, path):
    if (len(code) < minlen or len(code) > maxlen):
        return False
    outfile = open(path + ".java", "w", encoding='utf-8');
    # print(code)
    outfile.write(code)
    outfile.close()
    os.system('java -jar CheckerDemo2.jar ' + path)
    readans = open(path + ".out", "r")
    ans = int(readans.read())
    if ans == 1:
        return True
    else:
        return False


def working():
    s = login()
    codes = []
    badcodes = []
    while not queue.empty():
        code, code_id = queue.get()
        queue.task_done()
        now_code = getcode(s, code)
        if qualify(now_code, "../GetRelativeCodes/qualify/" + str(code_id)):
            codes.append(now_code)
        else:
            badcodes.append(now_code)
    #print(str(len(codes)) + ' ' + str(len(badcodes)))
    return [codes, badcodes]


def getall(codelist):
    global queue
    queue = Queue()
    for i in range(len(codelist)):
        queue.put([codelist[i], i])
    threads = []
    for i in range(threadnum):
        t = MyThread(working)
        t.start()
        threads.append(t)
    queue.join()
    codes = []
    badcodes = []
    for thread in threads:
        thread.join()
        w = thread.get_result()
        codes.extend(w[0])
        badcodes.extend(w[1])
    return [codes, badcodes]
