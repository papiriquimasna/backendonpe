# 🚀 Sistema de Registro y Gestión de Usuarios

API REST completa con Spring Boot que incluye registro con verificación por email, autenticación JWT, gestión de perfiles, libro de reclamaciones y chatbot.

---

## ⚡ Inicio Rápido (3 pasos)

### 1️⃣ Configurar Email

**Opción A: Mailtrap (RECOMENDADO - 2 minutos)** ⭐
1. Crea cuenta gratis en: https://mailtrap.io/
2. Copia username y password de SMTP Settings
3. Pégalos en `src/main/resources/application.properties`

📖 **Guía rápida:** [MAILTRAP_RAPIDO.md](MAILTRAP_RAPIDO.md)

**Opción B: Gmail (requiere configuración)**
📖 **Guía completa:** [SOLUCION_EMAIL_SIMPLE.md](SOLUCION_EMAIL_SIMPLE.md)

### 2️⃣ Ejecutar
```bash
mvn spring-boot:run
```

### 3️⃣ Probar
1. Importa `Postman_Collection.json` en Postman
2. Prueba el endpoint de registro
3. Revisa tu email para el código de verificación

📖 **Guía detallada:** [INICIO_RAPIDO.md](INICIO_RAPIDO.md)

---

## ✨ Características

### 🔐 Autenticación
- ✅ Registro con verificación por email (código de 6 dígitos)
- ✅ Login con DNI y PIN de 6 dígitos
- ✅ Autenticación JWT
- ✅ Códigos con expiración de 15 minutos

### 👤 Gestión de Usuarios
- ✅ Perfil de usuario (solo lectura)
- ✅ Cambio de foto de perfil
- ✅ Sistema de roles (Usuario, Administrador, SuperAdministrador)
- ✅ Cambio de roles (solo SuperAdmin)

### 📝 Libro de Reclamaciones
- ✅ Crear reclamaciones (Queja, Reclamo, Sugerencia)
- ✅ Ver mis reclamaciones
- ✅ Ver todas las reclamaciones (solo Admin)

### 💬 Chatbot
- ✅ Enviar mensajes
- ✅ Respuestas automáticas
- ✅ Historial de conversaciones

---

## 📋 Endpoints Principales

### Públicos (sin autenticación)
```
POST /api/auth/registro              - Iniciar registro
POST /api/auth/verificar-codigo      - Verificar código de email
POST /api/auth/login                 - Iniciar sesión
```

### Autenticados (requieren token)
```
GET  /api/usuario/perfil             - Ver mi perfil
POST /api/usuario/foto-perfil        - Cambiar foto
POST /api/reclamaciones              - Crear reclamación
GET  /api/reclamaciones/mis-reclamaciones - Ver mis reclamaciones
POST /api/chat/mensaje               - Enviar mensaje al chatbot
GET  /api/chat/historial             - Ver historial de chat
```

### Solo SuperAdministrador
```
POST /api/usuario/cambiar-role       - Cambiar role de usuario
GET  /api/reclamaciones/todas        - Ver todas las reclamaciones
```

📖 **Documentación completa:** [README_API.md](README_API.md)

---

## 🎯 Flujo de Registro

```
1. Usuario → POST /api/auth/registro
   ↓
2. Sistema → Envía código de 6 dígitos al email
   ↓
3. Usuario → Revisa su email
   ↓
4. Usuario → POST /api/auth/verificar-codigo
   ↓
5. Sistema → Valida código y devuelve token JWT
   ↓
6. Usuario → Usa el token para acceder al sistema
```

---

## 🔑 Usuario SuperAdmin por Defecto

Para probar funciones de administrador:

```
DNI: 99999999
PIN: 999999
```

---

## 📦 Tecnologías

- **Spring Boot 3.5.7** - Framework principal
- **Spring Security** - Autenticación y autorización
- **JWT (jjwt 0.12.3)** - Tokens de autenticación
- **Spring Data JPA** - Acceso a base de datos
- **H2 Database** - Base de datos en memoria
- **Spring Mail** - Envío de emails
- **Lombok** - Reducción de código boilerplate

---

## 📚 Documentación Completa

### 🚀 Para Empezar
- **[INICIO_RAPIDO.md](INICIO_RAPIDO.md)** - Guía de inicio rápido
- **[INDICE_DOCUMENTACION.md](INDICE_DOCUMENTACION.md)** - Índice de toda la documentación

### 📧 Configuración de Email
- **[RESUMEN_CONFIGURACION_EMAIL.md](RESUMEN_CONFIGURACION_EMAIL.md)** - Resumen ejecutivo (3 pasos)
- **[CONFIGURAR_EMAIL_GMAIL.md](CONFIGURAR_EMAIL_GMAIL.md)** - Guía completa
- **[GUIA_VISUAL_GMAIL.md](GUIA_VISUAL_GMAIL.md)** - Guía con capturas visuales
- **[CHECKLIST_EMAIL.md](CHECKLIST_EMAIL.md)** - Lista de verificación
- **[PROBAR_EMAIL.md](PROBAR_EMAIL.md)** - Cómo probar el envío

### 📖 API y Ejemplos
- **[README_API.md](README_API.md)** - Documentación completa de endpoints
- **[EJEMPLOS_PRUEBA.md](EJEMPLOS_PRUEBA.md)** - Ejemplos paso a paso
- **[Postman_Collection.json](Postman_Collection.json)** - Colección de Postman

### 🏗️ Arquitectura
- **[ESTRUCTURA_PROYECTO.md](ESTRUCTURA_PROYECTO.md)** - Estructura del código

---

## 🗄️ Base de Datos H2

Accede a la consola H2 en: http://localhost:8080/h2-console

**Credenciales:**
```
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (dejar vacío)
```

---

## 🧪 Probar con Postman

1. Importa `Postman_Collection.json`
2. Los endpoints están organizados por funcionalidad
3. El token se guarda automáticamente después del login
4. Todas las variables están configuradas

---

## 📝 Campos del Registro

```json
{
  "nombres": "Juan Carlos",
  "apellidos": "Pérez García",
  "dni": "12345678",                    // 8 dígitos
  "dniDigitoVerificador": "9",          // 1 dígito
  "correo": "juan@example.com",
  "distrito": "Miraflores",
  "departamento": "Lima",
  "pin": "123456"                       // 6 dígitos
}
```

---

## 🔒 Seguridad

- ✅ Contraseñas encriptadas con BCrypt
- ✅ Tokens JWT con expiración de 24 horas
- ✅ Códigos de verificación con expiración de 15 minutos
- ✅ Validación de datos con Bean Validation
- ✅ Roles y permisos con Spring Security

---

## 🆘 Solución de Problemas

### No recibo el email
1. Verifica que configuraste correctamente `application.properties`
2. Revisa tu carpeta de SPAM
3. Consulta: [CHECKLIST_EMAIL.md](CHECKLIST_EMAIL.md)

### Error: "Authentication failed"
- Usa una contraseña de aplicación de Gmail (no tu contraseña normal)
- Consulta: [CONFIGURAR_EMAIL_GMAIL.md](CONFIGURAR_EMAIL_GMAIL.md)

### Error: "Value too long for column PIN"
- Ya está solucionado (columna PIN ahora permite 60 caracteres)

---

## 🎉 ¡Todo Listo!

Tu sistema incluye:

✅ Registro con verificación por email  
✅ Login con DNI y PIN  
✅ Gestión de perfiles  
✅ Cambio de foto  
✅ Libro de reclamaciones  
✅ Chatbot funcional  
✅ Sistema de roles  
✅ Colección de Postman lista  
✅ Documentación completa  

---

## 📞 Soporte

Si tienes dudas, consulta:
1. [INDICE_DOCUMENTACION.md](INDICE_DOCUMENTACION.md) - Índice completo
2. [INICIO_RAPIDO.md](INICIO_RAPIDO.md) - Guía de inicio
3. [EJEMPLOS_PRUEBA.md](EJEMPLOS_PRUEBA.md) - Ejemplos prácticos

---

**Desarrollado con Spring Boot 3.5.7** 🚀
