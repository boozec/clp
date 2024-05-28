import re
def fill_spaces(text):
  return (re.sub("[ ,.]", ":", text))