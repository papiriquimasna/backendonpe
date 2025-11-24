# 📚 Índice de Documentación

## 🚀 Inicio Rápido

### Para Empezar Inmediatamente
1. **[INICIO_RAPIDO.md](INICIO_RAPIDO.md)** ⭐
   - Pasos básicos para ejecutar la aplicación
   - Flujo completo de prueba
   - Usuario superadmin por defecto

### Configuración de Email (IMPORTANTE)
2. **[CUAL_EMAIL_USAR.md](CUAL_EMAIL_USAR.md)** ⭐
   - Comparación de opciones
   - Recomendaciones según tu caso
   - Tabla comparativa

3. **[MAILTRAP_RAPIDO.md](MAILTRAP_RAPIDO.md)** ⭐⭐⭐
   - Configuración en 2 minutos
   - Opción más fácil
   - Perfecto para desarrollo

4. **[SOLUCION_EMAIL_SIMPLE.md](SOLUCION_EMAIL_SIMPLE.md)** ⭐
   - Todas las alternativas
   - Sin contraseñas de aplicación
   - Outlook, Mailtrap, Gmail

5. **[RESUMEN_CONFIGURACION_EMAIL.md](RESUMEN_CONFIGURACION_EMAIL.md)**
   - 3 pasos para Gmail
   - Resumen ejecutivo
   - Alternativa con Mailtrap

6. **[CONFIGURAR_EMAIL_GMAIL.md](CONFIGURAR_EMAIL_GMAIL.md)**
   - Guía completa paso a paso
   - Múltiples opciones (Gmail, Outlook, Mailtrap)
   - Solución de problemas detallada

7. **[GUIA_VISUAL_GMAIL.md](GUIA_VISUAL_GMAIL.md)**
   - Guía con capturas visuales
   - Paso a paso con ejemplos
   - Verificación de funcionamiento

8. **[CHECKLIST_EMAIL.md](CHECKLIST_EMAIL.md)**
   - Lista de verificación completa
   - Marca cada paso completado
   - Troubleshooting

9. **[PROBAR_EMAIL.md](PROBAR_EMAIL.md)**
   - Cómo probar el envío de emails
   - Verificar logs
   - Consultas SQL útiles

---

## 📖 Documentación de la API

### Referencia Completa
7. **[README_API.md](README_API.md)** ⭐
   - Todos los endpoints disponibles
   - Ejemplos de peticiones y respuestas
   - Autenticación con JWT
   - Base de datos H2

### Ejemplos Prácticos
8. **[EJEMPLOS_PRUEBA.md](EJEMPLOS_PRUEBA.md)**
   - 6 escenarios completos
   - Datos de prueba
   - Errores comunes y soluciones
   - Consultas SQL útiles

### Colección de Postman
9. **[Postman_Collection.json](Postman_Collection.json)** ⭐
   - Importar en Postman
   - Todos los endpoints configurados
   - Variables automáticas
   - Listo para usar

---

## 🏗️ Arquitectura del Proyecto

### Estructura del Código
10. **[ESTRUCTURA_PROYECTO.md](ESTRUCTURA_PROYECTO.md)**
    - Organización de carpetas
    - Descripción de componentes
    - Modelo de datos
    - Tecnologías utilizadas
    - Patrones de diseño

---

## 📋 Guías por Funcionalidad

### 1️⃣ Registro de Usuarios
**Archivos relevantes:**
- `INICIO_RAPIDO.md` → Sección "Registrar un Usuario"
- `EJEMPLOS_PRUEBA.md` → Escenario 1
- `README_API.md` → Endpoint de Registro

**Flujo:**
1. POST `/api/auth/registro` con datos del usuario
2. Sistema envía código al email
3. POST `/api/auth/verificar-codigo` con el código
4. Usuario registrado y autenticado

### 2️⃣ Login
**Archivos relevantes:**
- `INICIO_RAPIDO.md` → Sección "Login"
- `EJEMPLOS_PRUEBA.md` → Escenario 2
- `README_API.md` → Endpoint de Login

**Flujo:**
1. POST `/api/auth/login` con DNI y PIN
2. Recibir token JWT
3. Usar token en todas las peticiones

### 3️⃣ Perfil de Usuario
**Archivos relevantes:**
- `README_API.md` → Endpoints de Perfil
- `EJEMPLOS_PRUEBA.md` → Escenario 2

**Funcionalidades:**
- Ver perfil (GET `/api/usuario/perfil`)
- Cambiar foto (POST `/api/usuario/foto-perfil`)

### 4️⃣ Libro de Reclamaciones
**Archivos relevantes:**
- `EJEMPLOS_PRUEBA.md` → Escenario 3
- `README_API.md` → Endpoints de Reclamaciones

**Tipos:**
- QUEJA
- RECLAMO
- SUGERENCIA

### 5️⃣ Chatbot
**Archivos relevantes:**
- `EJEMPLOS_PRUEBA.md` → Escenario 4
- `README_API.md` → Endpoints de Chat

**Funcionalidades:**
- Enviar mensajes
- Recibir respuestas automáticas
- Ver historial

### 6️⃣ Gestión de Roles
**Archivos relevantes:**
- `EJEMPLOS_PRUEBA.md` → Escenario 5
- `README_API.md` → Endpoint de Cambio de Roles

**Roles disponibles:**
- USUARIO (por defecto)
- ADMINISTRADOR
- SUPERADMINISTRADOR

---

## 🔧 Configuración

### Archivos de Configuración
- `src/main/resources/application.properties` - Configuración principal
- `pom.xml` - Dependencias Maven

### Configuraciones Importantes
- **Base de datos:** H2 en memoria
- **JWT:** Tokens de autenticación
- **Email:** SMTP de Gmail
- **Archivos:** Límite de 5MB

---

## 🎯 Flujos Completos

### Flujo 1: Nuevo Usuario
```
1. Registro → 2. Email con código → 3. Verificación → 4. Login → 5. Usar sistema
```

### Flujo 2: Usuario Existente
```
1. Login → 2. Recibir token → 3. Usar sistema
```

### Flujo 3: Crear Reclamación
```
1. Login → 2. Crear reclamación → 3. Ver mis reclamaciones
```

### Flujo 4: Administrador
```
1. Login como superadmin → 2. Ver todas las reclamaciones → 3. Cambiar roles
```

---

## 🆘 Solución de Problemas

### Por Tema

#### Email no funciona
📖 Lee en orden:
1. `RESUMEN_CONFIGURACION_EMAIL.md`
2. `CHECKLIST_EMAIL.md`
3. `CONFIGURAR_EMAIL_GMAIL.md`

#### Errores de autenticación
📖 Lee:
- `README_API.md` → Sección "Autenticación"
- `EJEMPLOS_PRUEBA.md` → "Errores Comunes"

#### No entiendo la estructura
📖 Lee:
- `ESTRUCTURA_PROYECTO.md`

#### Quiero ejemplos
📖 Lee:
- `EJEMPLOS_PRUEBA.md`

---

## 📊 Orden Recomendado de Lectura

### Para Desarrolladores Nuevos
1. ✅ `INICIO_RAPIDO.md`
2. ✅ `RESUMEN_CONFIGURACION_EMAIL.md`
3. ✅ Configurar email en `application.properties`
4. ✅ Ejecutar aplicación
5. ✅ Importar `Postman_Collection.json`
6. ✅ Probar endpoints
7. ✅ `EJEMPLOS_PRUEBA.md` para más casos

### Para Configurar Email
1. ✅ `RESUMEN_CONFIGURACION_EMAIL.md`
2. ✅ `GUIA_VISUAL_GMAIL.md`
3. ✅ `CHECKLIST_EMAIL.md` (ir marcando)
4. ✅ `PROBAR_EMAIL.md` (verificar)

### Para Entender el Código
1. ✅ `ESTRUCTURA_PROYECTO.md`
2. ✅ Revisar código fuente
3. ✅ `README_API.md` para endpoints

---

## 🎓 Recursos Adicionales

### Tecnologías Usadas
- **Spring Boot 3.5.7** - Framework
- **Spring Security** - Autenticación
- **JWT** - Tokens
- **H2 Database** - Base de datos
- **Spring Mail** - Envío de emails
- **Lombok** - Reducción de código

### Enlaces Útiles
- Spring Boot: https://spring.io/projects/spring-boot
- JWT: https://jwt.io/
- H2 Database: https://www.h2database.com/
- Mailtrap: https://mailtrap.io/

---

## 📝 Archivos por Categoría

### 🚀 Inicio y Configuración
- `INICIO_RAPIDO.md`
- `RESUMEN_CONFIGURACION_EMAIL.md`
- `CONFIGURAR_EMAIL_GMAIL.md`
- `GUIA_VISUAL_GMAIL.md`
- `CHECKLIST_EMAIL.md`

### 📖 Documentación API
- `README_API.md`
- `EJEMPLOS_PRUEBA.md`
- `Postman_Collection.json`

### 🏗️ Arquitectura
- `ESTRUCTURA_PROYECTO.md`

### 🧪 Pruebas
- `PROBAR_EMAIL.md`
- `EJEMPLOS_PRUEBA.md`

### 📋 Referencia
- `INDICE_DOCUMENTACION.md` (este archivo)

---

## ✅ Checklist General

### Configuración Inicial
- [ ] Leí `INICIO_RAPIDO.md`
- [ ] Configuré email siguiendo `RESUMEN_CONFIGURACION_EMAIL.md`
- [ ] Ejecuté la aplicación
- [ ] Importé colección de Postman

### Pruebas Básicas
- [ ] Registré un usuario
- [ ] Recibí email con código
- [ ] Verifiqué el código
- [ ] Hice login
- [ ] Vi mi perfil

### Pruebas Avanzadas
- [ ] Cambié foto de perfil
- [ ] Creé una reclamación
- [ ] Usé el chatbot
- [ ] Cambié roles (como superadmin)

### Documentación
- [ ] Entiendo la estructura del proyecto
- [ ] Sé cómo usar todos los endpoints
- [ ] Puedo solucionar problemas comunes

---

**¡Bienvenido al proyecto!** 🎉

Si tienes dudas, consulta el archivo correspondiente en este índice.
