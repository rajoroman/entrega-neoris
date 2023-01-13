# Neoris
## Código fuente

Este repositorio comprende la entrega del código fuente de la solución.

## Imagen Docker y Docker-Compose

Al ejecutar el archivo docker-compose.yml que se encuentra en la carpeta docker-compose, 
se obtienen los contenedores de la solución y de postgres12.
El comando de ejecución es docker-compose up o docker-compose up -d (ejecución en segundo plano)

## Postman y data.sql

En el directorio docker-compose encontrará las colecciones de postman en 
formato json para realizar las respectivas pruebas.

En caso de que al iniciar no se inserten los valores iniciales de 
tipos de cuenta y genero, los insert necesarios están en el archivo 
data.sql el cual se puede ejecutar desde un cliente de base de datos
