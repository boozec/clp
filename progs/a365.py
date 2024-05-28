def remove_empty(tuple1): #L = [(), (), ('',), ('a', 'b'), ('a', 'b', 'c'), ('d')]
   tuple1 = [t for t in tuple1 if t]
   return tuple1