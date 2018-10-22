#!/bin/sh

cd bin || exit

if [ "$#" -eq 0 ]
then
  printf "\"./test.sh 1\" to run test 1 (works up to 70)\n"
  printf "\"./test.sh 1 -p\" to run test 1 and display input/output/expected\n"
  printf "\"./test.sh b1\" to run bonus test 1 (works up to 3)\n"
  exit 0
fi

if [ "$2" = "-p" ]
then
  printf "***INPUT***\n\n"
  cat -n "../tests/t$1.c"

  java simplec.AST < "../tests/t$1.c" > ../output.txt

  printf "\n***YOUR OUTPUT***\n"
  cat ../output.txt

  if [ "$3" = "-x" ]
  then
    cat ../output.txt > "../tests/output/t$1.out"
  fi

  printf "\n***EXPECTED OUTPUT***\n"
  cat "../tests/output/t$1.out"
  
  rm -f ../output.txt

  printf "\n"
else
  java simplec.AST < "../tests/t$1.c" > ../output.txt

  if not diff ../output.txt "../tests/output/t$1.out" 2> /dev/null;
  then
    echo "t$1 failed"
    cd ..
    rm -f output.txt
    exit -1
  fi
  echo "t$1 OK"
  cd ..
  rm -f output.txt
  exit 0
fi
