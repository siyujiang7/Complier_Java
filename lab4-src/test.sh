#!/bin/sh

set -e

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

  java simplec.Semant < "../tests/t$1.c" 2> ../output2.txt 1> /dev/null
  python3 ../script.py < ../output2.txt  > ../output.txt

  printf "\n***YOUR ERRORS***\n"
  cat ../output2.txt


  if [ "$3" = "-x" ]
  then
    cat ../output.txt > "../tests/output/t$1.out"
  fi

  printf "\n***YOUR OUTPUT \t\t\t\t\t EXPECTED OUTPUT***\n"
  pr -m -t -w 90 ../output.txt "../tests/output/t$1.out"
  
  rm -f ../output.txt
  rm -f ../output2.txt

  printf "\n"
else
  java simplec.Semant < "../tests/t$1.c" 2> ../output2.txt 1> /dev/null
  python3 ../script.py < ../output2.txt > ../output.txt
  rm -f ../output2.txt

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
