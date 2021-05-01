#!/bin/bash

if [[ $# -eq 0 ]]
then
cat ../data/categoriaPartitaCalcioBackup.xml  > ../data/categoriaPartitaCalcio.xml
cat ../data/escursioneBackup.xml > ../data/escursioneMontagna.xml 
cat ../data/utentiBackup.xml > ../data/utenti.xml
echo "restore backup"

elif [[ $1 -eq 1 ]]
then 
cat ../data/categoriaPartitaCalcioBackup.xml  > ../data/categoriaPartitaCalcio.xml
echo "restore backup partita di calcio"

elif [[ $1 -eq 2 ]]
then 
cat ../data/escursioneBackup.xml > ../data/escursioneMontagna.xml 
echo "restore backup escursione"

elif [[ $1 -eq 3 ]]
then 
cat ../data/utentiBackup.xml > ../data/utenti.xml
echo "restore backup utenti"

else
echo "per fare i backup di tutti i file usare setBackup.sh"
echo "per fare il backup solo di partite di calcio usare setBackup.sh 1"
echo "per fare il backup solo di escursione usare setBackup.sh 2"
echo "per fare il backup solo di utenti usare setBackup.sh 3"
fi
