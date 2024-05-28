def sector_area(r,a):
    pi=22/7
    if a >= 360:
        return None
    sectorarea = (pi*r**2) * (a/360)
    return sectorarea