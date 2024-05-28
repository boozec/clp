def Diff(li1,li2):
    return (list(list(set(li1)-set(li2)) + list(set(li2)-set(li1))))
 