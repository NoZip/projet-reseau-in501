#!/bin/bash

cd src/
javac *.java
read -p "Quel est votre pseudo? " nick
read -p "Veuillez choisir un port d'écoute : " port
java Start $nick $port
