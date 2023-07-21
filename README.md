# Introduction
Este es un servicio creado como prueba.

# Getting Started
Para desplegar este servicio debes hacer lo siguiente:

1. Abre una terminal y ubicate en la carpeta de este proyecto
2. Ejecuta los siguientes comandos para generar el archivo .jar
   1. mvn clean
   2. mvn compile
   3. mvn test
   4. mvn install
3. Desde la terminal entra a la carpeta "target" que se encuentra dentro del protecto
4. Ejecuta el comando "java -jar analista-0.0.1-SNAPSHOT.jar"

# Build and Test
Una vez desplegado el proyecto dirigete a la siguiente url "http://localhost:8080/swagger-ui/index.html#/"
ahi estara el listado de endpoints que posee el servicio y podrás probarlos desde Postman.