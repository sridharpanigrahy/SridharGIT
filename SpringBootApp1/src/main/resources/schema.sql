/*
DROP TABLE IF EXISTS TBL_EMPLOYEES;

CREATE TABLE EMPLOYEES (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  salary INT,
  email VARCHAR(250) DEFAULT NULL
);
*/

/*
DROP TABLE IF EXISTS users;
CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(45) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));

 DROP TABLE IF EXISTS user_roles;
 CREATE TABLE user_roles (
   user_role_id int(11) NOT NULL AUTO_INCREMENT,
   username varchar(45) NOT NULL,
   role varchar(45) NOT NULL,
   PRIMARY KEY (user_role_id),
   UNIQUE KEY uni_username_role (role,username),
   KEY fk_username_idx (username),
   CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));
   */