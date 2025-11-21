# Despliegue en Render

## 1. Preparar el repositorio
Asegúrate de hacer commit y push de los archivos: `meli-app.dockerfile`, `render.yaml`, `application-prod.properties`.

## 2. Crear la base de datos PostgreSQL (opcional)
En Render, crea un recurso de tipo PostgreSQL. Obtendrás:
- Host
- Database name
- User
- Password
- Internal DB URL (formato `postgres://user:pass@host:5432/dbname`)

Usa esa URL como `DATABASE_URL`.

## 3. Configurar servicio Web
Desde Dashboard de Render:
1. New + Web Service
2. Conecta tu repo (GitHub).
3. Render detectará `render.yaml` o puedes indicar manualmente Docker y apuntar a `meli-app.dockerfile`.
4. Setea las env vars:
   - `SPRING_PROFILES_ACTIVE=prod`
   - `DATABASE_URL=jdbc:postgresql://<host>:5432/<dbname>` (si prefieres formato JDBC) O usa variable y cambia en `application-prod.properties`.
   - `DATABASE_USERNAME=<user>`
   - `DATABASE_PASSWORD=<password>`

Si usas la URL tipo `postgres://`, entonces pon: `DATABASE_URL=jdbc:postgresql://host:5432/dbname`.

## 4. Health Check
Render usará `GET /actuator/health` como health check (definido en `render.yaml`). Debe responder `{"status":"UP"}`.

## 5. Puerto
Render asigna PORT automáticamente. El Dockerfile expone 8080 y `server.port=${PORT:8080}` lo respeta.

## 6. Endpoints
- `POST /mutant`  (JSON {"dna": ["..."]})
- `GET /stats`
- `GET /actuator/health`

## 7. Logs
En el panel del servicio en Render tienes pestaña Logs para ver arranque y errores.

## 8. Variables sensibles
No hagas commit de credenciales. Usa las env vars Render.

## 9. Deploy automático
`autoDeploy: true` en `render.yaml` hará que cada push a main dispare nuevo deploy.

## 10. Problemas comunes
- Error de conexión DB: revisa `DATABASE_URL` y firewall.
- Health check falla: confirma que el perfil `prod` está activo.
- Migraciones: con `ddl-auto=update` crea tablas automáticamente.

## 11. H2 vs PostgreSQL
En producción usa PostgreSQL; H2 queda solo para dev (no actives H2 en prod).

## 12. Comandos locales
```bash
# Build jar
./gradlew bootJar
# Ejecutar con perfil prod y variables
SPRING_PROFILES_ACTIVE=prod DATABASE_URL=jdbc:postgresql://localhost:5432/db DATABASE_USERNAME=postgres DATABASE_PASSWORD=secret java -jar build/libs/global-0.0.1-SNAPSHOT.jar
```

