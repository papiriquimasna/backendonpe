# 🆔 Integración con API de Consulta DNI

## 🎯 ¿Qué hace?

Cuando el usuario ingresa su DNI, el sistema **automáticamente** obtiene:
- ✅ Nombres completos
- ✅ Apellido paterno
- ✅ Apellido materno

**El usuario solo necesita ingresar:**
- DNI
- Dígito verificador
- Correo
- Distrito
- Departamento
- PIN

---

## 🔌 APIs Disponibles

### Opción 1: APIs Perú (Recomendada)
```
URL: https://dniruc.apisperu.com/api/v1/dni/{dni}
Costo: Gratis hasta 100 consultas/día
Token: Requerido
Registro: https://apisperu.com/
```

### Opción 2: API Pública (Alternativa)
```
URL: https://api.apis.net.pe/v2/reniec/dni?numero={dni}
Costo: Gratis
Token: No requerido
Límite: Variable
```

### Opción 3: Consulta DNI Perú
```
URL: https://consultadni.pe/api/dni/{dni}
Costo: Gratis con límites
Token: Opcional
```

---

## 📝 Configurar API Token

### Paso 1: Obtener Token

1. Ve a: https://apisperu.com/
2. Regístrate gratis
3. Copia tu token

### Paso 2: Configurar en el Proyecto

Edita `src/main/java/com/proyecto/proyecto/service/ReniecService.java`:

```java
private static final String API_TOKEN = "TU_TOKEN_AQUI";
```

O mejor, usa variables de entorno en `application.properties`:

```properties
# API RENIEC
reniec.api.url=https://dniruc.apisperu.com/api/v1/dni/
reniec.api.token=tu-token-aqui
```

---

## 🧪 Probar en Postman

### 1. Consultar DNI

```
GET http://localhost:8080/api/reniec/consultar-dni/12345678
```

**Respuesta:**
```json
{
  "success": true,
  "dni": "12345678",
  "nombres": "JUAN CARLOS",
  "apellidoPaterno": "PEREZ",
  "apellidoMaterno": "GARCIA",
  "nombreCompleto": "JUAN CARLOS PEREZ GARCIA",
  "mensaje": "Datos encontrados exitosamente"
}
```

### 2. Registro con Autocompletado

```
POST http://localhost:8080/api/auth/registro
```

**Body (SIN nombres ni apellidos):**
```json
{
  "dni": "12345678",
  "dniDigitoVerificador": "9",
  "correo": "usuario@example.com",
  "distrito": "Lima",
  "departamento": "Lima",
  "pin": "123456"
}
```

El sistema automáticamente:
1. Consulta el DNI en la API
2. Obtiene nombres y apellidos
3. Completa el registro
4. Envía código de verificación

---

## 💻 Uso en Frontend

### React/Next.js

```javascript
// Consultar DNI
const consultarDNI = async (dni) => {
  try {
    const response = await fetch(`http://localhost:8080/api/reniec/consultar-dni/${dni}`)
    const data = await response.json()
    
    if (data.success) {
      // Autocompletar formulario
      setNombres(data.nombres)
      setApellidos(data.apellidoPaterno + ' ' + data.apellidoMaterno)
    }
  } catch (error) {
    console.error('Error consultando DNI:', error)
  }
}

// Componente de registro
function RegistroForm() {
  const [dni, setDni] = useState('')
  const [nombres, setNombres] = useState('')
  const [apellidos, setApellidos] = useState('')
  const [loading, setLoading] = useState(false)

  const handleDniChange = async (e) => {
    const dniValue = e.target.value
    setDni(dniValue)
    
    // Cuando el DNI tiene 8 dígitos, consultar automáticamente
    if (dniValue.length === 8) {
      setLoading(true)
      const data = await consultarDNI(dniValue)
      setLoading(false)
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    
    // Registrar SIN nombres/apellidos (se obtienen automáticamente)
    const response = await fetch('http://localhost:8080/api/auth/registro', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        dni,
        dniDigitoVerificador: '9',
        correo: email,
        distrito,
        departamento,
        pin
        // NO enviar nombres ni apellidos
      })
    })
  }

  return (
    <form onSubmit={handleSubmit}>
      <input 
        type="text" 
        value={dni}
        onChange={handleDniChange}
        placeholder="DNI"
        maxLength="8"
      />
      {loading && <p>Consultando DNI...</p>}
      
      {/* Mostrar nombres autocompletados (solo lectura) */}
      {nombres && (
        <div>
          <p>Nombres: {nombres}</p>
          <p>Apellidos: {apellidos}</p>
        </div>
      )}
      
      {/* Resto del formulario */}
    </form>
  )
}
```

### Vue.js

```javascript
export default {
  data() {
    return {
      dni: '',
      nombres: '',
      apellidos: '',
      loading: false
    }
  },
  watch: {
    dni(newDni) {
      if (newDni.length === 8) {
        this.consultarDNI()
      }
    }
  },
  methods: {
    async consultarDNI() {
      this.loading = true
      try {
        const response = await fetch(`http://localhost:8080/api/reniec/consultar-dni/${this.dni}`)
        const data = await response.json()
        
        if (data.success) {
          this.nombres = data.nombres
          this.apellidos = `${data.apellidoPaterno} ${data.apellidoMaterno}`
        }
      } catch (error) {
        console.error('Error:', error)
      } finally {
        this.loading = false
      }
    },
    
    async registrar() {
      // Registrar sin nombres/apellidos
      const response = await fetch('http://localhost:8080/api/auth/registro', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          dni: this.dni,
          dniDigitoVerificador: '9',
          correo: this.correo,
          distrito: this.distrito,
          departamento: this.departamento,
          pin: this.pin
        })
      })
    }
  }
}
```

---

## 🔄 Flujo Completo

### Opción A: Con Autocompletado (Recomendado)

```
1. Usuario ingresa DNI (8 dígitos)
   ↓
2. Frontend consulta: GET /api/reniec/consultar-dni/{dni}
   ↓
3. API devuelve nombres y apellidos
   ↓
4. Frontend muestra datos (solo lectura)
   ↓
5. Usuario completa resto del formulario
   ↓
6. Frontend envía registro SIN nombres/apellidos
   ↓
7. Backend consulta DNI automáticamente
   ↓
8. Registro completado
```

### Opción B: Manual (Fallback)

```
1. Usuario ingresa DNI
   ↓
2. API no disponible o falla
   ↓
3. Frontend muestra campos de nombres/apellidos
   ↓
4. Usuario ingresa manualmente
   ↓
5. Registro normal
```

---

## ⚠️ Modo de Prueba

Si la API no está disponible, el sistema usa **datos de prueba**:

```java
// Genera nombres basados en el DNI
DNI: 12345678 → Juan Apellido Prueba
DNI: 87654321 → María Apellido Prueba
```

Esto permite desarrollar sin depender de la API externa.

---

## 🔒 Seguridad

### Validaciones Implementadas:

- ✅ DNI debe tener exactamente 8 dígitos
- ✅ Solo números permitidos
- ✅ Verificación de DNI único en la base de datos
- ✅ Timeout de 5 segundos en consultas API
- ✅ Fallback a datos de prueba en desarrollo

---

## 📊 Ventajas

### Para el Usuario:
- ✅ Menos campos que llenar
- ✅ Sin errores de escritura en nombres
- ✅ Registro más rápido
- ✅ Datos estandarizados

### Para el Sistema:
- ✅ Datos verificados
- ✅ Menos errores de validación
- ✅ Nombres consistentes
- ✅ Integración con RENIEC

---

## 🎯 Endpoints Disponibles

### 1. Consultar DNI
```
GET /api/reniec/consultar-dni/{dni}
```
Devuelve todos los datos del DNI

### 2. Validar DNI
```
GET /api/reniec/validar-dni/{dni}
```
Valida formato y existencia del DNI

### 3. Registro con Autocompletado
```
POST /api/auth/registro
Body: { dni, correo, distrito, departamento, pin }
```
Nombres y apellidos se obtienen automáticamente

---

## 🔧 Configuración Avanzada

### Cambiar API Provider

Edita `ReniecService.java`:

```java
// Usar API diferente
private static final String API_URL = "https://tu-api.com/dni/";

// O crear método personalizado
public ConsultaDniResponse consultarDniCustom(String dni) {
    // Tu implementación
}
```

### Agregar Caché

```java
@Cacheable("dni-cache")
public ConsultaDniResponse consultarDni(String dni) {
    // Consulta se cachea por 24 horas
}
```

---

## 📝 Notas Importantes

1. **API Gratuitas tienen límites**: Considera un plan pago para producción
2. **Datos de prueba**: Solo para desarrollo, desactiva en producción
3. **Privacidad**: Los datos del DNI son sensibles, maneja con cuidado
4. **Timeout**: Las APIs pueden ser lentas, implementa timeout
5. **Fallback**: Siempre permite registro manual si la API falla

---

## ✅ Checklist de Implementación

- [ ] Obtener token de API
- [ ] Configurar token en `ReniecService.java`
- [ ] Probar endpoint `/api/reniec/consultar-dni/{dni}`
- [ ] Actualizar frontend para consultar DNI
- [ ] Implementar autocompletado de formulario
- [ ] Agregar fallback manual
- [ ] Probar registro completo
- [ ] Desactivar datos de prueba en producción

---

**¡Listo! Ahora tu sistema consulta automáticamente los datos del DNI.** 🎉
