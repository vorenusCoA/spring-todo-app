# spring-todo-app
Just another TODO App but using Spring MVC and Spring Security

If you want to try it locally:
- Run: 'docker compose up' to start mysql
- With a MySQL client connect to the DB and run the scripts.sql (you can copy/paste and run them all at once)
- Start the SpringBoot App from your IDE
- Access localhost:8080
- You can login with "user@test.com" or "admin@test.com" both have "pass" as password.
- "user@test.com" has a role of USER and "admin@test.com" an ADMIN one (for the time being they have the same funcionality)
