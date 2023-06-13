# REALIZACION DEL DESPLIEGUE:

Creacion de contenedores
```cmd
docker-compose -f .\local-services\docker-compose.yml up
```

Con esto tenemos todo. Al hacer que corra todo por Docker no hace falta ni instalarse MongoDB, ni el gestor Compass.
Si vamos al buscador y escribimos: ` localhost:8081/ ` se abrirá un gestor muy parecido donde se puede hacer exactamente lo mismo.

No olvidar leer el readme de la parte Frontend.