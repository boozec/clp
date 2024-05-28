from collections import Counter 
def anagram_lambda(texts,str):
  result = list(filter(lambda x: (Counter(str) == Counter(x)), texts)) 
  return result