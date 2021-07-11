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

## 

# API REST de librería de descubrimiento

La documentación de esta sección hará referencia a cada uno de los EndPoints desplegados por el modulo service discovery, apoyándose en la documentación proporcionada por Swagger, por lo tanto los enlaces que se facilitan para los EndPoint descritos en Swagger, solo estarán disponibles si se ha realizado el despliegue, y dicho despliegue se ha realizado en la misma máquina donde se encuentra la presente documentación. En otro caso es necesario cambiar el host y el puerto por aquellos donde la librería de URIs ha sido desplegada.

La librería de descubrimiento despliega los siguientes EndPoints:

### API REST Librería de descubrimiento

##### Implementación

Es implementado por el controlador DiscoveryController descrito en la [seccion controladores, de la documentacion tecnica](./documentacion-tecnica.md#Controladores) 

##### EndPoints

![swagger](../images/swagger.png)

###### GET /service-discovery/

Disponible en Swagger el siguiente [enlace](http://localhost:9329/swagger-ui.html#/service-discovery-controller/getAllUsingGET)

**Semántica**

- Permite obtener el todos los nodos, servicios y tipos registrados en el modulo. El resultado se obtendrá de forma jerárquica siguiendo la estructura Nodo -> Servicio -> Tipo 

**PETICIÓN:**

```
curl -X GET "http://localhost:9329/service-discovery" -H "accept: */*"
```

**RESPUESTA**

```
[
  {
    "id": 1,
    "name": "um",
    "services": [
      {
        "id": 29383,
        "name": "trellis",
        "baseURL": "http://localhost",
        "port": null,
        "healthEndpoint": null,
        "status": "UNKNOWN",
        "types": []
      },
      {
        "id": 4961,
        "name": "Federation",
        "baseURL": "http://localhost",
        "port": 9328,
        "healthEndpoint": "/management/health",
        "status": "UP",
        "types": [
          {
            "id": 6610,
            "name": "fuseki",
            "suffixURL": "/endpoint-sparql/fuseki"
          }
        ]
      },
      {
        "id": 2,
        "name": "sparql-proxy",
        "baseURL": "http://herc-iz-front-desa.atica.um.es",
        "port": 8080,
        "healthEndpoint": "/v2/api-docs",
        "status": "DOWN",
        "types": [
          {
            "id": 15,
            "name": "sparql",
            "suffixURL": "/trellis/sparql"
          }
        ]
      }
    ]
  },
  {
    "id": 4,
    "name": "um2",
    "services": [
      {
        "id": 7062,
        "name": "Federation",
        "baseURL": "http://localhost",
        "port": 9328,
        "healthEndpoint": "/management/health",
        "status": "UP",
        "types": [
          {
            "id": 7073,
            "name": "fuseki",
            "suffixURL": "/endpoint-sparql/fuseki"
          }
        ]
      },
      {
        "id": 5,
        "name": "sparql-proxy",
        "baseURL": "http://herc-iz-front-desa.atica.um.es",
        "port": 8080,
        "healthEndpoint": "/v2/api-docs",
        "status": "DOWN",
        "types": [
          {
            "id": 16,
            "name": "sparql",
            "suffixURL": "/trellis/sparql"
          }
        ]
      },
      {
        "id": 29338,
        "name": "trellis",
        "baseURL": "http://localhost",
        "port": null,
        "healthEndpoint": null,
        "status": "UNKNOWN",
        "types": []
      }
    ]
  }
]
```



###### GET /service-discovery/node

Disponible en Swagger el siguiente [enlace](http://localhost:9329/swagger-ui.html#/service-discovery-controller/getNodeByNameUsingGET)

**Semántica**

- Permite obtener el nodo, servicios y tipos registrados en el modulo para el nodo indicado por parámetro. El resultado se obtendrá de forma jerárquica siguiendo la estructura Nodo -> Servicio -> Tipo 

**PETICIÓN:**

```
curl -X GET "http://localhost:9329/service-discovery/node?nodeName=um" -H "accept: */*"
```

**RESPUESTA**

```
{
  "id": 1,
  "name": "um",
  "services": [
    {
      "id": 29383,
      "name": "trellis",
      "baseURL": "http://localhost",
      "port": null,
      "healthEndpoint": null,
      "status": "UNKNOWN",
      "types": []
    },
    {
      "id": 4961,
      "name": "Federation",
      "baseURL": "http://localhost",
      "port": 9328,
      "healthEndpoint": "/management/health",
      "status": "UP",
      "types": [
        {
          "id": 6610,
          "name": "fuseki",
          "suffixURL": "/endpoint-sparql/fuseki"
        }
      ]
    },
    {
      "id": 2,
      "name": "sparql-proxy",
      "baseURL": "http://herc-iz-front-desa.atica.um.es",
      "port": 8080,
      "healthEndpoint": "/v2/api-docs",
      "status": "DOWN",
      "types": [
        {
          "id": 15,
          "name": "sparql",
          "suffixURL": "/trellis/sparql"
        }
      ]
    }
  ]
}
```



###### GET /service-discovery/service

Disponible en Swagger el siguiente [enlace](http://localhost:9329/swagger-ui.html#/service-discovery-controller/getServiceByNameUsingGET)

**Semántica**

- Permite obtener el todos los nodos, el servicio y tipos registrados en el modulo para el servicio indicado por parámetro. El resultado se obtendrá de forma jerárquica siguiendo la estructura Nodo -> Servicio -> Tipo 

**PETICIÓN:**

```
curl -X GET "http://localhost:9329/service-discovery/service?serviceName=federation" -H "accept: */*"
```

**RESPUESTA**

```
[
  {
    "id": 1,
    "name": "um",
    "services": [
      {
        "id": 4961,
        "name": "Federation",
        "baseURL": "http://localhost",
        "port": 9328,
        "healthEndpoint": "/management/health",
        "status": "UP",
        "types": [
          {
            "id": 6610,
            "name": "fuseki",
            "suffixURL": "/endpoint-sparql/fuseki"
          }
        ]
      }
    ]
  },
  {
    "id": 4,
    "name": "um2",
    "services": [
      {
        "id": 7062,
        "name": "Federation",
        "baseURL": "http://localhost",
        "port": 9328,
        "healthEndpoint": "/management/health",
        "status": "UP",
        "types": [
          {
            "id": 7073,
            "name": "fuseki",
            "suffixURL": "/endpoint-sparql/fuseki"
          }
        ]
      }
    ]
  }
]
```



###### POST /service-discovery/service

Disponible en Swagger el siguiente [enlace](http://localhost:9329/swagger-ui.html#/service-discovery-controller/addServiceUsingPOST)

**Semántica**

Permite solicitar la recarga de la cache.

**PETICIÓN:**

```
curl -X POST "http://localhost:9327/discovery/cache/force-reload" -H "accept: */*"
```

**RESPUESTA**

```
DONE
```



###### GET /service-discovery/service/type

Disponible en Swagger el siguiente [enlace](http://localhost:9329/swagger-ui.html#/service-discovery-controller/getServiceByNameAndTypeUsingGET)

**Semántica**

- Permite obtener el todos los nodos, los servicios y el tipo registrados en el modulo para el tipo indicado por parámetro. El resultado se obtendrá de forma jerárquica siguiendo la estructura Nodo -> Servicio -> Tipo 

**PETICIÓN:**

```
curl -X GET "http://localhost:9329/service-discovery/service/type?serviceName=federation&typeName=fuseki" -H "accept: */*"
```

**RESPUESTA**

```
[
  {
    "id": 1,
    "name": "um",
    "services": [
      {
        "id": 4961,
        "name": "Federation",
        "baseURL": "http://localhost",
        "port": 9328,
        "healthEndpoint": "/management/health",
        "status": "UP",
        "types": [
          {
            "id": 6610,
            "name": "fuseki",
            "suffixURL": "/endpoint-sparql/fuseki"
          }
        ]
      }
    ]
  },
  {
    "id": 4,
    "name": "um2",
    "services": [
      {
        "id": 7062,
        "name": "Federation",
        "baseURL": "http://localhost",
        "port": 9328,
        "healthEndpoint": "/management/health",
        "status": "UP",
        "types": [
          {
            "id": 7073,
            "name": "fuseki",
            "suffixURL": "/endpoint-sparql/fuseki"
          }
        ]
      }
    ]
  }
]
```





###### POST /service-discovery/type

Disponible en Swagger el siguiente [enlace](http://localhost:9329/swagger-ui.html#/service-discovery-controller/addTypeUsingPOST)

**Semántica**

Permite registrar un tipo, en un determinado servicio, perteneciente a un nodo. Solo se permite un tipo con el mismo nombre por servicio y nodo. En caso de que ya exista no se insertara.

**Parámetros**

- **nodeName:** (Requerido). Cadena de Texto.  El nombre del nodo al que pertenece.

- **serviceName:** (Requerido). Cadena de Texto.  El nombre del servicio al que pertenece.

- **typeName:** (Requerido). Cadena de Texto.  El nombre del tipo que queremos crear.

- **suffixURL:** (Requerido). Cadena de Texto.  sufijo de los enpoints relacionados con el tipo que estamos creando. La url final se compondrá por BASE_URL + PORT + SUFFIX_URL 

  

**PETICIÓN:**

```
curl -X POST "http://localhost:9329/service-discovery/type?nodeName=um2&serviceName=Federation&suffixURL=%2Fendpoint-sparql%2Ffuseki&typeName=fuseki" -H "accept: */*"
```

**RESPUESTA**

```
{
  "id": 4,
  "name": "um2",
  "services": [
    {
      "id": 7062,
      "name": "Federation",
      "baseURL": "http://localhost",
      "port": 9328,
      "healthEndpoint": "/management/health",
      "status": "UP",
      "types": [
        {
          "id": 7073,
          "name": "fuseki",
          "suffixURL": "/endpoint-sparql/fuseki"
        }
      ]
    },
    {
      "id": 5,
      "name": "sparql-proxy",
      "baseURL": "http://herc-iz-front-desa.atica.um.es",
      "port": 8080,
      "healthEndpoint": "/v2/api-docs",
      "status": "DOWN",
      "types": [
        {
          "id": 16,
          "name": "sparql",
          "suffixURL": "/trellis/sparql"
        }
      ]
    },
    {
      "id": 29338,
      "name": "trellis",
      "baseURL": "http://localhost",
      "port": null,
      "healthEndpoint": null,
      "status": "UNKNOWN",
      "types": []
    }
  ]
}
```

