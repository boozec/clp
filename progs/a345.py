from collections import Counter 
def most_common_elem(s,a):
  most_common_elem=Counter(s).most_common(a)
  return most_common_elem