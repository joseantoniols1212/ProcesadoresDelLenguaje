int x;
x = 6;
int a=1, b=1;
int temp, i;
for i=1 to x do {
  temp = a;
  a = a + b;
  b = temp;
}
print(a);
