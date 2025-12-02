#!/bin/bash

docker stop {{ cookiecutter.package_name }}-db && docker rm ddd-db
docker run \
  --name {{ cookiecutter.package_name }}-db \
  --platform linux/x86_64 \
  -p 3307:3306 \
  -e MYSQL_ROOT_PASSWORD=mysqlpass \
  -e MYSQL_DATABASE={{ cookiecutter.package_name }}-db \
  -d mysql:8.0