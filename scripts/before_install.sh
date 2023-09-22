#!/bin/bash

# Nome projeto
PROJETO=$(xmllint --xpath "//*[local-name()='project']/*[local-name()='artifactId']/text()" /home/ubuntu/pipeline/pom.xml)

# Pare todos os servidores e inicie o servidor como um daemon
sudo systemctl stop ${PROJETO}

# Removendo diretorio antigo
sudo rm -rf /home/ubuntu/api-gateway

# KILL forçe
sudo kill -9 $(pgrep java)