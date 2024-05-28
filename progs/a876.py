def add_str(test_tup, K):
  res = [ele for sub in test_tup for ele in (sub, K)]
  return (res) 