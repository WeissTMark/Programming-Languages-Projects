import re
p = re.compile(r'([\w-]+)\s*,\s*(\w+)\s*(\w+)?')
print("What would you like to match?")
a = input()
print(p.match(a))
