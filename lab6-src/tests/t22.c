long even(long x){
  if (x == 0)
    return 1;
  return odd(x - 1);
}

long odd(long x){
  if (x == 0)
    return 0;
  return even(x - 1);
}

void main() {
  printf("200 is even?: ");
  printf("%d\n", even(200));

  printf("201 is even?: ");
  printf("%d\n", even(201));

}
