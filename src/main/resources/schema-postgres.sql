DROP TABLE IF EXISTS users;
CREATE TABLE users(user_id serial PRIMARY KEY, firstname VARCHAR(255), lastname VARCHAR(255), login VARCHAR (255), dateofbirth DATE, active BOOLEAN);

