long main()
{
    char** strstar;

    strstar = &*strstar;
    strstar = &*&*&*strstar;
    strstar = &*&*&**strstar;

    return 1;
}
