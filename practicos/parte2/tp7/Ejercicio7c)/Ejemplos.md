# Api Rest
Spring security: ademas del manejo de session e cifrado de contraseña, se incorporo una api rest con las mismas funcionalidades que tiene la web, pero comunicandose por JWT


## Ejemplo Uso

### Logearse y obtener un access token (JWT)
endpoint -> /api/auth/login
```Json
{
  "username": "admin",
  "password": "admin123"
}
```
ejemplo response 
```
{
    "accessToken": "token",
    "refreshToken": "token",
    "tokenType": "Bearer"
}
```

### Ejemplo de uso para consultar los valores de la cuota mensual

endpoint -> /api/admin/valor-cuota

![Texto alternativo](token_request.png)

ejemplo response con autorizacion:

![Texto alternativo](response_endpoint_cuota.png)
ejemplo sin autorizacion (sin token o token invalido)

![Texto alternativo](response_endpoint_cuota_invalido.png)


### Crear rutina

usar el token ya seas de admin o profesor.
![Texto alternativo](token_request_post.png)
ajustar el endpoint -> api/admin/rutinas

ejemplo de json
```
{
  "numeroSocio": 1002,
  "profesorId": 1001,
  "fechaInicia": "2025-02-15",
  "fechaFinaliza": "2025-12-15",
  "objetivo": "Bajar de peso y mejorar asdsadsa"
}
```
![Texto alternativo](response_crearRutina.png)

