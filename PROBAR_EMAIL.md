# 🧪 Probar Envío de Email

## Pasos Rápidos

### 1. Configura tu email en application.properties

```properties
spring.mail.username=TU_EMAIL@gmail.com
spring.mail.password=tu-contraseña-de-aplicación
```

### 2. Inicia la aplicación

```bash
mvn spring-boot:run
```

### 3. Registra un usuario con TU email

**En Postman:**
```
POST http://localhost:8080/api/auth/registro
Content-Type: application/json
```

**Body:**
```json
{
  "nombres": "Prueba",
  "apellidos": "Email",
  "dni": "11111111",
  "dniDigitoVerificador": "1",
  "correo": "TU_EMAIL@gmail.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "111111"
}
```

### 4. Revisa tu email

Deberías recibir un mensaje como este:

```
Asunto: Código de Verificación - Sistema de Registro

¡Hola!

Tu código de verificación es: 123456

Este código expira en 15 minutos.

Si no solicitaste este código, ignora este mensaje.

Saludos,
Sistema de Registro
```

### 5. Verifica el código

**En Postman:**
```
POST http://localhost:8080/api/auth/verificar-codigo
Content-Type: application/json
```

**Body:**
```json
{
  "correo": "TU_EMAIL@gmail.com",
  "codigo": "123456"
}
```

---

## 📊 Logs de la Aplicación

### ✅ Email enviado correctamente:
```
✅ Email enviado exitosamente a: usuario@gmail.com
```

### ❌ Error al enviar:
```
❌ Error al enviar email: Authentication failed
📧 Código de verificación (para pruebas): 123456
```

Si ves el error, revisa:
1. Que hayas activado la verificación en 2 pasos en Gmail
2. Que uses una contraseña de aplicación (no tu contraseña normal)
3. Que el email esté bien escrito en `application.properties`

---

## 🔍 Verificar en la Consola H2

Puedes ver los códigos generados en la base de datos:

1. Ve a: http://localhost:8080/h2-console
2. Conéctate con:
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Username: `sa`
   - Password: (vacío)

3. Ejecuta:
```sql
SELECT nombres, apellidos, correo, codigo_verificacion, codigo_expiracion 
FROM USUARIOS 
WHERE verificado = false;
```

---

## 🎯 Checklist de Configuración

- [ ] Activé la verificación en 2 pasos en Gmail
- [ ] Generé una contraseña de aplicación
- [ ] Copié la contraseña de 16 caracteres
- [ ] Actualicé `spring.mail.username` con mi email
- [ ] Actualicé `spring.mail.password` con la contraseña de aplicación
- [ ] Reinicié la aplicación
- [ ] Probé el registro con mi email
- [ ] Recibí el código en mi bandeja de entrada
- [ ] Verifiqué el código exitosamente

---

## 💡 Tips

1. **Revisa SPAM:** A veces Gmail marca los primeros emails como spam
2. **Espera 1-2 minutos:** El email puede tardar un poco
3. **Usa tu email personal:** Para las pruebas, usa tu propio email
4. **Guarda la contraseña:** Puedes reutilizar la misma contraseña de aplicación

---

## 🚀 Alternativa: Mailtrap para Desarrollo

Si solo quieres probar sin enviar emails reales:

1. Crea cuenta en: https://mailtrap.io/
2. Usa estas credenciales en `application.properties`:

```properties
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=tu-username-mailtrap
spring.mail.password=tu-password-mailtrap
```

3. Los emails aparecerán en tu inbox de Mailtrap (no se envían realmente)
