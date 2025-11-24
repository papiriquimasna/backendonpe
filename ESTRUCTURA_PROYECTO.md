# 📁 Estructura del Proyecto

```
proyecto/
│
├── src/main/java/com/proyecto/proyecto/
│   │
│   ├── config/
│   │   └── DataInitializer.java          # Crea superadmin inicial
│   │
│   ├── controller/
│   │   ├── AuthController.java           # Registro, login, verificación
│   │   ├── UsuarioController.java        # Perfil, foto, cambio de roles
│   │   ├── ChatController.java           # Chatbot
│   │   └── ReclamacionController.java    # Libro de reclamaciones
│   │
│   ├── dto/
│   │   ├── RegistroRequest.java          # Datos para registro
│   │   ├── VerificarCodigoRequest.java   # Verificación de código
│   │   ├── LoginRequest.java             # Datos para login
│   │   ├── LoginResponse.java            # Respuesta con token
│   │   ├── UsuarioResponse.java          # Datos del usuario
│   │   ├── ChatRequest.java              # Mensaje del chat
│   │   ├── ReclamacionRequest.java       # Datos de reclamación
│   │   ├── CambiarRoleRequest.java       # Cambio de role
│   │   └── MessageResponse.java          # Respuesta genérica
│   │
│   ├── model/
│   │   ├── Usuario.java                  # Entidad Usuario
│   │   ├── MensajeChat.java              # Entidad Mensaje Chat
│   │   └── Reclamacion.java              # Entidad Reclamación
│   │
│   ├── repository/
│   │   ├── UsuarioRepository.java        # Repositorio Usuario
│   │   ├── MensajeChatRepository.java    # Repositorio Chat
│   │   └── ReclamacionRepository.java    # Repositorio Reclamaciones
│   │
│   ├── security/
│   │   ├── JwtUtil.java                  # Utilidad JWT
│   │   ├── JwtAuthenticationFilter.java  # Filtro de autenticación
│   │   └── SecurityConfig.java           # Configuración de seguridad
│   │
│   ├── service/
│   │   ├── UsuarioService.java           # Lógica de usuarios
│   │   ├── EmailService.java             # Envío de emails
│   │   ├── ChatService.java              # Lógica del chatbot
│   │   └── ReclamacionService.java       # Lógica de reclamaciones
│   │
│   └── ProyectoApplication.java          # Clase principal
│
├── src/main/resources/
│   └── application.properties            # Configuración de la app
│
├── Postman_Collection.json               # Colección de Postman
├── README_API.md                         # Documentación completa
├── EJEMPLOS_PRUEBA.md                    # Ejemplos de uso
├── INICIO_RAPIDO.md                      # Guía de inicio rápido
├── ESTRUCTURA_PROYECTO.md                # Este archivo
└── pom.xml                               # Dependencias Maven
```

## 🎯 Descripción de Componentes

### Controllers (Controladores)
Manejan las peticiones HTTP y devuelven respuestas.

- **AuthController**: Endpoints públicos (registro, login, verificación)
- **UsuarioController**: Gestión de perfil y roles (requiere autenticación)
- **ChatController**: Interacción con el chatbot (requiere autenticación)
- **ReclamacionController**: Libro de reclamaciones (requiere autenticación)

### DTOs (Data Transfer Objects)
Objetos para transferir datos entre cliente y servidor.

- **Request**: Datos que envía el cliente
- **Response**: Datos que devuelve el servidor

### Models (Modelos)
Entidades de la base de datos.

- **Usuario**: Información del usuario, DNI, PIN, role
- **MensajeChat**: Conversaciones del chatbot
- **Reclamacion**: Quejas, reclamos y sugerencias

### Repositories (Repositorios)
Interfaces para acceder a la base de datos.

### Security (Seguridad)
Componentes de autenticación y autorización.

- **JwtUtil**: Genera y valida tokens JWT
- **JwtAuthenticationFilter**: Intercepta peticiones y valida tokens
- **SecurityConfig**: Configura qué endpoints requieren autenticación

### Services (Servicios)
Lógica de negocio de la aplicación.

- **UsuarioService**: Registro, login, perfil, roles
- **EmailService**: Envío de códigos de verificación
- **ChatService**: Respuestas automáticas del bot
- **ReclamacionService**: Gestión de reclamaciones

## 🔐 Flujo de Autenticación

```
1. Usuario → POST /api/auth/registro
   ↓
2. Sistema → Genera código y envía email
   ↓
3. Usuario → POST /api/auth/verificar-codigo
   ↓
4. Sistema → Valida código y genera JWT
   ↓
5. Usuario → Guarda token
   ↓
6. Usuario → Usa token en header "Authorization: Bearer {token}"
   ↓
7. JwtAuthenticationFilter → Valida token en cada petición
   ↓
8. Sistema → Permite o deniega acceso
```

## 📊 Modelo de Datos

### Usuario
```
- id (Long)
- nombres (String)
- apellidos (String)
- dni (String, 8 dígitos, único)
- dniDigitoVerificador (String, 1 dígito)
- correo (String, único)
- distrito (String)
- departamento (String)
- pin (String, 6 dígitos, encriptado)
- fotoPerfil (String)
- role (Enum: USUARIO, ADMINISTRADOR, SUPERADMINISTRADOR)
- codigoVerificacion (String)
- codigoExpiracion (LocalDateTime)
- verificado (Boolean)
- activo (Boolean)
- fechaCreacion (LocalDateTime)
```

### MensajeChat
```
- id (Long)
- usuario (Usuario)
- mensaje (String)
- respuesta (String)
- fechaCreacion (LocalDateTime)
```

### Reclamacion
```
- id (Long)
- usuario (Usuario)
- asunto (String)
- descripcion (String)
- tipo (Enum: QUEJA, RECLAMO, SUGERENCIA)
- estado (Enum: PENDIENTE, EN_PROCESO, RESUELTO, CERRADO)
- respuesta (String)
- fechaCreacion (LocalDateTime)
- fechaRespuesta (LocalDateTime)
```

## 🔧 Tecnologías Utilizadas

- **Spring Boot 3.5.7**: Framework principal
- **Spring Security**: Autenticación y autorización
- **Spring Data JPA**: Acceso a base de datos
- **H2 Database**: Base de datos en memoria
- **JWT (jjwt 0.12.3)**: Tokens de autenticación
- **Lombok**: Reducción de código boilerplate
- **Spring Mail**: Envío de emails
- **Bean Validation**: Validación de datos

## 📝 Endpoints por Funcionalidad

### Públicos (sin autenticación)
- POST `/api/auth/registro`
- POST `/api/auth/verificar-codigo`
- POST `/api/auth/login`

### Autenticados (requieren token)
- GET `/api/usuario/perfil`
- POST `/api/usuario/foto-perfil`
- POST `/api/chat/mensaje`
- GET `/api/chat/historial`
- POST `/api/reclamaciones`
- GET `/api/reclamaciones/mis-reclamaciones`

### Solo Administradores
- GET `/api/reclamaciones/todas`

### Solo SuperAdministradores
- POST `/api/usuario/cambiar-role`

## 🎨 Patrones de Diseño Utilizados

1. **MVC (Model-View-Controller)**: Separación de responsabilidades
2. **DTO Pattern**: Transferencia de datos
3. **Repository Pattern**: Acceso a datos
4. **Service Layer**: Lógica de negocio
5. **Dependency Injection**: Inyección de dependencias
6. **Filter Pattern**: Filtro de autenticación JWT

## 🚀 Extensiones Futuras

Ideas para mejorar el proyecto:

- [ ] Paginación en listados
- [ ] Búsqueda y filtros
- [ ] Notificaciones push
- [ ] Recuperación de contraseña
- [ ] Auditoría de cambios
- [ ] Dashboard de administración
- [ ] Reportes en PDF
- [ ] Integración con servicios externos
- [ ] WebSockets para chat en tiempo real
- [ ] Almacenamiento de archivos en la nube
