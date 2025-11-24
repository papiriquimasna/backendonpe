# ✅ Checklist: Configuración de Email

## 📋 Antes de Empezar

- [ ] Tengo una cuenta de Gmail activa
- [ ] Tengo acceso a mi teléfono (para verificación en 2 pasos)
- [ ] Tengo conexión a internet

---

## 🔐 Configuración de Gmail

### Paso 1: Verificación en 2 Pasos
- [ ] Fui a https://myaccount.google.com/security
- [ ] Busqué "Verificación en dos pasos"
- [ ] Activé la verificación en 2 pasos
- [ ] Ingresé mi número de teléfono
- [ ] Recibí y verifiqué el código SMS
- [ ] Veo que está "Activada" ✅

### Paso 2: Contraseña de Aplicación
- [ ] Busqué "Contraseñas de aplicaciones" (aparece después de activar 2FA)
- [ ] Click en "Contraseñas de aplicaciones"
- [ ] Seleccioné App: "Correo"
- [ ] Seleccioné Dispositivo: "Otro (nombre personalizado)"
- [ ] Escribí: "Sistema Spring Boot"
- [ ] Click en "GENERAR"
- [ ] Copié la contraseña de 16 caracteres
- [ ] Guardé la contraseña en un lugar seguro

---

## 💻 Configuración del Proyecto

### Paso 3: application.properties
- [ ] Abrí `src/main/resources/application.properties`
- [ ] Encontré la sección de Email
- [ ] Reemplacé `tu-email@gmail.com` con mi email real
- [ ] Reemplacé `xxxx xxxx xxxx xxxx` con mi contraseña de aplicación
- [ ] Guardé el archivo

**Mi configuración:**
```properties
spring.mail.username=_________________@gmail.com
spring.mail.password=____ ____ ____ ____
```

---

## 🚀 Prueba

### Paso 4: Ejecutar la Aplicación
- [ ] Abrí terminal en la carpeta del proyecto
- [ ] Ejecuté: `mvn spring-boot:run`
- [ ] Esperé a que inicie (ver "Started ProyectoApplication")
- [ ] No vi errores de email en los logs

### Paso 5: Probar Registro
- [ ] Abrí Postman
- [ ] Importé `Postman_Collection.json`
- [ ] Abrí: "1. Autenticación → Registro - Paso 1"
- [ ] Cambié el correo por mi email real
- [ ] Click en "Send"
- [ ] Vi respuesta: "Registro iniciado. Revisa tu correo..."

### Paso 6: Verificar Email
- [ ] Abrí mi bandeja de entrada de Gmail
- [ ] Busqué email de "Sistema de Registro"
- [ ] Si no está, revisé carpeta SPAM
- [ ] Si no está, revisé carpeta Promociones
- [ ] Encontré el email con el código de 6 dígitos
- [ ] Copié el código

### Paso 7: Verificar Código
- [ ] En Postman, abrí: "1. Autenticación → Verificar Código - Paso 2"
- [ ] Pegué el código en el campo "codigo"
- [ ] Verifiqué que el correo sea el mismo
- [ ] Click en "Send"
- [ ] Recibí el token JWT
- [ ] El token se guardó automáticamente

---

## 🎯 Verificación Final

### Logs de la Aplicación
- [ ] Vi: `✅ Email enviado exitosamente a: mi-email@gmail.com`
- [ ] NO vi: `❌ Error al enviar email`

### Email Recibido
- [ ] Asunto: "Código de Verificación - Sistema de Registro"
- [ ] Contiene código de 6 dígitos
- [ ] Mensaje bien formateado

### Funcionalidad
- [ ] Puedo registrar usuarios
- [ ] Los emails llegan en menos de 2 minutos
- [ ] Los códigos funcionan correctamente
- [ ] Puedo completar el registro

---

## ❌ Si Algo Falló

### No veo "Contraseñas de aplicaciones"
- [ ] Verifiqué que la verificación en 2 pasos esté activada
- [ ] Cerré sesión y volví a entrar
- [ ] Intenté desde otro navegador

### Error: "Authentication failed"
- [ ] Verifiqué que usé contraseña de aplicación (no mi contraseña normal)
- [ ] Copié la contraseña sin espacios extras
- [ ] Generé una nueva contraseña de aplicación
- [ ] Reinicié la aplicación después de cambiar la configuración

### No recibo el email
- [ ] Revisé carpeta SPAM
- [ ] Revisé carpeta Promociones
- [ ] Esperé 2-3 minutos
- [ ] Verifiqué que el email esté bien escrito
- [ ] Revisé los logs de la aplicación
- [ ] Intenté con otro email

### Error: "Could not connect"
- [ ] Verifiqué mi conexión a internet
- [ ] Desactivé temporalmente el antivirus
- [ ] Intenté con otro WiFi
- [ ] Verifiqué que el puerto 587 no esté bloqueado

---

## 📚 Recursos de Ayuda

Si necesitas más ayuda, consulta:

- [ ] `CONFIGURAR_EMAIL_GMAIL.md` - Guía completa
- [ ] `GUIA_VISUAL_GMAIL.md` - Guía con capturas visuales
- [ ] `RESUMEN_CONFIGURACION_EMAIL.md` - Resumen rápido
- [ ] `PROBAR_EMAIL.md` - Cómo probar el envío

---

## 🎉 ¡Configuración Completa!

Si marcaste todas las casillas, tu sistema está listo para:

✅ Enviar emails reales
✅ Registrar usuarios con verificación por email
✅ Códigos de verificación automáticos
✅ Expiración de códigos en 15 minutos
✅ Sistema de autenticación completo

**¡Felicitaciones!** 🚀

---

## 💡 Alternativa: Mailtrap

Si prefieres no usar Gmail para desarrollo:

- [ ] Creé cuenta en https://mailtrap.io/
- [ ] Copié las credenciales SMTP
- [ ] Configuré en `application.properties`:
```properties
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=mi-username-mailtrap
spring.mail.password=mi-password-mailtrap
```
- [ ] Los emails aparecen en Mailtrap (no se envían realmente)

---

**Fecha de configuración:** _______________

**Email configurado:** _______________@gmail.com

**Estado:** ⬜ Pendiente  ⬜ En Proceso  ✅ Completado
