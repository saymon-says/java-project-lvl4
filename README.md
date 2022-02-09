[![Actions Status](https://github.com/saymon-says/java-project-lvl4/workflows/my-project-check/badge.svg)](https://github.com/saymon-says/java-project-lvl4/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/6bc70b8e4b4d35bef182/maintainability)](https://codeclimate.com/github/saymon-says/java-project-lvl4/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/6bc70b8e4b4d35bef182/test_coverage)](https://codeclimate.com/github/saymon-says/java-project-lvl4/test_coverage)

### Описание

___
[Page Analyzer](https://page-analize.herokuapp.com/) – сайт, который анализирует указанные страницы на SEO пригодность.

### Реализовано

___

* Проверка при добавлении на уникальность страницы.
* Список добавленных сайтов выводится с пейджингом.
* У каждого добавленного сайта выводится дата последней проверки и код ответа.
* У каждого добавленного сайта можно небольшой SEO анализ запустить.

### Технологии и подход к разработке

___

* Javalin + ORM ebean;
* БД H2 для разработки;
* Thymeleaf + bootstrap;
* Jsoup парсер HTML;
* JUnit5 + Unirest + jacoco;
* CodeClimate, GitHub Action;
* Heroku + PostgreSQL

### Требования

___

* OpenJDK_16
* Gradle 7.2
* Make

### Запуск

___
Генерация миграций БД

```makefile
  make doMigration
  ```

Старт приложения

```makefile
  make start
```

Приложение будет запущено по адресу https://localhost:5000/

Запуск тестов
```makefile
  make test
```

### Пример

___
![сайт](https://user-images.githubusercontent.com/43708964/153193276-6de0b950-e586-428e-9742-f4b06159bbc0.jpg)

![сайт](https://user-images.githubusercontent.com/43708964/153199833-88db9ecd-8e06-466b-ba41-7f9f689f592d.jpg)

![сайт](https://user-images.githubusercontent.com/43708964/153200052-1340b779-d66e-4e9c-a784-2fb0c30a407c.jpg)