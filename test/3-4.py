
def fibonacciIT(n,a,b):
    if ((n == 0) or (n == 1)):
        return a
    else:
        return (fibonacciIT(n-1, a+b, a))
   
print(fibonacciIT(6,1,1))
