n = int(input())
x = int(input())
y = int(input())
m = 1

if x == 10:
    x = 1
while n > 2 * x - 3 * y + 5:
    tmp = 2 * x
    m = m * tmp
    n = n - 1

print(m + tmp)
