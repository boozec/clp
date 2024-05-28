from collections import Counter
def count_variable(a,b,c,d):
  c = Counter(p=a, q=b, r=c, s=d)
  return list(c.elements())