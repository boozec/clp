def find_ind(key, i, n, 
			k, arr):
	ind = -1
	start = i + 1
	end = n - 1;
	while (start < end):
		mid = int(start +
				(end - start) / 2)
		if (arr[mid] - key <= k):
			ind = mid
			start = mid + 1
		else:
			end = mid
	return ind
def removals(arr, n, k):
	ans = n - 1
	arr.sort()
	for i in range(0, n):
		j = find_ind(arr[i], i, 
					n, k, arr)
		if (j != -1):
			ans = min(ans, n -
						(j - i + 1))
	return ans