import re
def remove_multiple_spaces(text1):
  return (re.sub(' +',' ',text1))