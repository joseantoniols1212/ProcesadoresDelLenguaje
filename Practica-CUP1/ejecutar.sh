#!/bin/bash

clear

jflex Calculadora.flex

java -jar java-cup-11b.jar Calculadora.cup
javac -cp java-cup-11b.jar: *.java
java -cp java-cup-11b.jar: Calculadora prueba.txt
