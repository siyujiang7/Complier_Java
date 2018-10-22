#!/bin/sh

cd bin || exit
printf "%s\n" "$1" > f.o

# redirect all of our error messages into output.txt, stdout to output2.txt
java simplec.Semant < f.o 2> ../output.txt 1> ../output2.txt

cat ../output2.txt
rm f.o
cd .. || exit
cat output.txt
cat output.txt | python3 script.py
rm output.txt
rm output2.txt
