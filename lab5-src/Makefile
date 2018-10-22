
all: myaddtest mystrlentest addarraytest factorialtest test

myaddtest: myadd.s myaddtest.c
	gcc -o myaddtest myadd.s myaddtest.c

mystrlentest: mystrlen.s mystrlentest.c
	gcc -o mystrlentest mystrlen.s mystrlentest.c

addarraytest: addarraytest.c addarray.s
	gcc -o addarraytest addarraytest.c addarray.s

factorialtest:  factorialtest.c factorial.s
	gcc -o factorialtest factorialtest.c factorial.s

test:
	./myaddtest
	./mystrlentest
	./addarraytest
	./factorialtest

clean:
	rm -f myaddtest mystrlentest addarraytest factorialtest