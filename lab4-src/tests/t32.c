long main() {
  long g;
  for (g = 0; g < 5; g = g + 1) {
    long f;
    f = 0;
    for (f = 0; f < 5; f = f + 1) {
      if (f == g) {
        break;
      } else {
        continue;
      }
    }
  }
}
