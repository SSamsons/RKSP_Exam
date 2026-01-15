# Экзаменационная работа

Автор: Самсонов Станислав Грайрович

## Описание

Два микросервиса для обработки событий:
- **ingest-service** - принимает события и отправляет в RabbitMQ
- **processor-service** - обрабатывает события из RabbitMQ, сохраняет в PostgreSQL и ClickHouse

## Запуск

1. Запустить контейнеры:
```bash
cd processor-service
docker compose up -d
```

2. Создать таблицу в ClickHouse:
```bash
docker exec -i clickhouse clickhouse-client --query="CREATE TABLE IF NOT EXISTS агрегаты_событий_студентов (дата_и_время_записи DateTime, количество_записей UInt64) ENGINE = MergeTree() ORDER BY дата_и_время_записи;"
```

3. Запустить сервисы:
```bash
cd ingest-service && mvn spring-boot:run
cd processor-service && mvn spring-boot:run
```

## API

- **POST** `http://localhost:8080/api/v1/events` - отправка события
- **POST** `http://localhost:8081/api/v1/events/count` - получение статистики
- Swagger UI: `http://localhost:8080/swagger-ui.html` и `http://localhost:8081/swagger-ui.html`

## Технологии

Java 17, Spring Boot, RabbitMQ, PostgreSQL, ClickHouse
