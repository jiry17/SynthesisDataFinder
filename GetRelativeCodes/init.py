import os
import shutil

def init(code_id, work_dict):
    if not os.path.exists("../dataset"):
        os.makedirs("../dataset")

    if not os.path.exists(work_dict):
        os.makedirs(work_dict)

    if os.path.exists(work_dict + '/code'):
        shutil.rmtree(work_dict + '/code')
    os.makedirs(work_dict + '/code')

    if os.path.exists(work_dict + '/badcode'):
        shutil.rmtree(work_dict + '/badcode')
    os.makedirs(work_dict + '/badcode')

    if os.path.exists('../GetRelativeCodes/qualify'):
        shutil.rmtree('../GetRelativeCodes/qualify')
    os.makedirs('../GetRelativeCodes/qualify')

    code_path = "../GetData/data/" + str(code_id) + ".java"
    os.system("cp " + code_path + " " + work_dict)

def get_unique(in_list):
    S = {}
    ans_list = []
    for i in in_list:
        if not i in S:
            S[i] = 1
            ans_list.append(i)
    return ans_list


def parse_token(type_token, method_token):
    candidate_tokens = []
    type_list = get_unique(type_token.split('+'))
    method_list = get_unique(method_token.split('+'))

    type_string = ""
    for type_word in type_list:
        type_string += type_word + "+"

    method_string = ""
    for method_word in method_list:
        method_string += "." + method_word + "+"

    candidate_tokens.append(type_string + method_string[: -1])
    candidate_tokens.append(method_string[: -1])
    return candidate_tokens


