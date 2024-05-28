def arc_length(d,a):
    pi=22/7
    if a >= 360:
        return None
    arclength = (pi*d) * (a/360)
    return arclength