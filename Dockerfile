FROM openjdk:17

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR construido en el paso de construcción al contenedor
COPY target/spring-security-course-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080, que es el puerto predeterminado para las aplicaciones Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "app.jar"]


