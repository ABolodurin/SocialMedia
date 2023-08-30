# SocialMedia
## Social Media Project
___

### Запуск

- Java 11
- DB path ```localhost:5432/socialmedia```
- Swagger UI path ```localhost:8080/swagger-ui/```

## Описание
___
### Иллюстрация UI
<image
  src="/UI.png"
  alt="UI"
  caption="Иллюстрация UI">
  
### Иллюстрация DB
<image
  src="/DB.png"
  alt="DB"
  caption="Иллюстрация DB">

### Документация из Swagger

[/api-docs.json](https://github.com/ABolodurin/SocialMedia/blob/master/api-docs.json)

## Выполненные задачи
___

###	Аутентификация и авторизация

- Пользователи могут зарегистрироваться, указав имя пользователя, электронную почту и пароль
- Пользователи могут войти в систему, предоставив правильные учетные данные
- API должен обеспечивать защиту конфиденциальности пользовательских данных, включая хэширование паролей и использование JWT

###	Управление постами

- Пользователи могут создавать новые посты, указывая текст, заголовок
- Пользователи могут обновлять и удалять свои собственные посты
- Пользователи могут посмотреть определённый пост

###	Взаимодействие пользователей
- Пользователи могут подписываться на других пользователей.
  С этого момента, пользователь, отправивший заявку, остается подписчиком до тех пор,
  пока сам не откажется от подписки.
- Если пользователи подписаны друг на друга, то они могут писать друг другу сообщения.
  Помимо возможности отправки сообщений пользователи могут запросить диалог
  с пользователем(сортировка в диалоге по времени в обратном порядке). 

###	Подписки и лента активности
- Лента активности пользователя отображает последние посты от пользователей, на которых он подписан.
- Лента активности поддерживает пагинацию и сортировку по времени создания постов

### Обработка ошибок
- API осуществляет валидацию введенных данных в запросах
- API обрабатывает и возвращает сообщения при:
  - ошибочных или невалидных данных в запросах
  - ошибках авторизации
  - внутренних проблемах сервера.

### Документация API
- Все доступные эндпоинты задокументированы с помощью Swagger,
  документация содержит форматы запросов и ответов,
  а также требования к аутентификации.

### Тестирование
- Бизнес логика проекта(кроме мессенджера) покрыта модульными тестами.

## TODO list
___

- Покрыть тестами мессенджер, контроллеры и секьюрити
- Добавить в посты изображения
