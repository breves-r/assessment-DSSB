FROM openjdk:17-jdk-slim
# Crie um diretório para a aplicação
WORKDIR /app
# Copie o arquivo JAR da aplicação para o diretório do contêiner
COPY target/at-0.0.1-SNAPSHOT.jar app.jar
# Exponha a porta que a aplicação usará
EXPOSE 8080
# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]