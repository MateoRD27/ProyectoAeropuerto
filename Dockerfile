# Usar la imagen de OpenJDK 21
FROM openjdk:21-jdk-slim

# Configurar el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR al contenedor
COPY target/sistemaVuelo2-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto del contenedor
EXPOSE ${SPRING_PORT}

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
