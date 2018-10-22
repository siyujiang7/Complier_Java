long add(long a, long b) {
  if (b < a)
    return add(b, a);
  if (a == 0)
    return b;
  return add(a - 1, b + 1);
}

long main() {
  printf("%d\n", add(5, 39));
}
