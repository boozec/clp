def re_arrange_tuples(test_list, ord_list):
  temp = dict(test_list)
  res = [(key, temp[key]) for key in ord_list]
  return (res) 