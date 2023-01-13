# Neoris
## Código fuente

Este repositorio comprende la entrega del código fuente de la solución.

## Imagen Docker y Docker-Compose

La imagen docker se puede obtener por medio del enlace de descarga:
docker pull rajoroman/neoris:neoris-test

Luego de obtenida la imagen se puede descarga el archivo docker-compose.yml
para la generación de los contenedores de la solución y de postgres12
ejecutando el comand docker-compose up o docker-compose up -d (ejecución en segundo plano)

## Postman y data.sql
En el directorio docker-compose encontrará las colecciones de postman en 
formato json para realizar las respectivas pruebas.

En caso de que al iniciar no se inserten los valores iniciales de 
tipos de cuenta y genero, los insert necesarios están en el archivo 
data.sql el cual se puede ejecutar desde un cliente de base de datos
