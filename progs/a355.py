import re
def split_list(text):
  return (re.findall('[A-Z][^A-Z]*', text))