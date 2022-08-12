CREATE TABLE USERS (
  id BINARY(16) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE (email)
);


INSERT INTO USERS (id, email, password) VALUES (unhex(replace(uuid(), '-', '')), 'user@test.com', '$2a$10$a07FaSKwo2xAwEj4UJYa0etu8sY5o9onG/0psQ2FxzjviueQUYnbm');
INSERT INTO USERS (id, email, password) VALUES (unhex(replace(uuid(), '-', '')), 'admin@test.com', '$2a$10$a07FaSKwo2xAwEj4UJYa0etu8sY5o9onG/0psQ2FxzjviueQUYnbm');


CREATE TABLE ACTIVITIES (
  id BINARY(16) NOT NULL,
  user_id BINARY(16) NOT NULL,
  description VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES USERS(id)
);


CREATE TABLE AUTHORITIES (
  id BINARY(16) NOT NULL,
  role VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO AUTHORITIES (id, role) VALUES (unhex(replace(UUID(), '-', '')), 'USER');
INSERT INTO AUTHORITIES (id, role) VALUES (unhex(replace(UUID(), '-', '')), 'ADMIN');

CREATE TABLE USERS_AUTHORITIES (
  id BINARY(16) NOT NULL,
  user_id BINARY(16) NOT NULL,
  authority_id BINARY(16) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES USERS(id),
  FOREIGN KEY (authority_id) REFERENCES AUTHORITIES(id)
);

CREATE UNIQUE INDEX ix_user
  on USERS_AUTHORITIES (user_id);


SELECT @user1 := id FROM USERS WHERE email = "user@test.com";
SELECT @user2 := id FROM USERS WHERE email = "admin@test.com";
SELECT @role1 := id FROM AUTHORITIES WHERE role = "USER";
SELECT @role2 := id FROM AUTHORITIES WHERE role = "ADMIN";
  
INSERT INTO USERS_AUTHORITIES (id, user_id, authority_id) VALUES (unhex(replace(UUID(), '-', '')), @user1, @role1);
INSERT INTO USERS_AUTHORITIES (id, user_id, authority_id) VALUES (unhex(replace(UUID(), '-', '')), @user2, @role2);
  

CREATE TABLE persistent_logins ( 
  username VARCHAR(100) NOT NULL, 
  series VARCHAR(64) PRIMARY KEY, 
  token VARCHAR(64) NOT NULL, 
  last_used TIMESTAMP NOT NULL 
);
