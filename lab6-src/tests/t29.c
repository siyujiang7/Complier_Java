long* x;

long a, b, c;

long main() {
  a = 10;
  b = 10;

  x = &c;

  c = a + b;

  printf("%ld + %ld = %ld\n", a, b, *x);
}
