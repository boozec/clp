def factorial(start,end): 
    res = 1 
    for i in range(start,end + 1): 
        res *= i      
    return res 
def sum_of_square(n): 
   return int(factorial(n + 1, 2 * n)  /factorial(1, n)) 