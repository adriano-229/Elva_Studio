# Patrones de diseño identificados en el framework de seguridad utilizado: *Spring Security*

Este documento presenta los principales **patrones de diseño** utilizados por *Spring Security* y muestra cómo se
reflejan en nuestro proyecto.

---

## 1) Strategy

**Idea:**  
Define una familia de algoritmos intercambiables. El cliente programa contra una interfaz, y en tiempo de ejecución se
elige la implementación concreta.

**En Spring Security:**  
Los componentes de autenticación y autorización están construidos bajo este patrón:  
`UserDetailsService`, `PasswordEncoder`, `AuthenticationProvider`, `AccessDecisionManager`, entre otros.  
Cada uno representa una estrategia configurable sin alterar el flujo general.

**En el proyecto:**

- `PasswordEncoder` configurado como `BCryptPasswordEncoder` en `SecurityConfig`, podría ser un encoder diferente.
- `CustomUserDetailsService` implementa `UserDetailsService` para definir cómo se cargan usuarios y roles desde la base.

---

## 2) Chain of Responsibility

**Idea:**  
Una solicitud atraviesa una cadena de manejadores; cada uno decide si procesar, transformar o delegar al siguiente.

**Evidencia:**  
El `SecurityFilterChain` configurado en `SecurityConfig` con `HttpSecurity` es una aplicación directa del patrón: una
secuencia de filtros Servlet que manejan autenticación, autorización, excepciones, etc.

---

## 3) Proxy

**Idea:**  
Un proxy envuelve al objeto real para ejecutar lógica transversal antes o después de un método (por ejemplo, seguridad,
logs, transacciones).

**En Spring Security:**  
Las anotaciones como `@PreAuthorize` funcionan gracias a proxies AOP que interceptan llamadas a métodos antes de su
ejecución.

**En el proyecto:**

- En `LoanService`, los métodos críticos (`save`, `update`, `deleteById`) usan `@PreAuthorize`.

---

## 4) Builder

**Idea:**  
Permite construir objetos complejos de forma incremental y legible, como si fuera un pequeño “lenguaje interno”.

**Evidencia**  
`HttpSecurity` utiliza un *builder pattern* con una API fluida para configurar reglas: autorización, login/logout, CSRF,
etc.  
El método `http.build()` crea finalmente el `SecurityFilterChain`.

---

## 5) Adapter

**Idea:**  
Convierte la interfaz de una clase en otra que el cliente espera, sin modificar la clase original.

**Evidencia:**  
Nuestro dominio (`User`) no implementa `UserDetails` directamente.  
`CustomUserDetailsService` actúa como adaptador, transformando `User` en una instancia compatible con Spring (
`org.springframework.security.core.userdetails.User`).

---

## 6) Facade

**Idea:**  
Ofrece una interfaz unificada y simple para interactuar con un subsistema complejo.

**En Spring Security:**

- `AuthenticationManager` funciona como fachada frente a múltiples `AuthenticationProvider`.
- `HttpSecurity` también cumple ese rol al agrupar filtros, reglas y configuraciones.

**En el proyecto:**
`AuthenticationManager` se obtiene desde `AuthenticationConfiguration`, ocultando la complejidad interna.

---

## 7) Template Method

**Idea:**  
Define el esqueleto de un algoritmo en una clase base y permite redefinir pasos concretos en subclases o puntos de
extensión.

**En Spring Security:**  
Clases como `AbstractHttpConfigurer` o `AbstractAuthenticationProcessingFilter` usan este patrón.  
Incluso cuando no las extendemos, nuestra configuración invoca esos métodos plantilla al habilitar o deshabilitar
comportamientos.

**En el proyecto:**
`AbstractHttpConfigurer::disable` usado para desactivar CSRF.

---

## 8) Dependency Injection (DIP)

**Idea:**  
Los componentes no crean sus dependencias; el contenedor las inyecta automáticamente.

**En Spring Security:**  
Beans como `PasswordEncoder`, `SecurityFilterChain`, `AuthenticationManager` y `CustomUserDetailsService` son
gestionados por Spring e inyectados donde se necesiten.

**En el proyecto:**
Inyección por constructor en `SecurityConfig` y `CustomUserDetailsService`.

---

## 9) Guardas en la vista *(no GoF, pero relevante)*

**Idea:**  
Aplicar restricciones visuales según los permisos del usuario mejora la experiencia y refuerza la coherencia del
sistema.

**En Spring Security:**  
El dialecto de Thymeleaf (`sec:authorize`) muestra u oculta secciones del HTML según el rol.

**Evidencia:**  
`templates/home.html` muestra paneles distintos para `ADMIN` y `USUARIO`.

---

## Conclusión

Spring Security es un conjunto coordinado de **patrones de diseño** que trabajan para ofrecer una seguridad modular y
extensible. La combinación de estos patrones habilita una protección en capas: desde los filtros HTTP, pasando por la
lógica de negocio, hasta la UI.