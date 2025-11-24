# 🧪 Ejemplos de Prueba - Paso a Paso

## 🎯 Escenario 1: Registro Completo de Usuario

### Paso 1: Registrar Usuario
```bash
POST http://localhost:8080/api/auth/registro
Content-Type: application/json

{
  "nombres": "María Elena",
  "apellidos": "Torres Sánchez",
  "dni": "87654321",
  "dniDigitoVerificador": "5",
  "correo": "maria.torres@example.com",
  "distrito": "San Isidro",
  "departamento": "Lima",
  "pin": "654321"
}
```

**Respuesta esperada:**
```json
{
  "mensaje": "Registro iniciado. Revisa tu correo para el código de verificación."
}
```

**Importante:** Revisa la consola de la aplicación para ver el código si no configuraste el email.

### Paso 2: Verificar con el Código
```bash
POST http://localhost:8080/api/auth/verificar-codigo
Content-Type: application/json

{
  "correo": "maria.torres@example.com",
  "codigo": "123456"
}
```

**Respuesta esperada:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer",
  "usuario": {
    "id": 1,
    "nombres": "María Elena",
    "apellidos": "Torres Sánchez",
    "dni": "87654321",
    "correo": "maria.torres@example.com",
    "distrito": "San Isidro",
    "departamento": "Lima",
    "fotoPerfil": null,
    "role": "USUARIO"
  }
}
```

**Guarda el token** para usarlo en las siguientes peticiones.

---

## 🎯 Escenario 2: Login y Ver Perfil

### Login
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "dni": "87654321",
  "pin": "654321"
}
```

### Ver Mi Perfil
```bash
GET http://localhost:8080/api/usuario/perfil
Authorization: Bearer {tu-token-aqui}
```

---

## 🎯 Escenario 3: Libro de Reclamaciones

### Crear una QUEJA
```bash
POST http://localhost:8080/api/reclamaciones
Authorization: Bearer {tu-token-aqui}
Content-Type: application/json

{
  "asunto": "Demora en la atención",
  "descripcion": "Esperé más de 2 horas para ser atendido en la oficina de Miraflores. El servicio fue muy lento y no había suficiente personal.",
  "tipo": "QUEJA"
}
```

### Crear un RECLAMO
```bash
POST http://localhost:8080/api/reclamaciones
Authorization: Bearer {tu-token-aqui}
Content-Type: application/json

{
  "asunto": "Cobro indebido en mi cuenta",
  "descripcion": "Se realizó un cargo de S/. 150.00 que no corresponde a ningún servicio que haya solicitado. Solicito la devolución inmediata.",
  "tipo": "RECLAMO"
}
```

### Crear una SUGERENCIA
```bash
POST http://localhost:8080/api/reclamaciones
Authorization: Bearer {tu-token-aqui}
Content-Type: application/json

{
  "asunto": "Mejorar horarios de atención",
  "descripcion": "Sugiero ampliar el horario de atención hasta las 8 PM para personas que trabajan.",
  "tipo": "SUGERENCIA"
}
```

### Ver Mis Reclamaciones
```bash
GET http://localhost:8080/api/reclamaciones/mis-reclamaciones
Authorization: Bearer {tu-token-aqui}
```

---

## 🎯 Escenario 4: Chatbot

### Saludar
```bash
POST http://localhost:8080/api/chat/mensaje
Authorization: Bearer {tu-token-aqui}
Content-Type: application/json

{
  "mensaje": "Hola, buenos días"
}
```

**Respuesta esperada:**
```json
{
  "id": 1,
  "mensaje": "Hola, buenos días",
  "respuesta": "¡Hola! ¿En qué puedo ayudarte hoy?",
  "fechaCreacion": "2024-11-20T10:30:00"
}
```

### Preguntar por Horarios
```bash
POST http://localhost:8080/api/chat/mensaje
Authorization: Bearer {tu-token-aqui}
Content-Type: application/json

{
  "mensaje": "¿Cuál es el horario de atención?"
}
```

### Preguntar por Reclamaciones
```bash
POST http://localhost:8080/api/chat/mensaje
Authorization: Bearer {tu-token-aqui}
Content-Type: application/json

{
  "mensaje": "¿Cómo hago una reclamación?"
}
```

### Ver Historial de Chat
```bash
GET http://localhost:8080/api/chat/historial
Authorization: Bearer {tu-token-aqui}
```

---

## 🎯 Escenario 5: Gestión de Roles (SuperAdmin)

### Primero: Login como SuperAdmin
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "dni": "99999999",
  "pin": "999999"
}
```

### Cambiar Usuario a ADMINISTRADOR
```bash
POST http://localhost:8080/api/usuario/cambiar-role
Authorization: Bearer {token-del-superadmin}
Content-Type: application/json

{
  "usuarioId": 2,
  "nuevoRole": "ADMINISTRADOR"
}
```

### Ver Todas las Reclamaciones (como Admin)
```bash
GET http://localhost:8080/api/reclamaciones/todas
Authorization: Bearer {token-del-admin}
```

---

## 🎯 Escenario 6: Cambiar Foto de Perfil

### Usando Postman:
1. Selecciona el endpoint "Cambiar Foto de Perfil"
2. En Body, selecciona "form-data"
3. Agrega key: `archivo`, type: `File`
4. Selecciona una imagen de tu computadora
5. Envía la petición

### Usando cURL:
```bash
curl -X POST http://localhost:8080/api/usuario/foto-perfil \
  -H "Authorization: Bearer {tu-token}" \
  -F "archivo=@/ruta/a/tu/foto.jpg"
```

---

## 📊 Datos de Prueba Adicionales

### Usuario 2
```json
{
  "nombres": "Carlos Alberto",
  "apellidos": "Rodríguez Vega",
  "dni": "45678912",
  "dniDigitoVerificador": "3",
  "correo": "carlos.rodriguez@example.com",
  "distrito": "Surco",
  "departamento": "Lima",
  "pin": "789012"
}
```

### Usuario 3
```json
{
  "nombres": "Ana Lucía",
  "apellidos": "Mendoza Flores",
  "dni": "78912345",
  "dniDigitoVerificador": "7",
  "correo": "ana.mendoza@example.com",
  "distrito": "Barranco",
  "departamento": "Lima",
  "pin": "345678"
}
```

---

## ⚠️ Errores Comunes y Soluciones

### Error: "El DNI ya está registrado"
**Solución:** Usa un DNI diferente o elimina la base de datos (reinicia la aplicación).

### Error: "El código ha expirado"
**Solución:** El código expira en 15 minutos. Inicia el registro nuevamente.

### Error: "Credenciales inválidas"
**Solución:** Verifica que el DNI y PIN sean correctos.

### Error: "No tienes permisos para cambiar roles"
**Solución:** Solo los SUPERADMINISTRADORES pueden cambiar roles. Usa el usuario admin por defecto (DNI: 99999999, PIN: 999999).

### Error: "Usuario no verificado"
**Solución:** Completa el paso de verificación con el código enviado al correo.

---

## 🔍 Verificar en la Base de Datos

Accede a la consola H2:
```
http://localhost:8080/h2-console
```

Consultas útiles:
```sql
-- Ver todos los usuarios
SELECT * FROM USUARIOS;

-- Ver todas las reclamaciones
SELECT * FROM RECLAMACIONES;

-- Ver mensajes del chat
SELECT * FROM MENSAJES_CHAT;

-- Ver usuarios por role
SELECT nombres, apellidos, dni, role FROM USUARIOS WHERE role = 'ADMINISTRADOR';
```
