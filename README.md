# ElectroShop Managment System

Данный проект представляет собой backend-систему для управления сотрудниками, товарами,
покупками и справочниками в магазине электроники.

----

## Стек технологий
- Java 17
- Spring Boot 2.7.x
- Spring Data JPA (Hibernate)
- Spring Web
- PostgreSQL
- Apache Commons CSV
- springdoc-openapi-ui
- Maven

----

## Структура проекта

Проект разделен на несколько модулей:
- **Реестры**:
- Employee - управление сотрудниками
- Product - управление товарами
- Purchase - управление покупками
- **Справочники**:
- Должности
- Типы товаров
- Магазины
- Типы покупок
- **Специальные отчёты**:
- Лучшие сотрудники по продажам и должностям
- Лучший младший сотрудник по продажам умных часов
- Сумма продаж по типу оплаты (наличные)

----

## Запуск проекта

### Backend (Spring Boot)
1. Установите JDK 17 и PostgreSQL.
2. В файле `backend/src/main/resources/application.yml` укажите параметры подключения к вашей базе данных.
3. Перейдите в папку backend:
   ```
   cd backend
   ```
4. Соберите и запустите проект:
   ```
   ./mvnw clean package
   ./mvnw spring-boot:run
   ```
5. API будет доступен по адресу: http://localhost:8080/api/

### Frontend
1. Перейдите в папку frontend.
2. Откройте файл `electroshop_ui.html` через локальный сервер (например, Live Server или http-server).
3. Все запросы к данным идут на backend по адресу http://localhost:8080/api/

---

## Структура репозитория

- backend/ — Spring Boot backend (REST API, Java, Maven)
- frontend/ — HTML, CSS, JS (клиентская часть)

---

## Swagger UI
Документация API будет доступна по адресу: http://localhost:8080/swagger-ui.html