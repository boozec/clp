def get_noOfways(n):
    if (n == 0):
        return 0;
    if (n == 1):
        return 1; 
    return get_noOfways(n - 1) + get_noOfways(n - 2);