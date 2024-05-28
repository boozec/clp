import bisect
def right_insertion(a, x):
    i = bisect.bisect_right(a, x)
    return i