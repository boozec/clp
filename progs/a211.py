import re
def road_rd(street):
  return (re.sub('Road$', 'Rd.', street))