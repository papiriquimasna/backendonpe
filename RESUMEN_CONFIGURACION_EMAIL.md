# 📧 Resumen Rápido: Configurar Email Gmail

## 🎯 3 Pasos Simples

### ✅ PASO 1: Activar Verificación en 2 Pasos
1. Ve a: https://myaccount.google.com/security
2. Busca "Verificación en dos pasos"
3. Actívala (necesitas tu teléfono)

### ✅ PASO 2: Generar Contraseña de Aplicación
1. En la misma página de Seguridad
2. Busca "Contraseñas de aplicaciones"
3. Selecciona: App = "Correo", Dispositivo = "Otro"
4. Escribe: "Sistema Spring Boot"
5. Click "Generar"
6. **COPIA** la contraseña de 16 caracteres (ej: `abcd efgh ijkl mnop`)

### ✅ PASO 3: Configurar application.properties
Abre: `src/main/resources/application.properties`

Reemplaza:
```properties
spring.mail.username=tu-email@gmail.com
spring.mail.password=xxxx xxxx xxxx xxxx
```

Con tus datos reales:
```properties
spring.mail.username=juanperez@gmail.com
spring.mail.password=abcd efgh ijkl mnop
```

---

## 🚀 Probar

1. Ejecuta: `mvn spring-boot:run`
2. Registra un usuario con tu email
3. Revisa tu bandeja de entrada
4. Usa el código de 6 dígitos para verificar

---

## ❌ Errores Comunes

| Error | Causa | Solución |
|-------|-------|----------|
| Authentication failed | No usaste contraseña de aplicación | Genera una contraseña de aplicación |
| Invalid credentials | Contraseña incorrecta | Verifica que copiaste bien la contraseña |
| Could not connect | Firewall/Antivirus | Desactiva temporalmente el antivirus |
| No recibo email | Email en SPAM | Revisa carpeta de SPAM |

---

## 💡 Alternativa: Mailtrap (Sin Gmail)

Si no quieres usar Gmail, usa Mailtrap para desarrollo:

1. Crea cuenta gratis: https://mailtrap.io/
2. Copia las credenciales SMTP
3. Configura:

```properties
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=tu-username-mailtrap
spring.mail.password=tu-password-mailtrap
```

Los emails aparecerán en Mailtrap (no se envían realmente).

---

## 📖 Documentación Completa

Para más detalles, lee: **`CONFIGURAR_EMAIL_GMAIL.md`**
