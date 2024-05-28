def matrix_to_list(test_list):
  temp = [ele for sub in test_list for ele in sub]
  res = list(zip(*temp))
  return (str(res))