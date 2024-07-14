n = int(input())
x = int(input())
m = 1
while n > 1:
    tmp = 2 * x
    m = m * tmp
    n = n - 1
c = 0
for i in range(m):
    g = 2 * m
    c = c + i + g
print(m + c)
