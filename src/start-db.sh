#!/bin/sh

docker volume create file_service_db_vol

docker run \
      --rm \
      --platform linux/amd64 \
      -e POSTGRES_HOST_AUTH_METHOD=trust \
      -v file_service_db_vol:/var/lib/postgresql/data \
      --name FileServiceDB \
      -p 5432:5432 \
      postgres:12-alpine