n = 2
y = 7
c = 0
while y > 0:
    g = 2 * n
    c = y * n + g
    y = y - 1

for i in range(y):
    g = 2 * y
    c = c + i + g
print(g)
