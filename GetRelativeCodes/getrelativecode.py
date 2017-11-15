import search
import init


def getrelativecodes(code_id):
    code_file = open("../GetData/data/" + str(code_id) + ".java", "r")
    work_dic = "../dataset/data" + str(code_id)
    init.init(code_id, work_dic)

    method_token = code_file.readline()[3: -1]
    type_token = code_file.readline()[3: -1]
    source_project = code_file.readline()[3: -1]
    candidate_tokens = init.parse_token(type_token, method_token)

    s = search.login()

    print("Searching on github")
    url = ""
    for i in range(0,len(candidate_tokens)):
        url = "https://github.com/search?l=Java&type=Code&utf8=%E2%9C%93&q=" + \
              candidate_tokens[i]
        print("Try token " + candidate_tokens[i])
        html = search.gethtmlpre(s,url)
        if i == len(candidate_tokens)-1 or html != "":
            print("Search finished, the final token is " + candidate_tokens[i])
            break

    ans = search.getlist(s, url, source_project)
    print(len(ans))
    print("Downloading the code snippets")
    codes, badcodes = search.getall(ans)

    code_Id = 0
    badcode_Id =0
    for code in codes:
        if len(code) == 0:
            continue
        code_Id += 1
        outputcode = open(work_dic + "/code/" + str(code_Id) +
                          ".java", "w", encoding='utf-8')
        outputcode.write(code)
        outputcode.close()

    for code in badcodes:
        if len(code) == 0:
            continue
        badcode_Id += 1
        outputcode = open(work_dic + "/badcode/" + str(badcode_Id) +
                          ".java", "w", encoding='utf-8')
        outputcode.write(code)
        outputcode.close()

    print("\nSearch completed, found " + str(code_Id) + " code snippets and " +
          str(badcode_Id)+" bad code snippets.")
    output = open(work_dic + "/codenumer.txt", "w")
    output.write(str(code_Id)+"\n"+str(badcode_Id)+"\n")