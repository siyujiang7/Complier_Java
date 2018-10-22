long func()
{
  return 0;
}

long main()
{
  char** strstar;
  char* str;
  long num;
  str = "some long string";

  num = str[1];
  str[1] = func();
  str[1+3] = str[2];
  str['a'] = 'b';
}
