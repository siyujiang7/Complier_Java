long k, x, y, o, N;
double A, B, i, j, c, d, e, f, g, h, D, l, m, n, t;
double* z;
char* b;
char* LL;
long C;
void main(){
  z = malloc(1760*4*4);
  b = malloc(1760);
  LL = ".,-~:;=!*#$@";
  A = 0;
  B = 0;
  printf("\x1b[2J");
  while (1) {
    memset(b, 32, 1760);
    memset(z, 0, 7040*4);
    for (j = 0; 6.28 > j; j = j + 0.07) {
      for (i = 0; 6.28 > i; i = i + 0.02) {
        c = sin(i);
        d = cos(j);
        e = sin(A);
        f = sin(j);
        g = cos(A);
        h = d + 2;
        D = 1 / (c * h * e + f * g + 5);
        l = cos(i);
        m = cos(B);
        n = sin(B);
        t = c * h * g - f * e;
        x = 40 + 30 * D * (l * h * m - t * n);
        y = 12 + 15 * D * (l * h * n + t * m);
        o = x + 80 * y;
        N = 8 * ((f * e - c * d * g) * m - c * d * e - f * g - l * d * n);
        if (22 > y && y > 0 && x > 0) {
          if (80 > x) {
            if (D > z[o]) {
              z[o] = D;
              if (N > 0)
                b[o] = LL[N];
              else
                b[o] = LL[0];
            }
          }
        }
      }
    }
    printf("\x1b[H");
    for (k = 0; 1761 > k; k = k + 1) {
      C = k - 80 * (k / 80);
      if (C != 0)
        putchar(b[k]);
      else
        putchar(10);
    }
    A = A + 0.04;
    B = B + 0.02;
  }
}
