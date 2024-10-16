# Use uma imagem base do Java
#FROM openjdk:17-jdk-slim

# Crie um diretório para o aplicativo
#WORKDIR /app

# Copie o arquivo JAR para o contêiner
#COPY target/*.jar app.jar

# Expõe a porta que a aplicação Spring Boot está escutando
#EXPOSE 8080

# Comando para rodar a aplicação
#ENTRYPOINT ["java", "-jar", "app.jar"]
