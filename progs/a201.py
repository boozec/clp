def count_Rotation(arr,n):   
    for i in range (1,n): 
        if (arr[i] < arr[i - 1]): 
            return i  
    return 0