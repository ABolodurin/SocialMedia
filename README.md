# SocialMedia
## Social Media Pet Project
___

О времени выполнения задания - неделя (в моём случае по будням 3 часа после работы + 2 полных рабочих дня в сб и вс, прошу принять во внимание)

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

## Выполненные задачи
___

###	Аутентификация и авторизация

- Пользователи могут зарегистрироваться, указав имя пользователя, электронную почту и пароль
- Пользователи могут войти в систему, предоставив правильные учетные данные
- API должен обеспечивать защиту конфиденциальности пользовательских данных, включая хэширование паролей и использование JWT

###	Управление постами

- Пользователи могут создавать новые посты, указывая текст, заголовок
- Пользователи могут обновлять и удалять свои собственные посты

###	Взаимодействие пользователей
- Пользователи могут подписываться на других пользователей.
  С этого момента, пользователь, отправивший заявку, остается подписчиком до тех пор,
  пока сам не откажется от подписки.

###	Подписки и лента активности

### Обработка ошибок
- API осуществляет валидацию введенных данных в запросе на регистрацию (```username, password, email```)

### Документация API
- Все доступные эндпоинты задокументированы с помощью Swagger,
  документация содержит форматы запросов и ответов,
  а также требования к аутентификации.

## TODO list
___

- Unit tests
- Создать для контроллеров обработчик ошибок запросов
  REST Error Exception Handler https://www.baeldung.com/exception-handling-for-rest-with-spring
- Заполнить все заглушки исключений в сервисах
- Добавить нужные валидации на формы запросов пользоватея
- Добавить в посты изображения
- Добавить возможность друзей(когда пользователи подписаны друг на друга)
- Добавить сообщения между друзьями
  
