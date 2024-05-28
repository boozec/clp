import re 
def remove_char(S):
  result = re.sub('[\W_]+', '', S) 
  return result