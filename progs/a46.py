import re
def split_upperstring(text):
 return (re.findall('[A-Z][^A-Z]*', text))