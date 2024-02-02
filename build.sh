#!/bin/sh

echo "Cleaning build environment..."
rm -f classes/uk/ac/tees/v8206593/life/*.class

echo "Compiling application..."
javac -d classes -classpath classes -sourcepath src \
    src/uk/ac/tees/v8206593/life/LifeApp.java

[ $? -eq 0 ] || exit 1

echo "Creating jar..."
jar -cfe LifeApp.jar uk/ac/tees/v8206593/life/LifeApp -C classes .

echo "done."

