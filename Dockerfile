FROM maven:3.6.3-jdk-11-slim AS build
# Use uma imagem base com o Java 17 e o sistema operacional desejados
RUN mvn clean package -Plocal -DprofileIdEnabled=true -DskipTests -B
FROM openjdk:17-oracle
# Criar o diretório de aplicativo
WORKDIR /app

# Copiar o arquivo JAR do seu microserviço para o contêiner
COPY target/clickbank-products-service-0.0.1-SNAPSHOT.jar /app/clickbank-products-service-0.0.1-SNAPSHOT.jar

# Expor a porta que o seu microserviço está escutando
EXPOSE 8086

# Comando para iniciar o seu microserviço quando o contêiner for iniciado
CMD ["java", "-jar", "clickbank-products-service-0.0.1-SNAPSHOT.jar"]

#sudo docker run -d --add-host=host.docker.internal:host-gateway -p 8080:8080 --name smiles-searcher-manager  pedroboaron/smiles-searcher-manager