void func(long b, long c) {
  long a;
  for (a = 0; a < 100; a = a + 1) {
    b = b + c;
  }
  printf("%ld %ld %ld\n",
      a, b, c);
  return;
}
long main() {
  long b, c;
  b = 1;
  c = 7;
  func(b, c);
}
