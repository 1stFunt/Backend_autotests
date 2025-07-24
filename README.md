## Описание
Образец некоторых моих автотестов, демонстрирующий лаконичную и компактную структуру.  
Для сохранения конфиденциальности показан неполный набор тестовых сценариев, а также использованы фейковые данные и нейтральные названия.

- [TermDepositProductTest](https://github.com/1stFunt/Backend_autotests/blob/main/app/src/test/java/depositService/TermDepositProductTest.java) - один из лучших примеров оформления тестового класса
- [build.gradle](https://github.com/1stFunt/Backend_autotests/blob/main/app/build.gradle) - файл с настройками сборки и зависимостями, отражающими стек технологий проекта

P.S. Структуру можно дополнительно улучшить за счёт использования `Enum` для эндпоинтов, а также вместо обычного `JDBC` и классов мапперов использовать `Spring JDBC` с `JdbcTemplate` и интерфейсом `RowMapper`.  
Кроме того, чтобы сократить число assert-проверок в [TermDepositProductTest](https://github.com/1stFunt/Backend_autotests/blob/main/app/src/test/java/depositService/TermDepositProductTest.java), можно создать базовый `класс-хелпер` с удобными методами сравнения объектов из класса маппера и DTO-класса с большим количеством обязательных полей.
