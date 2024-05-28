import math
def volume_tetrahedron(num):
	volume = (num ** 3 / (6 * math.sqrt(2)))	
	return round(volume, 2)