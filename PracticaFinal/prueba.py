import ast

print(ast.dump(ast.parse("""

if x:

   ...

elif y:

   ...

else:

   ...

""")))
