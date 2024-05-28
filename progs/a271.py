def check_subset(list1,list2): 
    return all(map(list1.__contains__,list2)) 