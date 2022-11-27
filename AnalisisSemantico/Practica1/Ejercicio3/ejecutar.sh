#!/bin/bash

name="Test"

# Compilamos los archivos
jflex $name.flex
cup $name.cup
javac -cp ../../LibreriaCup/java-cup-11b-runtime.jar: *.java

# Ejecutamos las pruebas
for file in pruebas/*
do
  echo " ####### $file #######"
  java -cp ../../LibreriaCup/java-cup-11b-runtime.jar: $name $file
  # echo " -- Comprobacion con el resultado esperado : "
done

rm Yylex.* sym.* parser* $name.class
