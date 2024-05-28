def sum_column(list1, C):
    result = sum(row[C] for row in list1)
    return result