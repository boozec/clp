def sorted_dict(dict1):
  sorted_dict = {x: sorted(y) for x, y in dict1.items()}
  return sorted_dict