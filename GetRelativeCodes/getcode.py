import sys
import getrelativecode

if len(sys.argv) < 2:
    raise Exception("Query is empty")

l = int(sys.argv[1])
r = int(sys.argv[2])
for code_id in range(l, r + 1):
    getrelativecode.getrelativecodes(code_id)

