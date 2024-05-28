def check_Type_Of_Triangle(a,b,c): 
    sqa = pow(a,2) 
    sqb = pow(b,2) 
    sqc = pow(c,2) 
    if (sqa == sqa + sqb or sqb == sqa + sqc or sqc == sqa + sqb): 
        return ("Right-angled Triangle") 
    elif (sqa > sqc + sqb or sqb > sqa + sqc or sqc > sqa + sqb): 
        return ("Obtuse-angled Triangle") 
    else: 
        return ("Acute-angled Triangle") 