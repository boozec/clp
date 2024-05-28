def is_Word_Present(sentence,word): 
    s = sentence.split(" ") 
    for i in s:  
        if (i == word): 
            return True
    return False