n = 9

a = 0
b = 1
c = 0
i = 0
while i < n - 2:
    c = a + b
    a = b
    b = c
    i = i + 1

print(c)
