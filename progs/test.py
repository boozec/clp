import math
from re import f4, r3

from abs import *


def find_first_duplicate(nums, x):
    num_set = set()
    no_duplicate = -1

    for i in range(len(nums)):
        if nums[i] in num_set:
            return nums[i]
        else:
            num_set.add(nums[i])

    return no_duplicate
