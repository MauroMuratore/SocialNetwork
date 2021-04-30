#!/bin/bash
echo "" > ../data/log.txt
echo "" > ../data/error.txt
echo "cancellato i log"
grep "\S" ../data/categoriaPartitaCalcioBackup.xml > ../data/categoriaPartitaCalcio.xml
grep "\S" ../data/categoriaPartitaCalcio.xml > ../data/categoriaPartitaCalcioBackup.xml 
grep "\S" ../data/escursioneMontagna.xml > ../data/escursioneBackup.xml
grep "\S" ../data/escursioneBackup.xml > ../data/escursioneMontagna.xml 
grep "\S" ../data/utenti.xml > ../data/utentiBackup.xml
grep "\S" ../data/utentiBackup.xml > ../data/utenti.xml
echo "eliminazione degli spazi e backup"

exit 0
