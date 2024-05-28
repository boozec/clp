def len_log(list1):
    min=len(list1[0])
    for i in list1:
        if len(i)<min:
            min=len(i)
    return min