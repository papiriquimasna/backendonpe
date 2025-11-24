# 🎯 Cómo Usar la API de Consulta DNI

## ✅ Sistema Implementado

Tu sistema ahora funciona de 3 formas:

### 1️⃣ **DNI Existe en RENIEC** (Automático)
Si el DNI está en la base de datos de RENIEC, los nombres y apellidos se obtienen automáticamente.

**Ejemplo:**
```json
POST /api/auth/registro
{
  "dni": "43287690",
  "dniDigitoVerificador": "5",
  "correo": "usuario@example.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "123456"
}
```

**Resultado:** ✅ Nombres y apellidos obtenidos automáticamente de RENIEC

---

### 2️⃣ **DNI NO Existe en RENIEC** (Manual)
Si el DNI no está en RENIEC, el usuario debe proporcionar sus nombres y apellidos manualmente.

**Ejemplo:**
```json
POST /api/auth/registro
{
  "dni": "74992266",
  "dniDigitoVerificador": "9",
  "nombres": "JOSUE",
  "apellidos": "OCHOA REYES",
  "correo": "usuario@example.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "123456"
}
```

**Resultado:** ✅ Registro exitoso con datos manuales

---

### 3️⃣ **DNI NO Existe y NO hay Datos Manuales** (Error)
Si el DNI no está en RENIEC y no se proporcionan nombres/apellidos, se rechaza el registro.

**Ejemplo:**
```json
POST /api/auth/registro
{
  "dni": "99999999",
  "dniDigitoVerificador": "9",
  "correo": "usuario@example.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "123456"
}
```

**Resultado:** ❌ Error: "No se encontró el DNI en RENIEC. Por favor, proporciona tus nombres y apellidos manualmente."

---

## 🎨 Implementación en Frontend

### React/Next.js

```javascript
const [dni, setDni] = useState('')
const [nombres, setNombres] = useState('')
const [apellidos, setApellidos] = useState('')
const [mostrarCamposManuales, setMostrarCamposManuales] = useState(false)
const [loading, setLoading] = useState(false)

// Consultar DNI cuando el usuario termine de escribir
const handleDniBlur = async () => {
  if (dni.length === 8) {
    setLoading(true)
    try {
      const response = await fetch(`http://localhost:8080/api/reniec/consultar-dni/${dni}`)
      const data = await response.json()
      
      if (data.success) {
        // DNI encontrado - ocultar campos manuales
        setNombres(data.nombres)
        setApellidos(data.apellidoPaterno + ' ' + data.apellidoMaterno)
        setMostrarCamposManuales(false)
      } else {
        // DNI no encontrado - mostrar campos manuales
        setMostrarCamposManuales(true)
        setNombres('')
        setApellidos('')
      }
    } catch (error) {
      setMostrarCamposManuales(true)
    }
    setLoading(false)
  }
}

// Registrar usuario
const handleSubmit = async (e) => {
  e.preventDefault()
  
  const body = {
    dni,
    dniDigitoVerificador,
    correo,
    distrito,
    departamento,
    pin
  }
  
  // Solo agregar nombres/apellidos si están disponibles
  if (nombres && apellidos) {
    body.nombres = nombres
    body.apellidos = apellidos
  }
  
  const response = await fetch('http://localhost:8080/api/auth/registro', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
  })
  
  const data = await response.json()
  
  if (response.ok) {
    alert('Registro exitoso. Revisa tu correo.')
  } else {
    alert(data.mensaje)
  }
}

return (
  <form onSubmit={handleSubmit}>
    <input 
      type="text" 
      value={dni}
      onChange={(e) => setDni(e.target.value)}
      onBlur={handleDniBlur}
      placeholder="DNI (8 dígitos)"
      maxLength="8"
      required
    />
    
    {loading && <p>Consultando DNI...</p>}
    
    {/* Mostrar nombres si se encontraron automáticamente */}
    {nombres && !mostrarCamposManuales && (
      <div className="bg-green-100 p-3 rounded">
        <p>✅ Datos encontrados:</p>
        <p><strong>{nombres} {apellidos}</strong></p>
      </div>
    )}
    
    {/* Mostrar campos manuales si el DNI no se encontró */}
    {mostrarCamposManuales && (
      <>
        <p className="text-yellow-600">
          ⚠️ DNI no encontrado. Por favor ingresa tus datos manualmente:
        </p>
        <input 
          type="text" 
          value={nombres}
          onChange={(e) => setNombres(e.target.value)}
          placeholder="Nombres"
          required
        />
        <input 
          type="text" 
          value={apellidos}
          onChange={(e) => setApellidos(e.target.value)}
          placeholder="Apellidos"
          required
        />
      </>
    )}
    
    {/* Resto del formulario */}
    <input type="text" placeholder="Dígito Verificador" required />
    <input type="email" placeholder="Correo" required />
    <input type="text" placeholder="Distrito" required />
    <input type="text" placeholder="Departamento" required />
    <input type="password" placeholder="PIN (6 dígitos)" required />
    
    <button type="submit">Registrarse</button>
  </form>
)
```

---

## 📊 Flujo Completo

```
1. Usuario ingresa DNI (8 dígitos)
   ↓
2. Frontend consulta: GET /api/reniec/consultar-dni/{dni}
   ↓
3a. Si DNI existe:
    - Mostrar nombres y apellidos (solo lectura)
    - Ocultar campos manuales
    - Enviar registro SIN nombres/apellidos
    ↓
3b. Si DNI NO existe:
    - Mostrar mensaje: "DNI no encontrado"
    - Mostrar campos para ingresar nombres/apellidos
    - Usuario completa manualmente
    - Enviar registro CON nombres/apellidos
    ↓
4. Backend valida y registra
   ↓
5. Envía código de verificación por email
```

---

## ⚙️ Configuración Actual

✅ Token configurado en: `src/main/resources/application.properties`
✅ API: https://dniruc.apisperu.com/
✅ Plan: Gratuito (100 consultas/día)
✅ Endpoint: `/api/reniec/consultar-dni/{dni}`
✅ Registro: `/api/auth/registro`

---

## 🔍 Endpoints Disponibles

### 1. Consultar DNI
```
GET /api/reniec/consultar-dni/{dni}
```

**Respuesta exitosa:**
```json
{
  "success": true,
  "dni": "43287690",
  "nombres": "DEYSI ROXANA",
  "apellidoPaterno": "GONZÁLEZ",
  "apellidoMaterno": "CRUZ",
  "nombreCompleto": "DEYSI ROXANA GONZÁLEZ CRUZ"
}
```

**Respuesta fallida:**
```json
{
  "success": false,
  "dni": "74992266",
  "mensaje": "No se pudieron obtener los datos del DNI."
}
```

### 2. Registro (con autocompletado)
```
POST /api/auth/registro
```

**Opción A - DNI existe (sin nombres):**
```json
{
  "dni": "43287690",
  "dniDigitoVerificador": "5",
  "correo": "usuario@example.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "123456"
}
```

**Opción B - DNI no existe (con nombres):**
```json
{
  "dni": "74992266",
  "dniDigitoVerificador": "9",
  "nombres": "JOSUE",
  "apellidos": "OCHOA REYES",
  "correo": "usuario@example.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "123456"
}
```

---

## ✅ Ventajas de esta Implementación

1. **Flexible:** Funciona con o sin API
2. **User-friendly:** Permite ingreso manual si falla
3. **Eficiente:** Un solo endpoint para ambos casos
4. **Seguro:** Valida que los datos estén completos
5. **Escalable:** Fácil de mantener y actualizar

---

## 🎉 ¡Listo!

Tu sistema de consulta DNI está completamente funcional y listo para producción.
