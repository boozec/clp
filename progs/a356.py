import math
def get_First_Set_Bit_Pos(n):
     return math.log2(n&-n)+1