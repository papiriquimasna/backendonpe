# API Sistema de Registro y Gestión de Usuarios

## 🚀 Configuración Inicial

### 1. Configurar Email (OBLIGATORIO para envío real)

**📧 Para enviar emails reales:**
1. Lee la guía completa: **`CONFIGURAR_EMAIL_GMAIL.md`**
2. Configura Gmail con contraseña de aplicación
3. Edita `src/main/resources/application.properties`:

```properties
spring.mail.username=tu-email@gmail.com
spring.mail.password=xxxx xxxx xxxx xxxx
```

**⚠️ IMPORTANTE:** 
- Debes usar una **contraseña de aplicación** de Gmail (no tu contraseña normal)
- Sigue los pasos en `CONFIGURAR_EMAIL_GMAIL.md` para generarla
- Sin configuración, la app lanzará error al intentar enviar emails

### 2. Compilar y Ejecutar
```bash
mvnw clean install
mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 📋 Endpoints Disponibles

### 1️⃣ REGISTRO (2 pasos)

#### Paso 1: Iniciar Registro
```
POST /api/auth/registro
```
**Body:**
```json
{
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "dni": "12345678",
  "dniDigitoVerificador": "9",
  "correo": "juan.perez@example.com",
  "distrito": "Miraflores",
  "departamento": "Lima",
  "pin": "123456"
}
```
**Respuesta:** Se envía un código de 6 dígitos al correo.

#### Paso 2: Verificar Código
```
POST /api/auth/verificar-codigo
```
**Body:**
```json
{
  "correo": "juan.perez@example.com",
  "codigo": "123456"
}
```
**Respuesta:** Token JWT + datos del usuario

### 2️⃣ LOGIN
```
POST /api/auth/login
```
**Body:**
```json
{
  "dni": "12345678",
  "pin": "123456"
}
```
**Respuesta:** Token JWT + datos del usuario

### 3️⃣ PERFIL DE USUARIO

#### Ver Mi Perfil
```
GET /api/usuario/perfil
Headers: Authorization: Bearer {token}
```

#### Cambiar Foto de Perfil
```
POST /api/usuario/foto-perfil
Headers: Authorization: Bearer {token}
Body: form-data
  - archivo: [seleccionar archivo]
```

### 4️⃣ LIBRO DE RECLAMACIONES

#### Crear Reclamación
```
POST /api/reclamaciones
Headers: Authorization: Bearer {token}
```
**Body:**
```json
{
  "asunto": "Problema con el servicio",
  "descripcion": "Descripción detallada del problema",
  "tipo": "RECLAMO"
}
```
**Tipos válidos:** QUEJA, RECLAMO, SUGERENCIA

#### Ver Mis Reclamaciones
```
GET /api/reclamaciones/mis-reclamaciones
Headers: Authorization: Bearer {token}
```

#### Ver Todas las Reclamaciones (Solo Admin)
```
GET /api/reclamaciones/todas
Headers: Authorization: Bearer {token}
```

### 5️⃣ CHATBOT

#### Enviar Mensaje
```
POST /api/chat/mensaje
Headers: Authorization: Bearer {token}
```
**Body:**
```json
{
  "mensaje": "Hola, necesito ayuda"
}
```

#### Ver Historial
```
GET /api/chat/historial
Headers: Authorization: Bearer {token}
```

### 6️⃣ GESTIÓN DE ROLES (Solo SuperAdmin)

#### Cambiar Role de Usuario
```
POST /api/usuario/cambiar-role
Headers: Authorization: Bearer {token}
```
**Body:**
```json
{
  "usuarioId": 1,
  "nuevoRole": "ADMINISTRADOR"
}
```
**Roles válidos:** USUARIO, ADMINISTRADOR, SUPERADMINISTRADOR

## 🔐 Autenticación

Todos los endpoints (excepto registro, verificar código y login) requieren el header:
```
Authorization: Bearer {tu-token-jwt}
```

## 📦 Importar en Postman

1. Abre Postman
2. Click en "Import"
3. Selecciona el archivo `Postman_Collection.json`
4. La colección incluye todas las peticiones configuradas
5. El token se guarda automáticamente después del login

## 🗄️ Base de Datos

La aplicación usa H2 (base de datos en memoria) para pruebas.

**Consola H2:** http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (dejar vacío)

## 🧪 Flujo de Prueba Completo

1. **Registrar usuario** → Recibir código por email/consola
2. **Verificar código** → Obtener token
3. **Ver perfil** → Confirmar datos
4. **Cambiar foto** → Subir imagen
5. **Crear reclamación** → Probar libro de reclamaciones
6. **Chatbot** → Enviar mensajes
7. **Cambiar role** → Necesitas ser SUPERADMINISTRADOR

## 📝 Notas Importantes

- El código de verificación expira en 15 minutos
- El PIN debe tener exactamente 6 dígitos
- El DNI debe tener 8 dígitos
- Las fotos se guardan en la carpeta `uploads/`
- Por defecto, todos los usuarios nuevos tienen role USUARIO
