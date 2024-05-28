def get_product(val) : 
	res = 1
	for ele in val: 
		res *= ele 
	return res 
def find_k_product(test_list, K):
  res = get_product([sub[K] for sub in test_list])
  return (res) 