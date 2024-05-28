import re
def search_literal(pattern,text):
 match = re.search(pattern, text)
 s = match.start()
 e = match.end()
 return (s, e)