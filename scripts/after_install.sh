#!/bin/bash

cd /home/ubuntu/pipeline

# Nome projeto
PROJETO=$(xmllint --xpath "//*[local-name()='project']/*[local-name()='artifactId']/text()" /home/ubuntu/pipeline/pom.xml)

# Vendo a versão
VERSAO_JAR=$(xmllint --xpath "//*[local-name()='project']/*[local-name()='version']/text()" /home/ubuntu/pipeline/pom.xml)

# Definindo a permissão de execução
sudo chmod +x /home/ubuntu/pipeline/target/${PROJETO}-${VERSAO_JAR}.jar

sudo su << SERVICE
cat <<EOF > /etc/systemd/system/${PROJETO}.service
[Unit]
Description=Backend Spring Boot

[Service]
ExecStart=/usr/bin/java -jar /home/ubuntu/pipeline/target/${PROJETO}-${VERSAO_JAR}.jar
SuccessExitStatus=143
Restart=always
RestartSec=5
StandardOutput=file:/var/log/nginx/${PROJETO}.log
StandardError=file:/var/log/nginx/${PROJETO}.err.log
Type=simple
WorkingDirectory=/home/ubuntu/pipeline

[Install]
WantedBy=multi-user.target
EOF
SERVICE