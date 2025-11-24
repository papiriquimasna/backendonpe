# ✅ Conexión Frontend-Backend Completada

## 📁 Archivos Creados/Modificados

### Nuevo Archivo:
- `onpe-app/src/services/api.ts` - Servicio centralizado de API

### Archivos Modificados:
1. `onpe-app/src/features/auth/ui/Register/Registerdni.tsx` - Consulta DNI en RENIEC
2. `onpe-app/src/features/auth/ui/Register/Pindni.tsx` - Guarda PIN
3. `onpe-app/src/features/auth/ui/PhaseRegister/RegisterEmail.tsx` - Registro completo
4. `onpe-app/src/features/auth/ui/PhaseRegister/VerifyEmail.tsx` - Verificación de código

## 🔄 Flujo Completo Implementado

### 1. Validación de DNI
**Componente:** `Registerdni.tsx`
- Usuario ingresa DNI + dígito verificador
- **API Call:** `GET /api/reniec/consultar-dni/{dni}`
- Si existe en RENIEC → Obtiene nombres y apellidos automáticamente
- Si no existe → Continúa sin datos (se pueden ingresar manualmente)
- Guarda datos en `sessionStorage`

### 2. Ingreso de PIN
**Componente:** `Pindni.tsx`
- Usuario ingresa PIN de 6 dígitos
- Guarda PIN en `sessionStorage`

### 3. Registro con Email
**Componente:** `RegisterEmail.tsx`
- Usuario ingresa email
- **API Call:** `POST /api/auth/registro`
- Envía:
  ```json
  {
    "dni": "12345678",
    "dniDigitoVerificador": "9",
    "nombres": "JUAN",
    "apellidos": "PEREZ GARCIA",
    "correo": "usuario@example.com",
    "distrito": "Lima",
    "departamento": "Lima",
    "pin": "123456"
  }
  ```
- Backend envía código de verificación al email

### 4. Verificación de Email
**Componente:** `VerifyEmail.tsx`
- Usuario ingresa código de 6 dígitos
- **API Call:** `POST /api/auth/verificar-codigo`
- Envía:
  ```json
  {
    "correo": "usuario@example.com",
    "codigo": "123456"
  }
  ```
- Recibe token JWT
- Guarda token en `localStorage`

## 🎯 Endpoints Disponibles

### Autenticación
```typescript
// Consultar DNI
GET /api/reniec/consultar-dni/{dni}

// Registrar usuario
POST /api/auth/registro

// Verificar código email
POST /api/auth/verificar-codigo

// Login
POST /api/auth/login
```

### Votación (Requieren token)
```typescript
// Obtener candidatos
GET /api/candidatos?tipo=PRESIDENTE
GET /api/candidatos?tipo=ALCALDE

// Votar
POST /api/votos/votar

// Obtener perfil
GET /api/usuarios/perfil
```

## 🔧 Servicios Implementados

### `apiService`
Todos los métodos para comunicarse con el backend:
- `consultarDni(dni)` - Consulta RENIEC
- `registrar(data)` - Registro de usuario
- `verificarCodigo(data)` - Verificación de email
- `login(data)` - Inicio de sesión
- `obtenerCandidatos(tipo, token)` - Lista de candidatos
- `votar(data, token)` - Emitir voto
- `obtenerPerfil(token)` - Datos del usuario

### `tokenService`
Manejo del token JWT:
- `guardar(token)` - Guarda en localStorage
- `obtener()` - Obtiene token guardado
- `eliminar()` - Elimina token (logout)

## 📊 Flujo de Datos

```
1. Usuario ingresa DNI
   ↓
2. Frontend → Backend: Consulta RENIEC
   ↓
3. Backend → RENIEC API: Obtiene datos
   ↓
4. Backend → Frontend: Devuelve nombres/apellidos
   ↓
5. Usuario ingresa PIN
   ↓
6. Usuario ingresa Email
   ↓
7. Frontend → Backend: Registro completo
   ↓
8. Backend: Guarda usuario + Envía código email
   ↓
9. Usuario ingresa código
   ↓
10. Frontend → Backend: Verifica código
    ↓
11. Backend → Frontend: Devuelve token JWT
    ↓
12. Frontend: Guarda token → Usuario autenticado
```

## 🚀 Cómo Usar

### Iniciar Backend
```bash
cd proyecto
./mvnw spring-boot:run
```
Backend corriendo en: `http://localhost:8080`

### Iniciar Frontend
```bash
cd onpe-app
npm install
npm run dev
```
Frontend corriendo en: `http://localhost:5173` (o el puerto que Vite asigne)

## ✅ Funcionalidades Implementadas

- ✅ Consulta automática de DNI en RENIEC
- ✅ Registro de usuario con validación
- ✅ Envío de código de verificación por email
- ✅ Verificación de email con código
- ✅ Autenticación con JWT
- ✅ Manejo de errores
- ✅ Loading states
- ✅ Validación de formularios

## 🔜 Próximos Pasos (Opcional)

Para completar la aplicación, faltaría implementar:

1. **Login** - Componente para usuarios que ya están registrados
2. **Votación** - Pantalla para seleccionar candidatos y votar
3. **Perfil** - Ver datos del usuario autenticado
4. **Logout** - Cerrar sesión

## 📝 Notas Importantes

1. **CORS**: El backend ya tiene CORS configurado para permitir peticiones del frontend
2. **Token**: Se guarda en `localStorage` y se envía en el header `Authorization: Bearer {token}`
3. **SessionStorage**: Se usa para datos temporales durante el registro
4. **Errores**: Todos los errores se manejan y se muestran al usuario

## 🎉 Estado Actual

El frontend está **completamente conectado** con el backend para el flujo de registro. Los usuarios pueden:
- Validar su DNI
- Obtener datos automáticamente de RENIEC
- Registrarse con email
- Verificar su cuenta
- Recibir token de autenticación

¡Todo funcional y listo para usar!
