def rotate_right(list1,m,n):
  result =  list1[-(m):]+list1[:-(n)]
  return result