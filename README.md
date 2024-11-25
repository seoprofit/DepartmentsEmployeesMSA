## Запуск приложения в корневой директории проекта: docker-compose -f docker-compose.yml up

### 2 микросервиса Employees и Departments

### API Gateway http://localhost:8765

#### POST http://localhost:8765/api/departments/add

#### GET http://localhost:8765/api/departments

#### GET http://localhost:8765/api/departments/id/1

#### GET http://localhost:8765/api/departments/name/1

#### GET http://localhost:8765/api/departments/1/summary

#### GET http://localhost:8765/api/departments/1/details

#### GET http://localhost:8765/api/departments/employees

#### POST http://localhost:8765/api/employees/add

#### GET http://localhost:8765/api/employees/1


### Apache Kafka для дублирования информации о создании департаментов в сервис Employees.

### Eureka server для регистрации микросервисов.
