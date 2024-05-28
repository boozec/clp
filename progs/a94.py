def check_greater(test_tup1, test_tup2):
  res = all(x < y for x, y in zip(test_tup1, test_tup2))
  return (res) 