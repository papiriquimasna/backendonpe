# ✅ Checklist de Despliegue

## Preparación (Ya está listo ✅)
- [x] `railway.json` creado
- [x] `nixpacks.toml` creado
- [x] CORS configurado en `WebConfig.java`
- [x] Frontend actualizado para usar variables de entorno
- [x] `.gitignore` correcto

---

## Tu turno 👇

### 1️⃣ Subir a GitHub
```bash
git add .
git commit -m "Preparar para despliegue"
git push
```

### 2️⃣ Crear cuenta en Railway
- [ ] Ir a https://railway.app
- [ ] Registrarse con GitHub
- [ ] Confirmar email

### 3️⃣ Desplegar proyecto
- [ ] Click "Start a New Project"
- [ ] Click "Deploy from GitHub repo"
- [ ] Seleccionar tu repositorio
- [ ] Esperar que detecte Java/Maven

### 4️⃣ Configurar variables
En Railway → Variables → Add Variable:
- [ ] `SPRING_PROFILES_ACTIVE` = `supabase`
- [ ] `PORT` = `8080`

### 5️⃣ Esperar despliegue
- [ ] Ver logs en Railway
- [ ] Esperar mensaje "Started ProyectoApplication"
- [ ] Copiar URL pública

### 6️⃣ Configurar frontend
Crear `onpe-app/.env.local`:
```bash
VITE_API_URL=https://TU-URL-DE-RAILWAY.up.railway.app/api
```

### 7️⃣ Probar
- [ ] Abrir: `https://TU-URL/actuator/health`
- [ ] Debe mostrar: `{"status":"UP"}`
- [ ] Ejecutar frontend: `cd onpe-app && npm run dev`
- [ ] Probar registro/login

---

## 🆘 Si algo falla

### Backend no inicia
1. Ver logs en Railway
2. Verificar que las variables estén correctas
3. Verificar que Supabase esté accesible

### Frontend no conecta
1. Verificar URL en `.env.local`
2. Verificar que termine en `/api`
3. Abrir consola del navegador (F12)

### Error CORS
1. Verificar `WebConfig.java`
2. Hacer commit y push
3. Railway redesplegará automáticamente

---

## 📞 Ayuda

Si tienes problemas:
1. Revisa los logs en Railway
2. Verifica las variables de entorno
3. Asegúrate de que la URL en el frontend sea correcta
