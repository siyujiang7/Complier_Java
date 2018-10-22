long f(long a, char b, long c, char d) {
  printf("expecting: 5 a 6 b\n");
  printf("%ld\n", a);
  printf("%c\n", b);
  printf("%ld\n", c);
  printf("%c\n", d);
}

long main() {
  long x;
  x = 5;
  
  f(x, 'a', x + 1, 'b');
}
