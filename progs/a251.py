def remove_negs(num_list): 
    for item in num_list: 
        if item < 0: 
           num_list.remove(item) 
    return num_list