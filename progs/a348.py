def count_digs(tup):
  return sum([len(str(ele)) for ele in tup ]) 
def sort_list(test_list):
  test_list.sort(key = count_digs)
  return (str(test_list))