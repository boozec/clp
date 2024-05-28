import collections as ct
def merge_dictionaries(dict1,dict2):
    merged_dict = dict(ct.ChainMap({}, dict1, dict2))
    return merged_dict