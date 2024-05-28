import re 
regex = '^[aeiouAEIOU][A-Za-z0-9_]*'
def check_str(string): 
	if(re.search(regex, string)): 
		return ("Valid") 
	else: 
		return ("Invalid") 