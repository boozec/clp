def check(arr,n): 
    g = 0 
    for i in range(1,n): 
        if (arr[i] - arr[i - 1] > 0 and g == 1): 
            return False
        if (arr[i] - arr[i] < 0): 
            g = 1
    return True