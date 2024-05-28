from collections import Counter
def add_dict(d1,d2):
   add_dict = Counter(d1) + Counter(d2)
   return add_dict