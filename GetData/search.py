import requests
import re
import configparser
import os
import chardet
import shutil
import json
import time

kMaxSize = 100000  # At most 1e5 files
kMaxLen = 3000


def GetRepositoriesList(start_id, size):
    s = requests.session()
    url = "https://api.github.com/search/repositories?&q=language%3AJava&ref=advsearch&type=Repositories&utf8=%E2%9C%93"
    repositories_list = []
    now = start_id
    while len(repositories_list) < size:
        now += 1
        if_success_load = False
        for i in range(10):
            html = s.get(url + "&page=" + str(now))
            print(html.text)
            if "API rate limit exceeded for 162.105.87.214." in html.text:
                time.sleep(10)
            else:
                if_success_load = True
                break
        if not if_success_load:
            continue
        now_repositories_list = json.loads(html.text)
        for repositories in now_repositories_list["items"]:
            repositories_list.append(repositories)
            if len(repositories_list) == size:
                break;
    return repositories_list


def check_valid_file_name(name):
    pattern = r".java$"
    getmatch = re.compile(pattern)
    result = re.findall(getmatch, name)
    return len(result) > 0


def qualify_code(code, total_numbers, project_name):
    pattern = r"(//)|(/\*)"
    getmatch = re.compile(pattern)
    result = re.findall(getmatch, code)
    if len(result) == 0 or len(code) > kMaxLen:
        return 0
    outfile = open("Check.java", "w");
    outfile.write(code)
    outfile.close()
    os.system('java -jar QueryCodeChecker.jar ' + str(total_numbers) + ' ' + project_name)
    read_result = open("Check.out", "r")
    result = int(read_result.readline())
    read_result.close()
    return result


def check_all_files(path, total_numbers, project_name):
    new_numbers = 0;
    rem_files = 0
    for now_child_files in os.walk(path):
        for files in now_child_files[2]:
            if check_valid_file_name(files):
                rem_files += 1

    for now_child_files in os.walk(path):
        for files in now_child_files[2]:
            if not check_valid_file_name(files):
                continue
            rem_files -= 1
            if rem_files % 100 == 0:
                print("There are " + str(rem_files) + " files remaining")
            try:
                code = open(now_child_files[0] + '/' + files, "r").read()
            except:
                continue
            result = qualify_code(code, total_numbers + new_numbers, project_name)
            #print(type(result))
            if result > 0:
                new_numbers += result
                print("in " + files + " find " + str(result) + " method")
    return new_numbers


def find_query(repositories, total_numbers):
    if repositories["size"] > kMaxSize:
        return 0
    if not os.path.exists('code/' + str(repositories['id'])):
        os.makedirs('code/' + str(repositories['id']))
        os.system('git clone ' + repositories['html_url'] + '.git code/' + str(repositories['id']));
    result = check_all_files('code/' + str(repositories['id']), total_numbers, repositories['name'])
    # Save the memory
    if os.path.exists('code/' + str(repositories['id'])):
        shutil.rmtree('code/' + str(repositories['id']))
    return result

