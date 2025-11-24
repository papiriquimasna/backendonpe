# 📸 Guía Visual: Obtener Contraseña de Gmail

## 🔐 Paso a Paso con Capturas

### PASO 1: Ir a Seguridad de Google

```
🌐 URL: https://myaccount.google.com/security
```

1. Inicia sesión en tu cuenta de Gmail
2. Ve a la sección de Seguridad

---

### PASO 2: Activar Verificación en 2 Pasos

**Busca esta sección:**
```
┌─────────────────────────────────────┐
│ Cómo accedes a Google               │
├─────────────────────────────────────┤
│ Verificación en dos pasos           │
│ ○ Desactivada                       │
│                                     │
│ [Comenzar] →                        │
└─────────────────────────────────────┘
```

**Sigue el asistente:**
1. Ingresa tu número de teléfono
2. Recibirás un código SMS
3. Ingresa el código
4. Confirma la activación

**Resultado:**
```
┌─────────────────────────────────────┐
│ Verificación en dos pasos           │
│ ● Activada                          │
└─────────────────────────────────────┘
```

---

### PASO 3: Generar Contraseña de Aplicación

**Ahora verás una nueva opción:**
```
┌─────────────────────────────────────┐
│ Contraseñas de aplicaciones         │
│                                     │
│ [Ir a Contraseñas de aplicaciones] →│
└─────────────────────────────────────┘
```

**Click en "Contraseñas de aplicaciones"**

---

### PASO 4: Crear Nueva Contraseña

**Verás esta pantalla:**
```
┌─────────────────────────────────────┐
│ Contraseñas de aplicaciones         │
├─────────────────────────────────────┤
│ Seleccionar app:                    │
│ [Correo ▼]                          │
│                                     │
│ Seleccionar dispositivo:            │
│ [Otro (nombre personalizado) ▼]    │
│                                     │
│ Nombre: [________________]          │
│                                     │
│ [GENERAR]                           │
└─────────────────────────────────────┘
```

**Completa:**
1. App: **Correo**
2. Dispositivo: **Otro (nombre personalizado)**
3. Nombre: **Sistema Spring Boot**
4. Click **GENERAR**

---

### PASO 5: Copiar la Contraseña

**Verás algo así:**
```
┌─────────────────────────────────────┐
│ Tu contraseña de aplicación         │
├─────────────────────────────────────┤
│                                     │
│   abcd efgh ijkl mnop               │
│                                     │
│ [Copiar]                            │
│                                     │
│ ⚠️ Esta es la única vez que verás  │
│    esta contraseña. Guárdala en un  │
│    lugar seguro.                    │
│                                     │
│ [Listo]                             │
└─────────────────────────────────────┘
```

**¡IMPORTANTE!** 
- Click en **Copiar**
- Guarda esta contraseña
- No la compartas con nadie
- Solo la verás una vez

---

### PASO 6: Configurar en Spring Boot

**Abre:** `src/main/resources/application.properties`

**Pega tu información:**
```properties
# Reemplaza con TUS datos
spring.mail.username=tu-email@gmail.com
spring.mail.password=abcd efgh ijkl mnop
```

**Ejemplo real:**
```properties
spring.mail.username=juanperez@gmail.com
spring.mail.password=abcd efgh ijkl mnop
```

---

## ✅ Verificar que Funciona

### 1. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

### 2. Registrar usuario (Postman)
```json
POST http://localhost:8080/api/auth/registro

{
  "nombres": "Juan",
  "apellidos": "Pérez",
  "dni": "12345678",
  "dniDigitoVerificador": "9",
  "correo": "juanperez@gmail.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "123456"
}
```

### 3. Revisar logs
**✅ Éxito:**
```
✅ Email enviado exitosamente a: juanperez@gmail.com
```

**❌ Error:**
```
❌ Error al enviar email: Authentication failed
```

### 4. Revisar tu email
```
┌─────────────────────────────────────┐
│ 📧 Bandeja de Entrada               │
├─────────────────────────────────────┤
│ Sistema de Registro                 │
│ Código de Verificación - Sistema... │
│ Hace 1 minuto                       │
└─────────────────────────────────────┘
```

**Contenido del email:**
```
Asunto: Código de Verificación - Sistema de Registro

¡Hola!

Tu código de verificación es: 123456

Este código expira en 15 minutos.

Si no solicitaste este código, ignora este mensaje.

Saludos,
Sistema de Registro
```

---

## 🔍 Solución de Problemas

### ❌ "No veo Contraseñas de aplicaciones"
**Causa:** No activaste la verificación en 2 pasos
**Solución:** Completa el PASO 2 primero

### ❌ "Authentication failed"
**Causa:** Contraseña incorrecta o usaste tu contraseña normal
**Solución:** 
- Genera una nueva contraseña de aplicación
- Cópiala exactamente como aparece
- NO uses tu contraseña normal de Gmail

### ❌ "No recibo el email"
**Revisa:**
1. ✅ Carpeta de SPAM
2. ✅ Carpeta de Promociones
3. ✅ Que el email esté bien escrito
4. ✅ Espera 1-2 minutos

### ❌ "Could not connect to SMTP host"
**Causa:** Firewall o antivirus
**Solución:**
- Desactiva temporalmente el antivirus
- Verifica tu conexión a internet
- Intenta con otro WiFi

---

## 📱 Ejemplo Completo

**Tu configuración final debería verse así:**

```properties
spring.application.name=proyecto

# Database H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=miClaveSecretaSuperSeguraParaJWT2024ProyectoPeruano123456789
jwt.expiration=86400000

# Email - Gmail (REEMPLAZA CON TUS DATOS)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=juanperez@gmail.com
spring.mail.password=abcd efgh ijkl mnop
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000

# File upload
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
```

---

## 🎉 ¡Listo!

Ahora tu aplicación puede enviar emails reales a cualquier dirección de correo.

**Flujo completo:**
1. Usuario se registra con su email
2. Sistema genera código de 6 dígitos
3. Sistema envía email con el código
4. Usuario recibe el email
5. Usuario ingresa el código
6. Sistema verifica y completa el registro

**¡Todo automático!** 🚀
