struct cat {
  char* name;
  long age;
};

void printCat1 ( struct cat kitty ) {
  printf("%s: %d\n", kitty.name, kitty.age);
}

void printCat2 ( struct cat *kitty ) {
  printf("%s: %d\n", kitty->name, kitty->age);
}

long main() {
  struct cat kitty1; kitty1.name = "snowball"; kitty1.age = 5;
  printCat1( kitty1 );

  struct cat kitty2; kitty2.name = "melon"; kitty2.age = 7;
  printCat2( &kitty2 );
  return 0;
}
