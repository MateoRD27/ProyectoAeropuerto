FROM postgres:17

# Variables de entorno para la configuración de PostgreSQL

# Exponer el puerto estándar de PostgreSQL
EXPOSE 5432

# Opcional: Copiar scripts SQL para inicializar la base de datos
# COPY ./init-scripts/ /docker-entrypoint-initdb.d/

# El comando por defecto para iniciar PostgreSQL lo ejecuta la imagen base automáticamente