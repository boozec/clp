n = int(input())
x = int(input())
y = int(input())
m = 1
l = [0, 1, 2, 3, 4]
for i in l:
    g = x + 2 * y
    m = m + n + g
    n = n + 1
print(m)
