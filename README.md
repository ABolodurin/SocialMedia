<h1 align="center"> 
  SocialMedia
</h1>

## This project implements some features for modern social media API's

## Stack

- Java ```11```
- Maven ```4.0.0```
- Spring Boot ```2.7.14```
    - Spring Security
    - Spring Data JPA
    - Spring Web
- PostgreSQL
- Lombok
- JUnit/Mockito
- SpringFox (Swagger) ```3.0.0```

## Start

- Set project JDK to version 11
- Create new DB path ```localhost:5432/socialmedia``` in postgreSQL
  ```SQL
  CREATE DATABASE socialmedia;
  ```
- Run the application
- Swagger UI path ```localhost:8080/swagger-ui/```

DB migration is not required, Hibernate do it automatically.

Localhost ports customization: ```src/main/resources/application.properties```

### UI
<image
src="/UI.png"
alt="UI"
caption="Иллюстрация UI">

### DB
<image
src="/DB.png"
alt="DB"
caption="Иллюстрация DB">

### Swagger documentation

[/api-docs.json](https://github.com/ABolodurin/SocialMedia/blob/master/api-docs.json)

## Description

###	Authentication and authorization

- Users can register by username, email and password
  and login by username and password
- API implemented authentication using the JWT token
- The password is stored in a database as a hash

###	Posts

- Users can create posts
- Users can modify and delete their posts
- Users can view posts


Post contains
- Header
- Text content
- Generated timestamp

###	User interaction

- Users can subscribe to other users.
  The user remains a subscriber until he himself unsubscribes
- If two users are subscribed on top of each other, they can chat.
- Chat sorted by the timestamp of the messages(newer is above)

###	Feed

- The feed shows the last posts of the subscriptions
- The feed supports paging and sorting by the timestamp of the posts

### Error Handling and data validation

- API validates all data in the user's request forms
- API returns error messages of:
    - ```REQUEST_VALIDATION_ERROR```
    - ```INTERNAL_SERVER_ERROR```
    - ```NOT_FOUND```
    - ```AUTHENTICATION_ERROR```
    - ```FEED_IS_EMPTY```
    - ```UPDATE_NON_OWN_ENTITY_ERROR```
    - ```ALREADY_DONE```
    - ```NOT_FRIENDS```

### API Documentation

- All available endpoints are documented using Swagger
- Documentation contains request/response formats
  and authentication requirements
- GUI available after start (path: ```/swagger-ui/```)
- [JSON API docs](https://github.com/ABolodurin/SocialMedia/blob/master/api-docs.json)

### Testing

- Services and repositories are covered with unit tests