def div_of_nums(nums,m,n):
 result = list(filter(lambda x: (x % m == 0 or x % n == 0), nums)) 
 return result