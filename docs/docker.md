![](../images/logos_feder.png)

| Entregable     | Procesador de datos                                          |
| -------------- | ------------------------------------------------------------ |
| Fecha          | 28/04/2021                                                   |
| Revisado por   | Paloma Terán Pérez                                           |
| Proyecto       | [ASIO](https://www.um.es/web/hercules/proyectos/asio) (Arquitectura Semántica e Infraestructura Ontológica) en el marco de la iniciativa [Hércules](https://www.um.es/web/hercules/) para la Semántica de Datos de Investigación de Universidades que forma parte de [CRUE-TIC](http://www.crue.org/SitePages/ProyectoHercules.aspx) |
| Módulo         | Service Discovery                                            |
| Tipo           | Software                                                     |
| Objetivo       | Módulo Service Discovery para el proyecto Backend SGI (ASIO). |
| Estado         | Completado al **100%**                                       |
| Próximos pasos | Si fuese necesario, registrar otros servicios en el módulo   |
| Documentación  | [Manual de usuario](./docs/manual_de_usuario.md) (documentación de alto nivel)<br />[Documentación técnica](./docs/documentacion-tecnica.md) (documentación de bajo nivel)<br/>[Documentación API REST de la librería de descubrimiento](./docs/documentacion_api_rest_de_la_libreria_de_descubrimiento.md) (documentación de bajo nivel)<br/>[docker](./docs/docker.md)<br/>[Librería de descubrimiento](https://github.com/HerculesCRUE/ib-discovery)<br/>[Service Discovery](https://github.com/HerculesCRUE/ib-service-discovery) |



# Generación de imagen Docker

Los artefactos bootables están diseñados para poder ser distribuidos como imagen Docker. Se indicarán a continuación las instrucciones.

## Compilación

En primer lugar es preciso [compilar el artefacto](build.md) y copiar el JAR generado en el directorio `docker-build/java` con el nombre `app.jar`

## Generación de la imagen

Para la generación de la imagen se precisa ejecutar el siguiente comando desde el directorio `docker-build` que es donde se encuentra el fichero `Dockerfile`.

```bash
docker build . -t {artifact-name}:{tag}
```

Sustituyendo `{artifact-name}` y `{tag}` por el nombre del artefacto y la versión respectivamente.

En caso que se desee distribuir la imagen a través de un Registry de Docker, se deberá hacer un `pull` mediante la ejecución el comando:

```bash
docker pull {artifact-name}:{tag}
```

Es posible que algunos Registros requieran de autenticación previa, debiendo para ello ejecutar previamente un `docker login`.
