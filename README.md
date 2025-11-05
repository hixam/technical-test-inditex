# Technical Test Inditex - Price Query Service

REST API service for querying product prices based on application date, product ID, and brand ID.

## Requirements

- Java 21
- Maven 3.6+

## Technologies

- Spring Boot 3.4.11
- H2 Database (in-memory)
- Flyway (database migrations)
- MapStruct (object mapping)
- Lombok (boilerplate reduction)
- SpringDoc OpenAPI (API documentation)
- JUnit 5 + Mockito (testing)

## How to Run

### Build the project

```bash
mvn clean install
```

### Run the application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Run tests

```bash
# Run all tests
mvn test

# Run only unit tests
mvn test -Dtest="*Test"

# Run only integration tests
mvn test -Dtest="*IT"

# Run only system tests
mvn test -Dtest="*SystemTest"
```

## API Documentation

Once the application is running, access:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## API Usage

### Endpoint

```
GET /api/prices
```

### Parameters

- `applicationDate` (required): Date and time in ISO format (e.g., `2020-06-14T10:00:00`)
- `productId` (required): Product identifier (positive number)
- `brandId` (required): Brand identifier (1 = ZARA)

### Example Request

```bash
curl "http://localhost:8080/api/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

### Example Response

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50
}
```

## Database

The application uses an **in-memory H2 database** initialized with sample data via Flyway migrations.

### Initial Data

| BRAND_ID | PRODUCT_ID | PRICE_LIST | START_DATE          | END_DATE            | PRIORITY | PRICE | CURR |
|----------|------------|------------|---------------------|---------------------|----------|-------|------|
| 1        | 35455      | 1          | 2020-06-14 00:00:00 | 2020-12-31 23:59:59 | 0        | 35.50 | EUR  |
| 1        | 35455      | 2          | 2020-06-14 15:00:00 | 2020-06-14 18:30:00 | 1        | 25.45 | EUR  |
| 1        | 35455      | 3          | 2020-06-15 00:00:00 | 2020-06-15 11:00:00 | 1        | 30.50 | EUR  |
| 1        | 35455      | 4          | 2020-06-15 16:00:00 | 2020-12-31 23:59:59 | 1        | 38.95 | EUR  |

## Test Scenarios

The project includes 5 system tests as specified:

1. Request at 10:00 on June 14 → Returns price 35.50 (price list 1)
2. Request at 16:00 on June 14 → Returns price 25.45 (price list 2, higher priority)
3. Request at 21:00 on June 14 → Returns price 35.50 (price list 1)
4. Request at 10:00 on June 15 → Returns price 30.50 (price list 3)
5. Request at 21:00 on June 16 → Returns price 38.95 (price list 4)

## Authors

- Hicham Az. Hisami27@gmail.com
