import math
def round_up(a, digits):
    n = 10**-digits
    return round(math.ceil(a / n) * n, digits)