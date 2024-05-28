def remove_datatype(test_tuple, data_type):
  res = []
  for ele in test_tuple:
    if not isinstance(ele, data_type):
      res.append(ele)
  return (res) 