def lower_ctr(str):
      lower_ctr= 0
      for i in range(len(str)):
          if str[i] >= 'a' and str[i] <= 'z': lower_ctr += 1     
      return  lower_ctr