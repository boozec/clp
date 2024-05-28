def palindrome_lambda(texts):
  result = list(filter(lambda x: (x == "".join(reversed(x))), texts))
  return result