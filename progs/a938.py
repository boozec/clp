def count_no (A,N,L,R): 
    count = 0
    for i in range (L,R + 1): 
        if (i % A != 0): 
            count += 1
        if (count == N): 
            break
    return (i) 