n = 2
y = 7
c = 0
while y > 0:
    g = 2 * n
    c = y * n + g
    y = y - 1
y = 10
g = 1
for i in range(y):
    g = i * y
    c = c + i + g
while c > 0:
    g = 4 * y
    n = n + c + g
    c = c - 1
print(g)
