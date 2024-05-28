def last_Two_Digits(N): 
    if (N >= 10): 
        return
    fac = 1
    for i in range(1,N + 1): 
        fac = (fac * i) % 100
    return (fac) 