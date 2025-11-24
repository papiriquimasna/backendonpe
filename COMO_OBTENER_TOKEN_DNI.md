# 🔑 Cómo Obtener Token para Consulta de DNI

## ⚡ Pasos Rápidos (5 minutos)

### 1️⃣ Registrarse en APIs Perú (GRATIS)

1. Ve a: **https://apisperu.com/**
2. Haz clic en **"Registrarse"** (esquina superior derecha)
3. Completa el formulario:
   - Nombre
   - Email
   - Contraseña
4. Haz clic en **"Crear cuenta"**

### 2️⃣ Verificar Email

1. Revisa tu bandeja de entrada
2. Busca el email de **APIs Perú**
3. Haz clic en el enlace de verificación

### 3️⃣ Obtener tu Token

1. Inicia sesión en: **https://apisperu.com/login**
2. Ve a tu **Dashboard** o **Panel de Control**
3. Busca la sección **"API Token"** o **"Mi Token"**
4. **Copia tu token** (algo como: `abc123def456...`)

### 4️⃣ Configurar en tu Proyecto

1. Abre el archivo: `src/main/resources/application.properties`
2. Busca la línea que dice: `reniec.api.token=`
3. Pega tu token después del `=`:
   ```properties
   reniec.api.token=TU_TOKEN_AQUI
   ```
4. Guarda el archivo

### 5️⃣ Reiniciar la Aplicación

1. Detén tu aplicación (Ctrl+C en la terminal)
2. Vuelve a iniciar: `./mvnw spring-boot:run`
3. ¡Listo! Ahora puedes consultar DNIs reales

---

## 🧪 Probar que Funciona

### Opción 1: Desde Postman

```
GET http://localhost:8080/api/reniec/consultar-dni/TU_DNI_AQUI
```

Ejemplo:
```
GET http://localhost:8080/api/reniec/consultar-dni/43287690
```

**Respuesta esperada:**
```json
{
  "success": true,
  "dni": "43287690",
  "nombres": "JUAN CARLOS",
  "apellidoPaterno": "PEREZ",
  "apellidoMaterno": "GARCIA",
  "nombreCompleto": "JUAN CARLOS PEREZ GARCIA",
  "mensaje": "Datos encontrados exitosamente"
}
```

### Opción 2: Desde tu Frontend

```javascript
const consultarDNI = async (dni) => {
  const response = await fetch(`http://localhost:8080/api/reniec/consultar-dni/${dni}`)
  const data = await response.json()
  
  if (data.success) {
    console.log('Nombres:', data.nombres)
    console.log('Apellidos:', data.apellidoPaterno, data.apellidoMaterno)
  }
}

// Usar
consultarDNI('43287690')
```

---

## 📊 Plan Gratuito

✅ **100 consultas por día** (suficiente para desarrollo)
✅ Sin tarjeta de crédito
✅ Sin límite de tiempo
✅ Datos reales de RENIEC

Si necesitas más consultas, puedes:
- Crear otra cuenta con otro email
- Contratar un plan pago (muy económico)

---

## ❓ Problemas Comunes

### "No se pudieron obtener los datos"
- ✅ Verifica que copiaste bien el token (sin espacios)
- ✅ Asegúrate de reiniciar la aplicación después de configurar
- ✅ Revisa que tu cuenta esté verificada

### "401 Unauthorized"
- ✅ Token incorrecto o expirado
- ✅ Obtén un nuevo token desde tu dashboard

### "429 Too Many Requests"
- ✅ Superaste el límite de 100 consultas/día
- ✅ Espera hasta mañana o usa otra cuenta

---

## 🎯 Alternativas (si no quieres registrarte)

Si por alguna razón no puedes obtener un token, puedes:

1. **Usar datos de prueba** (solo para desarrollo):
   - Modifica `ReniecService.java` para devolver datos ficticios
   
2. **Permitir ingreso manual**:
   - El usuario ingresa DNI, nombres y apellidos manualmente
   - No hay validación automática

3. **Usar otra API**:
   - Busca otras APIs de DNI en Google
   - Algunas requieren pago desde el inicio

---

## 💡 Recomendación

**Para producción**, considera:
- Contratar un plan pago (más consultas, mejor soporte)
- Implementar caché para no consultar el mismo DNI múltiples veces
- Agregar fallback manual si la API falla

---

**¿Necesitas ayuda?** Revisa la documentación oficial: https://apisperu.com/docs
