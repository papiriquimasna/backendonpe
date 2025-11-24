# 📧 Solución Simple: Email sin Contraseñas de Aplicación

## 🎯 Opción 1: Mailtrap (RECOMENDADO - 2 minutos)

Mailtrap captura los emails sin enviarlos realmente. Perfecto para desarrollo.

### Paso 1: Crear cuenta en Mailtrap
1. Ve a: https://mailtrap.io/
2. Click en **"Sign Up"** (Registrarse)
3. Usa tu email o Google para registrarte
4. Es **GRATIS** para desarrollo

### Paso 2: Obtener credenciales
1. Una vez dentro, verás tu **Inbox**
2. Click en tu inbox (generalmente "My Inbox")
3. Ve a la pestaña **"SMTP Settings"**
4. Selecciona **"Spring"** en el dropdown
5. Verás algo como:

```
Host: sandbox.smtp.mailtrap.io
Port: 2525
Username: 1a2b3c4d5e6f7g
Password: 1a2b3c4d5e6f7g
```

### Paso 3: Configurar en tu proyecto
Abre `src/main/resources/application.properties` y reemplaza la sección de email:

```properties
# Email - Mailtrap (para desarrollo)
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=TU_USERNAME_MAILTRAP
spring.mail.password=TU_PASSWORD_MAILTRAP
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Paso 4: Probar
1. Ejecuta: `mvn spring-boot:run`
2. Registra un usuario en Postman
3. Ve a tu inbox de Mailtrap
4. ¡Verás el email con el código!

**Ventajas:**
- ✅ No necesitas configurar Gmail
- ✅ Ves todos los emails en un solo lugar
- ✅ No se envían emails reales (perfecto para pruebas)
- ✅ Configuración en 2 minutos

---

## 🎯 Opción 2: Gmail sin Verificación en 2 Pasos

Si quieres usar Gmail pero no puedes activar la verificación en 2 pasos:

### ⚠️ ADVERTENCIA
Esta opción es **menos segura** y Google puede bloquearla. Solo para pruebas locales.

### Paso 1: Habilitar acceso de apps menos seguras
1. Ve a: https://myaccount.google.com/lesssecureapps
2. Activa **"Permitir aplicaciones menos seguras"**

### Paso 2: Configurar
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=tu-email@gmail.com
spring.mail.password=tu-contraseña-normal-de-gmail
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

**Nota:** Google puede bloquear esto y pedirte que uses contraseñas de aplicación.

---

## 🎯 Opción 3: Outlook/Hotmail (Más Simple)

Outlook es más permisivo que Gmail:

### Configuración
```properties
spring.mail.host=smtp-mail.outlook.com
spring.mail.port=587
spring.mail.username=tu-email@outlook.com
spring.mail.password=tu-contraseña-normal
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

## 🎯 Opción 4: Crear cuenta Gmail nueva

Si no puedes activar 2FA en tu cuenta actual:

### Paso 1: Crear cuenta Gmail nueva
1. Ve a: https://accounts.google.com/signup
2. Crea una cuenta nueva (solo para este proyecto)
3. Usa un número de teléfono para verificación

### Paso 2: Activar verificación en 2 pasos
1. Ve a: https://myaccount.google.com/security
2. Activa "Verificación en dos pasos"
3. Ahora sí verás "Contraseñas de aplicaciones"

### Paso 3: Generar contraseña de aplicación
1. En Seguridad → "Contraseñas de aplicaciones"
2. Genera una contraseña
3. Úsala en tu proyecto

---

## 📊 Comparación de Opciones

| Opción | Dificultad | Tiempo | Emails Reales | Recomendado |
|--------|-----------|--------|---------------|-------------|
| Mailtrap | ⭐ Fácil | 2 min | ❌ No | ✅ SÍ (desarrollo) |
| Outlook | ⭐⭐ Media | 5 min | ✅ Sí | ✅ SÍ |
| Gmail nuevo | ⭐⭐⭐ Media | 10 min | ✅ Sí | ⚠️ Si necesitas emails reales |
| Gmail actual | ⭐⭐⭐⭐ Difícil | 15 min | ✅ Sí | ⚠️ Si ya tienes 2FA |

---

## 🚀 Mi Recomendación

### Para Desarrollo y Pruebas:
**Usa Mailtrap** (Opción 1)
- Es gratis
- Configuración en 2 minutos
- Ves todos los emails en un dashboard
- No necesitas configurar nada en Gmail

### Para Producción:
**Usa Gmail con 2FA** o un servicio profesional como:
- SendGrid
- Amazon SES
- Mailgun

---

## 📝 Configuración Completa con Mailtrap

Aquí está tu `application.properties` completo usando Mailtrap:

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

# Email - Mailtrap (Reemplaza con tus credenciales de Mailtrap)
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=TU_USERNAME_MAILTRAP
spring.mail.password=TU_PASSWORD_MAILTRAP
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

## 🎬 Video Tutorial de Mailtrap

1. Ve a: https://mailtrap.io/
2. Regístrate gratis
3. Copia las credenciales SMTP
4. Pégalas en `application.properties`
5. ¡Listo!

**Tiempo total: 2 minutos** ⏱️

---

## ✅ Verificar que Funciona

### 1. Ejecutar
```bash
mvn spring-boot:run
```

### 2. Registrar usuario
```json
POST http://localhost:8080/api/auth/registro

{
  "nombres": "Test",
  "apellidos": "Usuario",
  "dni": "12345678",
  "dniDigitoVerificador": "9",
  "correo": "test@example.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "123456"
}
```

### 3. Ver el email en Mailtrap
1. Ve a tu inbox de Mailtrap
2. Verás el email con el código
3. Copia el código
4. Úsalo para verificar

---

## 🎉 ¡Listo!

Con Mailtrap no necesitas:
- ❌ Verificación en 2 pasos
- ❌ Contraseñas de aplicación
- ❌ Configuración complicada
- ❌ Preocuparte por seguridad de Gmail

Solo necesitas:
- ✅ Cuenta gratis en Mailtrap
- ✅ Copiar 2 valores (username y password)
- ✅ Pegar en application.properties
- ✅ ¡Funciona!
