#!/bin/bash

name="PLXC"

# Compilamos los archivos
jflex $name.flex
cup $name.cup
javac -cp ../LibreriaCup/java-cup-11b-runtime.jar: *.java

echo "####### $1 #######"
echo "   ####### Source #######"
java -cp ../LibreriaCup/java-cup-11b-runtime.jar: $name $1 $2
echo "   ####### Ejecucion #######"
./ctd <(java -cp ../LibreriaCup/java-cup-11b-runtime.jar: $name $1 $2)

rm Yylex.* sym.* parser* $name.class
