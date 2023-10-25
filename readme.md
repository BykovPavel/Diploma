# Инструкция запуска приложения и тестов.
### 1. Запуск контейнеров Docker.
Ввести в терминале команду
```docker-compose up```
### 2. Запуск SUT
#### - PostgreSQL
Ввести в терминале команду ```java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar```
#### - MySQL
Ввести в терминале команду ```java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar```
### 3. Запуск тестов
#### - PostgreSQL
Ввести в терминале команду ```./gradlew clean test "-Ddatasource.url=jdbc:postgresql://localhost:5432/app"```
#### - MySQL
Ввести в терминале команду ```./gradlew clean test "-Ddatasource.url=jdbc:mysql://localhost:3306/app"```
### 4. Отчетность
#### - Получение отчета Allure
Ввести в терминале команду ```./gradlew allureServe```
### 5. Завершение 
#### - Остановка работы Allure
Ввести в терминале сочетание клавиш CTRL+C и подтвердить вводом ```Y```  в терминале.
#### - Остановка работы SUT
Ввести в терминале сочетание клавиш CTRL+C
#### - Остановка работы контейнеров Docker
Ввести в терминале команду ```docker-compose down```

