# 🚀 Mailtrap en 2 Minutos

## ¿Por qué Mailtrap?

- ✅ **Gratis** para desarrollo
- ✅ **Sin configuración complicada** (no necesitas 2FA ni contraseñas de aplicación)
- ✅ **2 minutos** de configuración
- ✅ **Ves todos los emails** en un dashboard bonito
- ✅ **No se envían emails reales** (perfecto para pruebas)

---

## 📝 Paso a Paso

### 1️⃣ Crear Cuenta (30 segundos)

```
🌐 Ve a: https://mailtrap.io/
```

1. Click en **"Sign Up"** (arriba a la derecha)
2. Opciones:
   - Registrarte con Google (más rápido)
   - O con tu email
3. Confirma tu email si es necesario

---

### 2️⃣ Obtener Credenciales (30 segundos)

Una vez dentro verás algo así:

```
┌─────────────────────────────────────────┐
│ Email Testing                           │
├─────────────────────────────────────────┤
│ My Inbox                                │
│ ○ 0 messages                            │
│                                         │
│ [SMTP Settings] [HTTP API] [Info]      │
└─────────────────────────────────────────┘
```

1. Click en **"SMTP Settings"**
2. En el dropdown, selecciona **"Spring"** o **"Java"**
3. Verás:

```
Host: sandbox.smtp.mailtrap.io
Port: 2525
Username: 1a2b3c4d5e6f7g
Password: 9h8i7j6k5l4m3n
```

4. **Copia el Username y Password**

---

### 3️⃣ Configurar en tu Proyecto (1 minuto)

Abre: `src/main/resources/application.properties`

**Busca la sección de Email y reemplaza:**

```properties
# Email - Mailtrap
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=1a2b3c4d5e6f7g
spring.mail.password=9h8i7j6k5l4m3n
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

**Reemplaza:**
- `1a2b3c4d5e6f7g` con tu username de Mailtrap
- `9h8i7j6k5l4m3n` con tu password de Mailtrap

---

### 4️⃣ Probar (30 segundos)

**Ejecutar:**
```bash
mvn spring-boot:run
```

**En Postman:**
```
POST http://localhost:8080/api/auth/registro
```

**Body:**
```json
{
  "nombres": "Test",
  "apellidos": "Mailtrap",
  "dni": "11111111",
  "dniDigitoVerificador": "1",
  "correo": "test@example.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "111111"
}
```

**Resultado:**
```
✅ Email enviado exitosamente a: test@example.com
```

---

### 5️⃣ Ver el Email

1. Ve a tu navegador con Mailtrap abierto
2. Refresca la página
3. Verás el email en tu inbox:

```
┌─────────────────────────────────────────┐
│ My Inbox                                │
├─────────────────────────────────────────┤
│ ● Sistema de Registro                   │
│   Código de Verificación - Sistema...  │
│   Hace 1 segundo                        │
└─────────────────────────────────────────┘
```

4. Click en el email
5. Verás el contenido completo con el código de 6 dígitos
6. Copia el código
7. Úsalo en Postman para verificar

---

## 🎯 Ejemplo Completo

### application.properties
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

# Email - Mailtrap (REEMPLAZA CON TUS CREDENCIALES)
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=1a2b3c4d5e6f7g
spring.mail.password=9h8i7j6k5l4m3n
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=false
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000

# File upload
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
```

---

## 📸 Capturas de Pantalla

### Dashboard de Mailtrap
```
┌─────────────────────────────────────────────────┐
│ Mailtrap                                        │
├─────────────────────────────────────────────────┤
│ Inboxes                                         │
│ ├─ My Inbox (0)                                 │
│                                                 │
│ Email Preview:                                  │
│ ┌─────────────────────────────────────────────┐ │
│ │ From: tu-email@gmail.com                    │ │
│ │ To: test@example.com                        │ │
│ │ Subject: Código de Verificación - Sistema...│ │
│ │                                             │ │
│ │ ¡Hola!                                      │ │
│ │                                             │ │
│ │ Tu código de verificación es: 123456       │ │
│ │                                             │ │
│ │ Este código expira en 15 minutos.          │ │
│ └─────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────┘
```

---

## ✅ Ventajas de Mailtrap

### Para Desarrollo
- ✅ No necesitas configurar Gmail
- ✅ No necesitas verificación en 2 pasos
- ✅ No necesitas contraseñas de aplicación
- ✅ Ves HTML y texto plano
- ✅ Puedes probar con cualquier email (no tiene que existir)
- ✅ No llenas tu bandeja de entrada con emails de prueba

### Características
- ✅ Inbox ilimitado
- ✅ 500 emails/mes gratis
- ✅ Retención de 1 mes
- ✅ API REST
- ✅ Webhooks
- ✅ Análisis de spam

---

## 🔄 Cambiar a Gmail Después

Cuando quieras usar Gmail real en producción, solo cambia:

```properties
# Producción - Gmail
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu-email@gmail.com
spring.mail.password=tu-contraseña-de-aplicacion
```

---

## 🆘 Problemas Comunes

### No veo el email en Mailtrap
- Refresca la página
- Verifica que copiaste bien username y password
- Revisa los logs de la aplicación

### Error: "Authentication failed"
- Verifica username y password de Mailtrap
- Asegúrate de usar `sandbox.smtp.mailtrap.io`
- Puerto debe ser `2525`

### Error: "Could not connect"
- Verifica tu conexión a internet
- Mailtrap puede estar en mantenimiento (raro)

---

## 🎉 ¡Listo!

**Tiempo total: 2 minutos**

Ahora puedes:
- ✅ Registrar usuarios
- ✅ Ver los emails con códigos
- ✅ Probar todo el flujo
- ✅ Sin complicaciones de Gmail

**¡A desarrollar!** 🚀
