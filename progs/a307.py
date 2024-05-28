def find_fixed_point(arr, n): 
	for i in range(n): 
		if arr[i] is i: 
			return i 
	return -1