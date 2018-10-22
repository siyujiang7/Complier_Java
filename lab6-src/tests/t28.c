long* x;
long* y;

long main() {
  long i;

  x = malloc(8 * 5);
  y = malloc(8 * 5);

  x[0] = 1;
  x[1] = 2;
  x[2] = 3;
  x[3] = 4;
  x[4] = 5;

  for (i = 0; i < 5; i = i + 1) {
    y[i] = *x;
    printf("%ld\n", y[i]);
    x = x + 1;
  }
}
