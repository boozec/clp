def check_tuples(test_tuple, K):
  res = all(ele in K for ele in test_tuple)
  return (res) 