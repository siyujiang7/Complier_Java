#!/bin/sh
totalpassed1=0
totalpassed2=0

TESTCOUNT=70
BONUSCOUNT=3

if [ "$1" = "-b" ]
then
  for i in $(seq 1 $BONUSCOUNT);
  do
    ./test.sh "b$i" && totalpassed2=$((totalpassed2 + 1))
  done
else
  for i in $(seq 1 $TESTCOUNT);
  do
    ./test.sh "$i" && totalpassed1=$((totalpassed1 + 1))
  done
fi

echo "Compilers project 3 (Parser)"
echo "**************************"
if [ "$1" = "-b" ]
then
  echo "$totalpassed2 / $BONUSCOUNT passed (BONUS)"
else
  echo "$totalpassed1 / $TESTCOUNT passed"
fi

