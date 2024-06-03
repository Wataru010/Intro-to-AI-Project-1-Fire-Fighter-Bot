#!/bin/sh
javac -d bin src/*.java

java -cp bin Run ${1-5} ${2-"demo"} ${3-1} ${4-5}