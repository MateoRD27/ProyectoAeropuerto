FROM postgres:15

# Variables de entorno para la configuración de PostgreSQL
ENV POSTGRES_DB=sistemavuelo
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres

# Exponer el puerto estándar de PostgreSQL
EXPOSE 5432

# Opcional: Copiar scripts SQL para inicializar la base de datos
# COPY ./init-scripts/ /docker-entrypoint-initdb.d/

# El comando por defecto para iniciar PostgreSQL lo ejecuta la imagen base automáticamente