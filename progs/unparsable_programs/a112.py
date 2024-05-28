def check_valid(test_tup):
  res = not any(map(lambda ele: not ele, test_tup))
  return (res) 