def remove_similar_row(test_list):
  res = set(sorted([tuple(sorted(set(sub))) for sub in test_list]))
  return (res) 