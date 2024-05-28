def sum_positivenum(nums):
  sum_positivenum = list(filter(lambda nums:nums>0,nums))
  return sum(sum_positivenum)