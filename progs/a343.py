import re
def num_position(text):
 for m in re.finditer("\d+", text):
    return m.start()