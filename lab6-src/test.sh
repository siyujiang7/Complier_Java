#!/bin/sh

cd bin || exit

if [ "$#" -eq 0 ]
then
  printf "\"./test.sh 1\" to run test 1\n"
  printf "\"./test.sh 1 -p\" to run test 1 and display input/output/expected\n"
  exit 0
fi

if [ "$2" = "-p" ]
then
  printf "***INPUT***\n\n"
  cat -n "../tests/t$1.c"

  java simplec.Codegen < "../tests/t$1.c" 1> "../tests/t$1.s"

  cat "../tests/t$1.s"

  printf "\n***OUTPUT***\n"
  gcc "../tests/t$1.s"
  ./a.out > "output.txt"
  cat "output.txt"

  printf "\n***EXPECTED OUTPUT***\n"
  cat "../tests/output/t$1.out"

  if [ "$3" = "-x" ]
  then
    mv "output.txt" "../tests/output/t$1.out"
  else
    rm -f "output.txt"
  fi
  
  rm -f "../tests/t$1.s"
  rm -f "a.out"
  cd ..
else
  java simplec.Codegen < "../tests/t$1.c" 1> "../tests/t$1.s"

  gcc "../tests/t$1.s"
  ./a.out > output.txt

  if not diff "output.txt" "../tests/output/t$1.out" > /dev/null;
  then
    echo -e "t$1 \e[91mfailed\e[0m"
    rm -f "../tests/t$1.s"
    rm -f "a.out"
    rm -f "output.txt"
    cd ..
    exit -1
  fi
  echo -e "t$1 \e[32mOK\e[0m"
  rm "../tests/t$1.s"
  rm -f "a.out"
  rm -f "output.txt"
  cd ..
  exit 0
fi
