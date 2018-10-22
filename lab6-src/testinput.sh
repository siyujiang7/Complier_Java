#!/bin/sh

cd bin || exit
printf "%s\n" "$1" > f.o

java simplec.Codegen < f.o > ../output.s

if [ $? -eq 0 ]
then
  cat ../output.s

  printf "\n***OUTPUT***\n"
  gcc -lm "../output.s" && ./a.out
  rm ../output.s

  cd .. || exit
else
  echo "Not compiling since we had errors"
fi
