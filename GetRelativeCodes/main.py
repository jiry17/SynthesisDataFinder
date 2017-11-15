import search
import init

output = open("searchlog.txt","w")
init.init()

tokens = init.getsearch()
print("Search token is "+tokens[0])

s=search.login()
url = "https://github.com/search?l=Java&type=Code&utf8=%E2%9C%93&q="+tokens[0]

print("Searching on github")
html = search.gethtmlpre(s,url)
if (html==""):
    print("Search Filed, try to use token "+tokens[1])
    url = "https://github.com/search?l=Java&type=Code&utf8=%E2%9C%93&q="+tokens[1]
    gtml = search.gethtml(s,url)

ans = search.getlist(s,url)
print(len(ans))

codeId = 0
badcodeId =0
print("Downloading the code snippets")
for i in ans:
    code = search.getcode(s,i)
    if search.qualify(code):
        codeId += 1
        outputcode = open("code\\"+str(codeId)+".java","w",encoding='utf-8');
        outputcode.write(code)
        outputcode.close()
    else:
        badcodeId += 1
        outputcode = open("badcode\\"+str(badcodeId)+".java","w",encoding='utf-8');
        outputcode.write(code)
        outputcode.close()
    if ((codeId + badcodeId) % 5 == 0):
        totcode=(codeId+badcodeId)//5
        sss="."*totcode
        print(sss)

output.close()
print("\nSearch completed, found "+str(codeId)+" code snippets and "+str(badcodeId)+" bad code snippets.")

