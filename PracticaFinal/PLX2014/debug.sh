#!/bin/bash

name="PLXC"

# Compilamos los archivos
jflex $name.flex
cup $name.cup
javac -cp ../LibreriaCup/java-cup-11b-runtime.jar: *.java

# Ejecutamos las pruebas
for file in pruebas/*
do
  echo " ####### $file #######"
  java -cp ../LibreriaCup/java-cup-11b-runtime.jar: $name $file -d
done

rm Yylex.* sym.* parser* $name.class temp.ctd
