# 📧 Configurar Email con Gmail - Guía Paso a Paso

## 🎯 Opción 1: Usar Gmail con Contraseña de Aplicación (RECOMENDADO)

### Paso 1: Habilitar Verificación en 2 Pasos

1. Ve a tu cuenta de Google: https://myaccount.google.com/
2. En el menú izquierdo, selecciona **"Seguridad"**
3. Busca la sección **"Verificación en dos pasos"**
4. Click en **"Verificación en dos pasos"**
5. Sigue los pasos para activarla (necesitarás tu teléfono)

### Paso 2: Crear Contraseña de Aplicación

1. Una vez activada la verificación en 2 pasos, regresa a **"Seguridad"**
2. Busca **"Contraseñas de aplicaciones"** (aparece después de activar 2FA)
3. Click en **"Contraseñas de aplicaciones"**
4. Puede que te pida tu contraseña de Google nuevamente
5. En "Seleccionar app", elige **"Correo"**
6. En "Seleccionar dispositivo", elige **"Otro (nombre personalizado)"**
7. Escribe: **"Sistema Registro Spring Boot"**
8. Click en **"Generar"**
9. **¡IMPORTANTE!** Copia la contraseña de 16 caracteres que aparece (sin espacios)

### Paso 3: Configurar application.properties

Abre el archivo `src/main/resources/application.properties` y reemplaza:

```properties
# Email - Configuración Gmail
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=TU_EMAIL@gmail.com
spring.mail.password=xxxx xxxx xxxx xxxx
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000
```

**Ejemplo real:**
```properties
spring.mail.username=juanperez@gmail.com
spring.mail.password=abcd efgh ijkl mnop
```

⚠️ **IMPORTANTE:** 
- Usa tu email completo de Gmail
- Usa la contraseña de aplicación de 16 caracteres (puedes dejar los espacios o quitarlos)
- NO uses tu contraseña normal de Gmail

---

## 🎯 Opción 2: Usar Outlook/Hotmail

Si prefieres usar Outlook o Hotmail:

```properties
spring.mail.host=smtp-mail.outlook.com
spring.mail.port=587
spring.mail.username=tu-email@outlook.com
spring.mail.password=tu-contraseña-normal
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

## 🎯 Opción 3: Usar Mailtrap (Para Desarrollo/Pruebas)

Mailtrap es perfecto para desarrollo porque captura los emails sin enviarlos realmente:

1. Crea cuenta gratis en: https://mailtrap.io/
2. Ve a tu inbox de prueba
3. Copia las credenciales SMTP

```properties
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=tu-username-mailtrap
spring.mail.password=tu-password-mailtrap
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

## ✅ Verificar la Configuración

### 1. Reinicia la aplicación
```bash
mvn spring-boot:run
```

### 2. Prueba el registro
Usa Postman para registrar un usuario con tu email real:

```json
{
  "nombres": "Test",
  "apellidos": "Usuario",
  "dni": "12345678",
  "dniDigitoVerificador": "9",
  "correo": "TU_EMAIL@gmail.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "123456"
}
```

### 3. Revisa tu bandeja de entrada
Deberías recibir un email con el código de verificación en menos de 1 minuto.

---

## 🔧 Solución de Problemas

### Error: "Authentication failed"
**Causa:** Contraseña incorrecta o no usaste contraseña de aplicación
**Solución:** 
- Verifica que hayas activado la verificación en 2 pasos
- Genera una nueva contraseña de aplicación
- Copia la contraseña sin espacios

### Error: "Could not connect to SMTP host"
**Causa:** Firewall o antivirus bloqueando la conexión
**Solución:**
- Verifica tu conexión a internet
- Desactiva temporalmente el antivirus
- Verifica que el puerto 587 no esté bloqueado

### Error: "Invalid Addresses"
**Causa:** Email mal formateado
**Solución:**
- Verifica que el email en `spring.mail.username` sea correcto
- Asegúrate de incluir @gmail.com

### No recibo el email
**Revisa:**
1. ✅ Carpeta de SPAM
2. ✅ Carpeta de Promociones (Gmail)
3. ✅ Que el email esté bien escrito en el registro
4. ✅ Los logs de la aplicación para ver si hay errores

---

## 📝 Ejemplo Completo de Configuración

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

# Email - Gmail con Contraseña de Aplicación
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=miproyecto2024@gmail.com
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

## 🔒 Seguridad

### ⚠️ NO SUBAS TUS CREDENCIALES A GIT

Crea un archivo `.env` o `application-local.properties` para tus credenciales:

**Opción 1: Variables de entorno**
```properties
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
```

Luego ejecuta:
```bash
set MAIL_USERNAME=tu-email@gmail.com
set MAIL_PASSWORD=tu-password-app
mvn spring-boot:run
```

**Opción 2: Archivo local (no versionado)**
1. Crea `application-local.properties`
2. Agrégalo a `.gitignore`
3. Ejecuta con: `mvn spring-boot:run -Dspring.profiles.active=local`

---

## 📱 Mensaje de Email que Recibirás

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

## 🎉 ¡Listo!

Una vez configurado correctamente:
1. ✅ Los emails se enviarán automáticamente
2. ✅ Los usuarios recibirán el código en su bandeja
3. ✅ El código expira en 15 minutos
4. ✅ Puedes ver en los logs si el email se envió correctamente

**Logs exitosos:**
```
✅ Email enviado exitosamente a: usuario@gmail.com
```

**Logs con error:**
```
❌ Error al enviar email: Authentication failed
📧 Código de verificación (para pruebas): 123456
```
