from collections import defaultdict 
def freq_element(test_tup):
  res = defaultdict(int)
  for ele in test_tup:
    res[ele] += 1
  return (str(dict(res))) 