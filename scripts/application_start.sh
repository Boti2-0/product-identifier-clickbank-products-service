#!/bin/bash

# Nome projeto
PROJETO=$(xmllint --xpath "//*[local-name()='project']/*[local-name()='artifactId']/text()" /home/ubuntu/pipeline/pom.xml)

# Pare todos os servidores e inicie o servidor como um daemon
sudo systemctl daemon-reload
sudo systemctl stop ${PROJETO}
sudo systemctl start ${PROJETO}
