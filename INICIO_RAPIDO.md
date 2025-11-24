# 🚀 Inicio Rápido - API Sistema de Gestión

## ⚡ Pasos para Empezar

### 1. Configurar Email (IMPORTANTE)

**🚀 Opción Rápida: Mailtrap (2 minutos)**

1. Ve a: https://mailtrap.io/ y crea cuenta gratis
2. Copia username y password de SMTP Settings
3. Abre `src/main/resources/application.properties`
4. Descomenta y reemplaza:
```properties
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=2525
spring.mail.username=TU_USERNAME_MAILTRAP
spring.mail.password=TU_PASSWORD_MAILTRAP
```

**📖 Guía detallada:** [MAILTRAP_RAPIDO.md](MAILTRAP_RAPIDO.md)

**Alternativa Gmail:** [SOLUCION_EMAIL_SIMPLE.md](SOLUCION_EMAIL_SIMPLE.md)

### 2. Ejecutar la Aplicación
```bash
./mvnw spring-boot:run
```

Espera a ver estos mensajes:
```
Started ProyectoApplication in X.XXX seconds
===========================================
SUPERADMINISTRADOR CREADO:
DNI: 99999999
PIN: 999999
===========================================
```

### 2. Importar Colección en Postman
1. Abre Postman
2. Click en **Import**
3. Selecciona el archivo **`Postman_Collection.json`**
4. ¡Listo! Todos los endpoints están configurados

### 3. Probar el Flujo Completo

#### A. Registrar un Usuario
1. En Postman, abre: **1. Autenticación → Registro - Paso 1**
2. **Cambia el correo** por tu email real (ej: tu-email@gmail.com)
3. Click en **Send**
4. **Revisa tu bandeja de entrada** - recibirás un email con el código de 6 dígitos
5. Si no configuraste el email, el código aparecerá en la consola

#### B. Verificar el Código
1. Copia el código de 6 dígitos que recibiste por email
2. Abre: **1. Autenticación → Verificar Código - Paso 2**
3. Pega el código en el campo `"codigo"`
4. Asegúrate de que el correo sea el mismo que usaste en el registro
5. Click en **Send**
6. **El token se guarda automáticamente** ✅

#### C. Ver tu Perfil
1. Abre: **2. Perfil de Usuario → Ver Mi Perfil**
2. Click en **Send**
3. Verás todos tus datos

#### D. Probar el Chatbot
1. Abre: **4. Chatbot → Enviar Mensaje al Chatbot**
2. Cambia el mensaje si quieres
3. Click en **Send**
4. Recibirás una respuesta automática

#### E. Crear una Reclamación
1. Abre: **3. Libro de Reclamaciones → Crear Reclamación**
2. Modifica el asunto y descripción
3. Click en **Send**

## 🔑 Usuario SuperAdmin por Defecto

Para probar funciones de administrador:

```
DNI: 99999999
PIN: 999999
```

Usa este usuario para:
- Cambiar roles de otros usuarios
- Ver todas las reclamaciones

## 📝 Tipos de Datos Válidos

### Tipos de Reclamación:
- `QUEJA`
- `RECLAMO`
- `SUGERENCIA`

### Roles de Usuario:
- `USUARIO` (por defecto)
- `ADMINISTRADOR`
- `SUPERADMINISTRADOR`

## 🗄️ Ver la Base de Datos

Accede a: http://localhost:8080/h2-console

**Credenciales:**
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (dejar vacío)

## 📚 Documentación Completa

- **README_API.md** - Documentación detallada de todos los endpoints
- **EJEMPLOS_PRUEBA.md** - Ejemplos paso a paso con diferentes escenarios
- **Postman_Collection.json** - Colección lista para importar

## ✅ Checklist de Prueba

- [ ] Registrar usuario
- [ ] Verificar código
- [ ] Login
- [ ] Ver perfil
- [ ] Cambiar foto de perfil
- [ ] Crear reclamación
- [ ] Enviar mensaje al chatbot
- [ ] Login como superadmin
- [ ] Cambiar role de usuario
- [ ] Ver todas las reclamaciones

## 🆘 ¿Problemas?

### La aplicación no inicia
```bash
./mvnw clean install
./mvnw spring-boot:run
```

### No recibo el código por email
**Solución:**
1. Verifica que configuraste correctamente `application.properties`
2. Revisa tu carpeta de SPAM
3. Espera 1-2 minutos
4. Verifica los logs de la aplicación:
   - ✅ `Email enviado exitosamente` = Todo bien
   - ❌ `Error al enviar email` = Revisa configuración
5. Lee `CONFIGURAR_EMAIL_GMAIL.md` para ayuda detallada

### Token expirado
Vuelve a hacer login para obtener un nuevo token.

### Error de permisos
Verifica que estés usando el token correcto y que tu usuario tenga el role necesario.

## 🎯 Próximos Pasos

1. ✅ Probar todos los endpoints en Postman
2. ✅ Revisar la base de datos en H2 Console
3. ✅ Crear múltiples usuarios con diferentes roles
4. ✅ Probar el chatbot con diferentes mensajes
5. ✅ Crear reclamaciones de diferentes tipos

---

**¡Todo está listo para probar!** 🎉
