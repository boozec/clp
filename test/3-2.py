
num = 29

def prime_numBIS(div):
    if (num >=1):
        if (div*div <= num/2):
            if (num % div) == 0:
                return False
            else:
                return prime_numBIS(div+1)
        else:
          return True
    else:
        return False
    
print(prime_numBIS(2))
