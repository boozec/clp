def jacobsthal_lucas(n): 
	dp=[0] * (n + 1) 
	dp[0] = 2
	dp[1] = 1
	for i in range(2, n+1): 
		dp[i] = dp[i - 1] + 2 * dp[i - 2]; 
	return dp[n]