from collections import defaultdict


def grouping_dictionary(l):
    d = defaultdict(list)
    for k, v in l:
        d[k].append(v)
    return d
