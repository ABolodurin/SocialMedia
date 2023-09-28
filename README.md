<h1 align="center"> 
  SocialMedia
</h1>

## This project implements some features for modern social media APIs

## Stack

- Java ```17```
- Maven ```4.0.0```
- Spring Boot ```3.0.0```
    - Spring Security
    - Spring Data JPA
    - Spring Web
- PostgreSQL
- Lombok
- JUnit/Mockito
- Swagger ```3.0```

## Start

- Set project JDK to version 17
- Create new DB path ```localhost:5432/socialmedia``` in postgreSQL
  ```SQL
  CREATE DATABASE socialmedia;
  ```
- Run the application
- Swagger UI path ```localhost:8080/swagger-ui.html```
- Type in swagger form ```/v3/api-docs``` and click ```Explore```

<image
  src="/form.png"
  alt="form"
  caption="form">

DB migration is not required, Hibernate does it automatically.

Localhost ports customization: ```src/main/resources/application.properties```

### UI
<image
src="/UI.png"
alt="UI"
caption="UI">

### DB
<image
src="/DB.png"
alt="DB"
caption="DB">

### Swagger documentation

[/api-docs.json](https://github.com/ABolodurin/SocialMedia/blob/master/api-docs.json)

## Description

###	Authentication and authorization

- Users can register by username, email, and password
  and login by username and password
- API uses JWT token authorization
- The database stores hashed passwords

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
  Such users remain as subscribers until they unsubscribe
- If two users subscribed to each other they can chat.
- Chat sorted by the timestamp of the messages(most recent is above)

###	Feed

- Feed shows the latest posts of user's subscriptions
- Feed supports paging and sorting by timestamp of the posts

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
