from collections import OrderedDict
def remove_duplicate(string):
  result = ' '.join(OrderedDict((w,w) for w in string.split()).keys())
  return result