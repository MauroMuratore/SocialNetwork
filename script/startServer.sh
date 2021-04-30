#!/bin/bash

if [[ $# -eq 0 ]]
then
cd ../bin
java server.MainServer

fi

if [[ $# -lt 3 ]]
then
case "$1" in 
-v)
cd ../bin
java server.MainServer $2
;;
*) echo "mettere un -v seguito da un numero compreso da 0 a 3 per scegliere il livello di verbosità, default è 1"
;;
esac
fi
