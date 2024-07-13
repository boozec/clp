n = int(input())
x = int(input())
m = 1
while n > 1:
    tmp = 2 * x
    m = m * tmp
    n = n - 1
print(m + tmp)
