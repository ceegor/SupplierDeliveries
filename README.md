# Supplier Deliveries API 

Мини-приложение для приёмки поставок от поставщиков и формирования отчёта за период.

Фронтенд не требуется - всё реализовано на уровне REST API.

---

## Стек

- Java 17+
- Spring Boot 3
- Spring Data JPA (Hibernate)
- PostgreSQL 16
- Flyway (миграции)
- Swagger / OpenAPI (springdoc)
- Docker / Docker Compose
- Testcontainers (Тесты с PostgreSQL)

---

## Быстрый старт

### 1) Поднять PostgreSQL в Docker
> Команду нужно выполнять в папке, где лежит `docker-compose.yml`

```bash
docker compose up -d
docker ps
```

### 2) Запустить приложение

```bash
mvnw.cmd spring-boot:run
```
- `Приложение` — http://localhost:8080
- `Swagger UI` — http://localhost:8080/swagger-ui/index.html

## Миграции

Миграции Flyway лежат в папке:
```bash
src/main/resources/db/migration
```

При старте приложения Flyway применяет миграции автоматически.

## API

### Справочники

- `GET /api/suppliers` — список поставщиков
- `GET /api/products` — список продуктов

### Приёмка поставки 

- `POST /api/supplies` — создать поставку (в одной поставке может быть несколько позиций)

Пример запроса: 
```json
{
  "supplierId": 1,
  "supplyDate": "2026-02-07T10:15:00",
  "lines": [
    { "productId": 1, "weightKg": 120.5 },
    { "productId": 3, "weightKg": 80.0 }
  ]
}
```

Что делает эндпоинт:
- создаёт запись в `supplies`
- создаёт строки в `supply_lines`
- определяет цену по прайсу поставщика на дату поставки и сохраняет снапшот цены в `supply_lines.price_per_kg`

### Просмотр созданной поставки 

- `GET /api/supplies/{id}` — получить поставку с позициями и итогами по весу/стоимости

### Отчёт за период

- `GET /api/reports/supplies?from=YYYY-MM-DD&to=YYYY-MM-DD`

Пример:
```bash
/api/reports/supplies?from=2026-02-01&to=2026-02-28
```

Отчёт:
- группирует поступления по поставщикам и видам продукции
- считает итоги по весу и стоимости (по строкам поставки, используя зафиксированную цену)

## Тесты

Тесты используют Testcontainers (поднимается PostgreSQL в контейнере, применяются Flyway-миграции).

Запуск:

```bash
mvnw.cmd test
```

## Остановка

Остановить контейнеры:

```bash
docker compose down
```

## Автор

Легуенко Егор
