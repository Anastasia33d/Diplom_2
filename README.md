# Diplom_2 - API Автотесты
Проект автоматизированного тестирования API сервиса Stellar Burgers.
## Технологии
- Java 11
- Maven 3.8.1
- JUnit 4.13.2
- Rest Assured 5.4.0
- Allure 2.20.1
- JavaFaker 1.0.2
- Jackson Databind 2.14.2
- SLF4J Simple 2.0.7
## Структура проекта
- `src/main/java/config` - конфигурационные классы для работы с API
- `src/main/java/models` - модели данных (User, Order)
- `src/main/java/utils` - вспомогательные классы для генерации тестовых данных и утилиты
- `src/test/java/tests` - тестовые классы
## Запуск тестов
### Предварительные требования
- Установленная Java 11
- Установленный Maven 3.8.1
### Запуск всех тестов
```mvn clean test```
### Генерация Allure-отчёта
```mvn allure:serve```