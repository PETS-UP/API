#!/bin/bash

# Caminho para o arquivo JAR
caminho_jar="/home/ubuntu/app-petsup-banco-prd.jar"

# Verifica se o arquivo JAR existe
if [ -f "$caminho_jar" ]; then
    # Executa o arquivo JAR
    java -jar "$caminho_jar > /dev/null 2>&1 &"
else
    echo "O arquivo JAR não foi encontrado em: $caminho_jar"
fi