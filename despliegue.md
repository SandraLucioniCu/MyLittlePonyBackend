# REALIZACION DEL DESPLIEGUE:

Lo primero que se debe realizar es asegurarse de tener JDK13 o alguno compatible.
Eliminaremos la carpeta `target` y limpiaremos con:
```cmd 
./mvnw clean install 
```

Nos creará otra carpeta `target` y nos fijaremos qué nombre tiene nuestro `.jar`.
Lo colocaremos en nuestro `DockerFile`:

```dockerfile
FROM openjdk:13
ARG JAR_FILE=./target/game-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} mlpg.jar
ENTRYPOINT ["java","-jar","/mlpg.jar"]
```

Comandos que vamos a ejecutar para crear la imagen:

```cmd
docker build -f .\local-services\Dockerfile -t my-little-pony-game-server .
```

A continuación creamos los contenedores: 
```cmd
docker-compose -f .\local-services\docker-compose.yml up
```

Con esto tenemos todo. Al hacer que corra todo por Docker no hace falta ni instalarse MongoDB, ni el gestor Compass.
Si vamos al buscador y escribimos: ` localhost:8081/ ` se abrirá un gestor muy parecido donde se puede hacer exactamente lo mismo.


![Video de diseño de las páginas.](src/main/resources/static/img/video/video.mp4)