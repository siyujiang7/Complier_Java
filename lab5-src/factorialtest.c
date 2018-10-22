
#include <assert.h>
#include <stdio.h>

double fact(int n);

main()
{
	assert(fact(5)==120);
	printf("factorial test passed.\n");
	return 0;
}

