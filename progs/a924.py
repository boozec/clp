def assign_elements(test_list):
  res = dict()
  for key, val in test_list:
    res.setdefault(val, [])
    res.setdefault(key, []).append(val)
  return (res) 