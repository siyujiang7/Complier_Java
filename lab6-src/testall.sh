#!/bin/sh
totalpassed=0

set -e

if [ -d "tests" ];
then
  TESTCOUNT=29
  for i in $(seq 1 $TESTCOUNT);
  do
    ./test.sh "$i" 2>/dev/null && totalpassed=$((totalpassed + 1))
  done

  echo "Compilers project 6 (Code Generation)"
  echo "**************************"
  echo "$totalpassed / $TESTCOUNT passed"
fi


