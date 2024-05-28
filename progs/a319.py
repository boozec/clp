def remove_tuple(test_list):
  res = [sub for sub in test_list if not all(ele == None for ele in sub)]
  return (str(res)) 