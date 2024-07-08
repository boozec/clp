
def prime_num(num, div):
    if (num >=1):
        if (div*div <= num/2):
            if (num % div) == 0:
                return False
            else:
                return prime_num(num,div+1)
        else:
          return True
    else:
        return False
    
print(prime_num(29,2))
