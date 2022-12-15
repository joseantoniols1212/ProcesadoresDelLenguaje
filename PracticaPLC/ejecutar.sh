#!/bin/bash

name="$(basename "$PWD")"

# Compilamos los archivos
jflex $name.flex
cup $name.cup
javac -cp ../LibreriaCup/java-cup-11b-runtime.jar: *.java

# Ejecutamos las pruebas
for file in pruebas/*
do
  echo " ####### $file #######"
  # java -cp ../LibreriaCup/java-cup-11b-runtime.jar: $name $file
  echo "   ####### Source MyPlc #######"
  java -cp ../LibreriaCup/java-cup-11b-runtime.jar: $name $file
  echo "   ####### Source Correcta #######"
  ./plc $file
  echo "   ####### Salida Programa #######"
  ./ctd <(java -cp ../LibreriaCup/java-cup-11b-runtime.jar: $name $file)
  echo "   ####### Salida Correcta #######"
  ./ctd <(./plc $file)
done

rm Yylex.* sym.* parser* $name.class temp.ctd
