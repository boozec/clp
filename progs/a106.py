def count_Set_Bits(n) :  
    n += 1; 
    powerOf2 = 2;   
    cnt = n // 2;  
    while (powerOf2 <= n) : 
        totalPairs = n // powerOf2;  
        cnt += (totalPairs // 2) * powerOf2;  
        if (totalPairs & 1) : 
            cnt += (n % powerOf2) 
        else : 
            cnt += 0
        powerOf2 <<= 1;    
    return cnt;  