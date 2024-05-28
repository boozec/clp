def find_Max(arr,low,high): 
    if (high < low): 
        return arr[0] 
    if (high == low): 
        return arr[low] 
    mid = low + (high - low) // 2 
    if (mid < high and arr[mid + 1] < arr[mid]): 
        return arr[mid] 
    if (mid > low and arr[mid] < arr[mid - 1]): 
        return arr[mid - 1]  
    if (arr[low] > arr[mid]): 
        return find_Max(arr,low,mid - 1) 
    else: 
        return find_Max(arr,mid + 1,high) 