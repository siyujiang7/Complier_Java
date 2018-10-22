char* x() {
  long x, y;
  for (x = 0; x < 5; x = x + 1) {
    for (y = 0; y < 5; y = y + 1) {
      if ((x - 0) == (y - 0)) {
        break;
      }
    }
    return 1.2345;
  }
  return "dog";
}

long main() {
  char* g;
  g = x();
}
