import sys 

def find_closet(A, B, C, p, q, r): 
	diff = sys.maxsize 
	res_i = 0
	res_j = 0
	res_k = 0
	i = 0
	j = 0
	k = 0
	while(i < p and j < q and k < r): 
		minimum = min(A[i], min(B[j], C[k])) 
		maximum = max(A[i], max(B[j], C[k])); 
		if maximum-minimum < diff: 
			res_i = i 
			res_j = j 
			res_k = k 
			diff = maximum - minimum; 
		if diff == 0: 
			break
		if A[i] == minimum: 
			i = i+1
		elif B[j] == minimum: 
			j = j+1
		else: 
			k = k+1
	return A[res_i],B[res_j],C[res_k]