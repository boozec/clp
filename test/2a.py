n = int(input())
x = int(input())
m = 1
tmp = 0
while n > 1:
    m = m * tmp
    tmp = 2 * x
    n = n - 1

print(m + tmp)
