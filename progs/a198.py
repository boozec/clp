INT_BITS = 32
def left_Rotate(n,d):   
    return (n << d)|(n >> (INT_BITS - d))  