from sys import maxsize 
def max_sub_array_sum(a,size): 
	max_so_far = -maxsize - 1
	max_ending_here = 0
	start = 0
	end = 0
	s = 0
	for i in range(0,size): 
		max_ending_here += a[i] 
		if max_so_far < max_ending_here: 
			max_so_far = max_ending_here 
			start = s 
			end = i 
		if max_ending_here < 0: 
			max_ending_here = 0
			s = i+1
	return (end - start + 1)