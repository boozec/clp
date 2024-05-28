import heapq
def cheap_items(items,n):
  cheap_items = heapq.nsmallest(n, items, key=lambda s: s['price'])
  return cheap_items