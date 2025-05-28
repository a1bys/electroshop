# ElectroShop Management System

**ElectroShop** — это backend-система для управления магазинами электроники, включающая сотрудников, товары, покупки и справочники.

## Стек технологий

- Java 17
- Spring Boot 2.7.x
- Spring Data JPA (Hibernate)
- Spring Web
- PostgreSQL
- Apache Commons CSV
- SpringDoc OpenAPI (Swagger UI)
- Maven

## Структура проекта

Проект разделён на несколько доменных областей:

### Реестры

- **Сотрудники** (`/employees`)  
  Управление данными о сотрудниках, включая CRUD-операции, фильтрацию по отделу, получение лучших сотрудников.

- **Товары** (`/products`)  
  CRUD-операции над товарами, включая тип, цену, описание и архивирование.

- **Покупки** (`/api/purchases`)  
  Хранение информации о покупках, сотрудниках, магазинах, товарах и типах оплаты.

### Справочники

- **Должности** (`/positions`)
- **Типы товаров** (`/api/product-types`)
- **Магазины** (`/api/stores`)
- **Типы покупок** (`/api/purchase-types`)

### Специальные отчёты

- Топ сотрудников по количеству и сумме продаж за последний год
- Лучший младший продавец-консультант по продажам умных часов
- Общая сумма наличных покупок
- Лучшие сотрудники по должности

### Импорт данных

Контроллер `/api/import` принимает ZIP-файл с CSV-документами:

- `position.csv`
- `store.csv`
- `product_types.csv`
- `products.csv`
- `employee.csv`
- `purchase.csv`
- `purchase_type.csv`

Импортируются с автоматической обработкой связей и уменьшением количества товаров при покупках.

## Запуск проекта

### Backend (Spring Boot)

1. Установите JDK 17 и PostgreSQL
2. Укажите параметры подключения в `application.yml`
3. Перейдите в папку `backend`:
   ```bash
   cd backend
   ./mvnw clean package
   ./mvnw spring-boot:run
    ```
4. Backend будет доступен по адресу 'http:localhost:8080/api'

### Frontend (React)

1. Перейдите в папку `frontend`
2. Откройте файл electroshop_ui.html через локальный сервер или браузер

Запросы отправляются на backend по адресу `http://localhost:8080/api`.

## Swagger UI

Документация API доступна по адресу `http://localhost:8080/swagger-ui/index.html`.

## Структура репозитория

* `backend/` - Spring Boot backend (Rest API, Java, Maven)
* `frontend/` - HTML, CSS, JS