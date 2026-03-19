#!/bin/bash
set -e

# ============================================================
#  deploy.sh — build + deploy de dmBackend
#  Uso: ./deploy.sh [--skip-build]
# ============================================================

COMPOSE_FILE="docker-compose.yml"
ENV_FILE=".env"
LOG_DIR="/var/log/dm-api"

echo "========================================"
echo "  dmBackend — deploy"
echo "========================================"

# --- Verificar que existe el .env ---
if [ ! -f "$ENV_FILE" ]; then
  echo ""
  echo "  [ERROR] No se encontró el archivo .env"
  echo "  Crea uno basado en el ejemplo:"
  echo ""
  echo "    SERVER_PORT=8088"
  echo "    CORS_ALLOWED_ORIGINS=http://192.168.100.62:4200"
  echo "    KEYCLOAK_AUTH_SERVER_URL=http://192.168.100.62:8080"
  echo "    KEYCLOAK_REALM=dm-dev"
  echo "    KEYCLOAK_CLIENT_ID=dm-client-api-rest"
  echo "    KEYCLOAK_CLIENT_SECRET=a2cSTRcRAUYsNph6yZelyEWwdMsQCTcf"
  echo "    DB_URL=jdbc:postgresql://192.168.100.62:5432/dm_db"
  echo "    DB_USERNAME=devuser"
  echo "    DB_PASSWORD=devpass123"
  echo ""
  exit 1
fi

# --- Crear directorio de logs si no existe ---
if [ ! -d "$LOG_DIR" ]; then
  echo "  Creando directorio de logs: $LOG_DIR"
  mkdir -p "$LOG_DIR"
fi

# --- Build de la imagen (salteable con --skip-build) ---
if [ "$1" != "--skip-build" ]; then
  echo ""
  echo "  [1/3] Construyendo imagen Docker..."
  docker compose -f "$COMPOSE_FILE" build --no-cache
else
  echo ""
  echo "  [1/3] Build omitido (--skip-build)"
fi

# --- Bajar el contenedor anterior si está corriendo ---
echo ""
echo "  [2/3] Deteniendo contenedor anterior (si existe)..."
docker compose -f "$COMPOSE_FILE" down --remove-orphans || true

# --- Levantar ---
echo ""
echo "  [3/3] Levantando servicio..."
docker compose -f "$COMPOSE_FILE" up -d

# --- Estado final ---
echo ""
echo "  Esperando que el contenedor arranque..."
sleep 5

docker compose -f "$COMPOSE_FILE" ps

echo ""
echo "  Logs recientes:"
docker compose -f "$COMPOSE_FILE" logs --tail=30

echo ""
echo "========================================"
echo "  Deploy finalizado"
echo "  API:     http://$(hostname -I | awk '{print $1}'):${SERVER_PORT:-8088}/api"
echo "  Health:  http://$(hostname -I | awk '{print $1}'):${SERVER_PORT:-8088}/api/actuator/health"
echo "  Swagger: http://$(hostname -I | awk '{print $1}'):${SERVER_PORT:-8088}/api/swagger-ui/index.html"
echo "  Logs:    $LOG_DIR/app.log"
echo "========================================"