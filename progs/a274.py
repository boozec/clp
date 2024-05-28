def min_difference(test_list):
  temp = [abs(b - a) for a, b in test_list]
  res = min(temp)
  return (res) 