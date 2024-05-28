def check_element(test_tup, check_list):
  res = False
  for ele in check_list:
    if ele in test_tup:
      res = True
      break
  return (res) 