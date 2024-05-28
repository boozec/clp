def remove_list_range(list1, leftrange, rigthrange):
   result = [i for i in list1 if (min(i)>=leftrange and max(i)<=rigthrange)]
   return result