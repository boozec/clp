n = [0,0,0,0,0]
x = 3
y = 7
c = 0
m = 1
while n > 1:
    tmp = 2 * x + n
    m = m * tmp
    n = n - 1
for i in n:
    g = 2 * m + i
    c = c + i + g
print(m + c)
