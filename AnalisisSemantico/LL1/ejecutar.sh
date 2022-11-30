#!/bin/bash

# Ejecutamos las pruebas
for file in pruebas/*
do
  echo " ####### $file #######"
  java Calculadora $file
done
