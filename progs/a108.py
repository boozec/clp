from collections import defaultdict 
def get_unique(test_list):
  res = defaultdict(list)
  for sub in test_list:
    res[sub[1]].append(sub[0])
  res = dict(res)
  res_dict = dict()
  for key in res:
    res_dict[key] = len(list(set(res[key])))
  return (str(res_dict)) 