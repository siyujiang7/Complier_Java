#!/bin/sh
totalpassed=0

set -e

if [ -d "tests" ];
then
  TESTCOUNT=$(ls ./tests/ | tr -d "LICENSE|output|(.c|t)" | sort -rn | head -n 1)

  for i in $(seq 1 $TESTCOUNT);
  do
    ./test.sh "$i" && totalpassed=$((totalpassed + 1))
  done

  echo "Compilers project 4 (Semantic Analysis)"
  echo "**************************"
  echo "$totalpassed / $TESTCOUNT passed"
else
  echo "You're missing the test directory"
  echo "See lab handout for instructions on downloading tests"
fi


