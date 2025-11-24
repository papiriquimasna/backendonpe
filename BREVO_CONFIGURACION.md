# 📧 Brevo (Sendinblue) - Emails Reales Gratis

## ✅ Ventajas
- 300 emails por día GRATIS
- Envía a emails reales (Gmail, Outlook, etc.)
- Configuración en 5 minutos
- No requiere verificación en 2 pasos
- No requiere contraseñas de aplicación

---

## 🚀 Configuración en 5 Pasos

### Paso 1: Crear Cuenta (2 minutos)
1. Ve a: https://www.brevo.com/
2. Click en **"Sign up free"**
3. Regístrate con tu email
4. Confirma tu email

### Paso 2: Obtener SMTP Key (1 minuto)
1. Una vez dentro, ve a: **Settings** (arriba derecha)
2. Click en **"SMTP & API"**
3. En la sección **"SMTP"**, verás:
   ```
   SMTP Server: smtp-relay.brevo.com
   Port: 587
   Login: tu-email@gmail.com
   SMTP Key: [Generar nueva clave]
   ```
4. Click en **"Generate a new SMTP key"**
5. Dale un nombre: "Sistema Spring Boot"
6. **Copia la clave** (algo como: `xkeysib-abc123def456...`)

### Paso 3: Configurar en tu Proyecto (1 minuto)
Abre `src/main/resources/application.properties`:

```properties
# Email - Brevo (300 emails/día gratis)
spring.mail.host=smtp-relay.brevo.com
spring.mail.port=587
spring.mail.username=josueochoareyes25@gmail.com
spring.mail.password=TU_SMTP_KEY_DE_BREVO
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Paso 4: Ejecutar (30 segundos)
```bash
mvn spring-boot:run
```

### Paso 5: Probar (1 minuto)
Registra un usuario con tu email real y recibirás el código en tu Gmail.

---

## 📝 Ejemplo Completo

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

# Email - Brevo
spring.mail.host=smtp-relay.brevo.com
spring.mail.port=587
spring.mail.username=josueochoareyes25@gmail.com
spring.mail.password=xkeysib-abc123def456ghi789jkl012mno345pqr678stu901vwx234yz
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

## ✅ Verificar que Funciona

### 1. Registrar usuario
```json
POST http://localhost:8080/api/auth/registro

{
  "nombres": "Josue",
  "apellidos": "Ochoa",
  "dni": "33333333",
  "dniDigitoVerificador": "3",
  "correo": "josueochoareyes25@gmail.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "333333"
}
```

### 2. Revisar tu Gmail
¡El código llegará a tu bandeja de entrada! 📧

---

## 🎉 Listo

Con Brevo:
- ✅ Los emails llegan a Gmail real
- ✅ 300 emails por día gratis
- ✅ Sin verificación en 2 pasos
- ✅ Sin contraseñas de aplicación
- ✅ Configuración en 5 minutos
