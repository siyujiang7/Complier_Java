#!/bin/sh

cd bin || exit
printf "%s\n" "$1" > f.o
java simplec.AST < f.o > ../output.txt
cat ../output.txt
rm ../output.txt
rm f.o
cd ..
