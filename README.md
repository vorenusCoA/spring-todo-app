# spring-todo-app
Just another TODO App but using Spring MVC and Spring Security.

You can add an delete activities, register users and reset your password

## Prerequisites:
 - [Docker](https://docs.docker.com/get-docker/)
 - [Java 17](https://www.azul.com/downloads/?package=jdk)
 - MySQL Client (i.e [HeidiSQL](https://www.heidisql.com/download.php))
 - SMTP Server (if you want to test the User Registration or Forgot Password functionalities). [Sendinblue](https://www.sendinblue.com/) is free and easy to configure

## To try it locally:
- Clone the repo
- If you want to test the User Registration and/or Forgot Password functionalitites you need to add the connection properties to your SMTP server into the "application.properties" file. For Sendinblue would be something like this:

```
spring.mail.username=yourUsername
spring.mail.password=yourPassword
spring.mail.port=587
spring.mail.host=smtp-relay.sendinblue.com
spring.mail.properties.mail.transport.protocol=smtp
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.connectiontimeout= 5000
spring.mail.properties.mail.smtp.timeout = 5000
spring.mail.properties.mail.writetimeout = 5000
spring.mail.properties.mail.debug=true
```

- From the project's root folder run: `docker compose up` to start mysql
- With a MySQL client connect to the DB and run the scripts.sql (you can copy/paste and run them all at once)
- From the project's root folder run: `mvn spring-boot:run`
- Access localhost:8080/login
- You can login with "user@test.com" or "admin@test.com" both have "pass" as password
- "user@test.com" has a role of USER and "admin@test.com" an ADMIN one (for the time being they have the same funcionality)
- Any new registered user will have the "USER" role as default
