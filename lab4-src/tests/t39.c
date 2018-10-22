long f(long argc, char** argv)
{
  char* g;
  g = "dog";
  argv[0] = g;
  return g[0];
}

long main(char argc, char** argv)
{
  char g;
  g = f(argc, argv);
  printf("%s\n", argv[0]);
  return -1;
}
