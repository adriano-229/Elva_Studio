# Seguridad: cómo funciona en el backend (My Car)

Este documento describe, de manera exhaustiva y práctica, cómo está montada la seguridad en el backend cuyos archivos
has compartido. Las rutas citadas corresponden al proyecto Spring (no a este repo cliente), por ejemplo:

- `com.example.mycar.controller.AuthController`
- `com.example.mycar.security.*`
- `com.example.mycar.security.auth.SecurityConfig`

Índice rápido

- Visión general
- Componentes clave
- Flujo de autenticación (login) y emisión de JWT
- Validación de JWT en endpoints protegidos
- Endpoints y acceso
- Ejemplos prácticos (curl)
- Consideraciones y bordes
- Observaciones de diseño actuales
- Recomendaciones de mejora

---

## Visión general

El backend usa Spring Security con un enfoque stateless basado en tokens JWT (Bearer). Hay dos cadenas de seguridad
definidas:

1) Authorization Server (endpoints OAuth2 estándar, potencialmente para flujos OIDC/OAuth)
2) Resource Server (el API que valida JWT para proteger recursos)

Además, existe un login “propio” (controlador) que autentica `usuario/clave` con `AuthenticationManager` y emite un JWT
HMAC (HS256) mediante una utilidad (`JwtUtil`). El Resource Server valida ese JWT con un `JwtDecoder` configurado con la
misma clave secreta.

Resultado: los clientes llaman a `/api/auth/login`, reciben un token, y después consumen endpoints protegidos enviando
`Authorization: Bearer <token>`.

---

## Componentes clave

### 1) Controlador de autenticación: `AuthController`

Ubicación: `com.example.mycar.controller.AuthController`

- `POST /api/auth/login`
    - Usa `AuthenticationManager` para autenticar: `UsernamePasswordAuthenticationToken(nombreUsuario, clave)`.
    - Carga `UserDetails` con `CustomUserDetailsService`.
    - Genera un JWT con `JwtUtil.generateToken(userDetails)`.
    - Resuelve el usuario vía `UsuarioService.buscarUsuarioPorNombre` para evaluar si requiere cambio de clave.
    - Respuesta: `LoginResponseDTO { token, requiereCambioClave }`.
    - `requiereCambioClave` se calcula con `passwordEncoder.matches("mycar", usuarioDTO.getClave())`, es decir, si la
      clave sigue siendo la predeterminada (codificada en la base).

- `POST /api/auth/cambiar-clave/{id}`
    - Requiere estar autenticado (ver configuración de rutas más abajo).
    - Llama a `UsuarioService.modificarClave(id, actual, nueva, confirmar)` y responde texto de éxito.

### 2) Configuración de seguridad web: `SecurityConfig`

Ubicación: `com.example.mycar.security.auth.SecurityConfig`

Define dos `SecurityFilterChain` con órdenes distintos:

- `@Order(1) authorizationServerSecurityFilterChain`
    - Aplica la configuración por defecto del Authorization Server de Spring (
      `OAuth2AuthorizationServerConfiguration.applyDefaultSecurity`).
    - Habilita `formLogin()` por defecto para endpoints del Authorization Server (p.ej., `/oauth2/authorize`,
      `/oauth2/token` cuando se usan).

- `@Order(2) defaultSecurityFilterChain` (Resource Server)
    - `csrf` deshabilitado (API stateless).
    - Autorización por rutas:
        - `permitAll()` para `POST /api/auth/login`.
        - `authenticated()` para `/api/auth/cambiar-clave/**` y el resto.
    - Sesión `STATELESS`.
    - `oauth2ResourceServer().jwt()` para validar Bearer JWT automáticamente con el `JwtDecoder`.

Otras beans relevantes:

- `AuthenticationManager` con `CustomUserDetailsService` + `BCryptPasswordEncoder` (para el login del controlador).
- `PasswordEncoder` es `BCryptPasswordEncoder`.
- `RegisteredClientRepository` y `AuthorizationServerSettings` (soporte de Authorization Server con un cliente en
  memoria).
- `JwtDecoder` (Nimbus) configurado con una clave simétrica (HMAC SHA-256) idéntica a la usada por `JwtUtil`.

### 3) Servicio de usuarios (para autenticación): `CustomUserDetailsService`

Ubicación: `com.example.mycar.security.CustomUserDetailsService`

- Implementa `UserDetailsService`.
- Busca `Usuario` por `nombreUsuario` en `UsuarioRepository`.
- Envuelve el resultado en `UserDetailsImpl`.

Hay también un `UserDetailsServiceImpl` con la misma lógica. Ver “Observaciones de diseño”.

### 4) Adaptador de usuario: `UserDetailsImpl`

Ubicación: `com.example.mycar.security.UserDetailsImpl`

- Expone a Spring Security `username`, `password` y `authorities` en base a la entidad `Usuario`.
- Autoridades: un único `GrantedAuthority` con valor `ROLE_<ROL>`, donde `<ROL>` viene de `usuario.getRol().name()`.

### 5) Utilidad JWT: `JwtUtil`

Ubicación: `com.example.mycar.security.JwtUtil`

- Algoritmo: HS256 (HMAC-SHA256) con clave simétrica.
- SecretKey inicializada con `Keys.hmacShaKeyFor("claveSecretaMuySegura123456789012345678901234567890".getBytes(...))`.
- Estructura del token generado:
    - `sub` (subject): el `username`.
    - `iat` (issued at).
    - `exp` (expira a la hora: `1000 * 60 * 60`).
    - No añade scopes/roles por defecto.
- Validación y parsing con `Jwts.parser().setSigningKey(SECRET_KEY)...`.

### 6) Filtro JWT personalizado: `JwtRequestFilter`

Ubicación: `com.example.mycar.security.JwtRequestFilter`

- Implementa `OncePerRequestFilter` para extraer el Bearer token manualmente, validar con `JwtUtil`, y poblar el
  `SecurityContext`.
- Excluye rutas que empiecen por `/auth`.
- Observación importante: en la configuración actual de `SecurityConfig` NO se añade este filtro explícitamente a la
  cadena del Resource Server. El Resource Server ya valida el JWT con `oauth2ResourceServer().jwt()` usando
  `JwtDecoder`. Es decir, este filtro queda “al margen” a menos que se agregue con `.addFilterBefore(...)` en la cadena
  correspondiente.

---

## Flujo de autenticación (login) y emisión de JWT

1) Cliente llama a `POST /api/auth/login` con cuerpo `{ nombreUsuario, clave }` (`LoginRequestDTO`).
2) `AuthController` crea un `UsernamePasswordAuthenticationToken` y delega en `AuthenticationManager`.
3) `AuthenticationManager` usa `CustomUserDetailsService` y `BCryptPasswordEncoder` para verificar la clave.
4) Si la autenticación es correcta:
    - `UserDetails` se carga con el usuario y sus roles.
    - Se firma un JWT con `JwtUtil` (HS256). Expira a la hora.
    - Se devuelve `LoginResponseDTO { token, requiereCambioClave }`.
5) El cliente guarda el `token` y lo envía en cada request protegida como `Authorization: Bearer <token>`.

Notas sobre `requiereCambioClave`:

- Se calcula como `passwordEncoder.matches("mycar", usuarioDTO.getClave())`, comparando el texto “mycar” contra el hash
  almacenado. Si coincide, se exige rotación (el usuario tiene la clave por defecto).

---

## Validación de JWT en endpoints protegidos

Para rutas protegidas (todas excepto `POST /api/auth/login`):

- Spring Security (Resource Server) intercepta la petición.
- Extrae el token del header `Authorization: Bearer ...`.
- `JwtDecoder` (Nimbus) valida firma y fechas usando la clave HMAC configurada.
- Si el token es válido y no está expirado, se crea una autenticación (`JwtAuthenticationToken`) marcada como
  autenticada y la request pasa al controlador.

Importante:

- El JWT que emite `JwtUtil` no incluye authorities/scopes. Aun así, las reglas actuales sólo exigen `authenticated()` y
  no roles concretos, por lo que funciona.
- Si en el futuro se requiere control por roles, habrá que mapear claims a authorities (ver Recomendaciones).

---

## Endpoints y acceso

- Público:
    - `POST /api/auth/login`

- Requiere autenticación (Bearer JWT):
    - `POST /api/auth/cambiar-clave/{id}`
    - Cualquier otro endpoint no listado como público en `defaultSecurityFilterChain`.

- Authorization Server (si se usa):
    - Endpoints estándar de OAuth2/OIDC expuestos por `OAuth2AuthorizationServerConfiguration` (p. ej.,
      `/oauth2/authorize`, `/oauth2/token`). Actualmente no están integrados en el flujo de login del `AuthController`.

---

## Ejemplos prácticos (curl)

1) Login

```bash
curl -s -X POST \
  -H "Content-Type: application/json" \
  -d '{"nombreUsuario":"alice","clave":"password"}' \
  http://localhost:8080/api/auth/login
```

Respuesta típica

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "requiereCambioClave": false
}
```

2) Usar el token en un endpoint protegido (cambiar clave)

```bash
TOKEN="<pega_aqui_el_token>"
curl -s -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${TOKEN}" \
  -d '{
        "claveActual":"password",
        "claveNueva":"nuevaSegura123",
        "confirmarClave":"nuevaSegura123"
      }' \
  http://localhost:8080/api/auth/cambiar-clave/123
```

---

## Consideraciones y bordes

- Expiración del token: 1 hora (`exp`). Peticiones con token expirado recibirán 401 (no autenticado).
- CSRF deshabilitado: correcto para APIs stateless con JWT. Si en el futuro se exponen formularios/sesiones, revisar.
- Autoridades en JWT: el token no contiene claims de rol/alcances. No impacta mientras sólo se requiera
  `authenticated()`. Si se pasan a exigir roles, habrá que incorporarlos al token y mapearlos.
- Contraseñas: Bcrypt para almacenar/verificar. La detección de “clave por defecto” compara contra el texto literal
  “mycar”. Si un usuario elige “mycar” voluntariamente, también disparará el cambio obligatorio (puede ser deseado o
  no).
- Clave secreta: está embebida en código tanto en `JwtUtil` como en el `JwtDecoder`. Esto complica la rotación y es
  sensible desde el punto de vista de seguridad de secretos.

---

## Observaciones de diseño actuales

- Filtro `JwtRequestFilter` no integrado: aunque existe, `defaultSecurityFilterChain` confía en
  `oauth2ResourceServer().jwt()` y no añade el filtro custom. Mantener ambos enfoques a la vez puede inducir confusión o
  inconsistencias (por ejemplo, el filtro custom evita `/auth`, pero los endpoints reales son `/api/auth/...`).

- Doble `UserDetailsService`: hay dos beans `@Service` (`CustomUserDetailsService` y `UserDetailsServiceImpl`) con la
  misma funcionalidad. Puede provocar ambigüedades si inyectas `UserDetailsService` por interfaz en otros puntos.

- Mezcla Authorization Server + login propio: la app tiene configurado Spring Authorization Server (con
  `RegisteredClient` en memoria), pero el flujo real de login usa un endpoint custom que emite JWT con JJWT. Son dos
  paradigmas distintos coexistiendo. No es “incorrecto”, pero conviene aclarar cuál es el camino soportado en producción
  o unificarlos.

- Compatibilidad de firmas JWT: `JwtUtil` (JJWT) firma con HS256 usando una secret key. `JwtDecoder` (Nimbus) valida con
  la misma clave. Esto es válido; sólo hay que asegurar que ambas claves se mantengan idénticas y seguras.

---

## Recomendaciones de mejora

1) Unificar el enfoque de validación de JWT
    - O seguir exclusivamente con `oauth2ResourceServer().jwt()` (recomendado) y eliminar el `JwtRequestFilter` custom,
      o
    - Integrar el `JwtRequestFilter` y desactivar el resource server JWT. Mantener ambos a la vez añade complejidad
      innecesaria.

2) Eliminar la duplicación de `UserDetailsService`
    - Conserva una sola implementación (`CustomUserDetailsService`) o renómbralas para usos distintos si es intencional.
      Evita tener dos beans del mismo tipo con la misma función.

3) Externalizar la clave secreta
    - Mover la secret a configuración (`application.properties`/variables de entorno) y cargarla con `@Value` o
      `Environment`.
    - Documentar rotación y longitud mínima (para HS256, ≥ 256 bits de entropía efectiva).

4) Incluir claims de rol/autoridades en el JWT (si habrá autorización por roles)
    - Añadir, por ejemplo, un claim `roles: ["ROLE_ADMIN", ...]` al crear el token.
    - Configurar un `JwtAuthenticationConverter` para mapear el claim a `GrantedAuthority`.

5) Alinear rutas del filtro (si se mantiene)
    - Si se usa `JwtRequestFilter`, alinear la exclusión a `/api/auth/**` para evitar procesar el login/cambio de clave.

6) Considerar refresh tokens o reemisión
    - Con expiración de 1 hora, decide si el cliente debe re-loguearse o si ofreces un endpoint de refresh.

7) Test de integración
    - Añadir tests que verifiquen: login OK/KO, token expirado, token manipulado, acceso a rutas públicas/protegidas y
      por rol (si aplica).

---

## Resumen

- Login propio emite un JWT HS256 con `JwtUtil` tras autenticar con `AuthenticationManager` (BCrypt +
  `CustomUserDetailsService`).
- El Resource Server valida ese JWT con `oauth2ResourceServer().jwt()` y un `JwtDecoder` con la misma secret.
- Rutas: `POST /api/auth/login` es pública; `POST /api/auth/cambiar-clave/**` y el resto están protegidas.
- Hay componentes adicionales (Authorization Server, filtro custom, doble `UserDetailsService`) que conviene simplificar
  o alinear con el flujo elegido.

Con estos puntos tienes una imagen completa de cómo fluye la autenticación y la autorización en el backend y qué
aspectos conviene pulir de cara a producción.
