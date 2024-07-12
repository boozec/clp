n = int(input())
x = int(input())
y = int(input())
m = 1
while n < 2 * x - 3 * y + 5:
    g = x + 2 * y
    m = m + n + g
    n = n + 1
print(m)
