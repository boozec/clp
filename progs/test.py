n = int(input())
x = int(input())
y = int(input())
m = 1
_tmp0 = x + 2 * y
while n < 2 * x - 3 * y + 5:
    g = _tmp0
    m = m + n + g
    n = n + 1
print(m)
