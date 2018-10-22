long geo_prog(long alpha, long n) {
  long current, sum;
  long i;
  current = 1;
  sum = current;
  for (i = 1; i <= n; i = i + 1) {
    current = current * alpha;
    sum = sum + current;
  }
  return sum;
}

long main() {
  printf("%ld\n", geo_prog(1, 999999));
}
