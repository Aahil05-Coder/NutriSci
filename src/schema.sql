-- schema.sql

CREATE TABLE IF NOT EXISTS user_profile (
  id         INT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  gender     VARCHAR(10)  NOT NULL,
  dob        DATE         NOT NULL,
  height_cm  INT          NOT NULL,
  weight_kg  INT          NOT NULL
);

CREATE TABLE IF NOT EXISTS ingredient (
  id                  INT AUTO_INCREMENT PRIMARY KEY,
  name                VARCHAR(255) UNIQUE NOT NULL,
  calories_per_unit   DOUBLE       NOT NULL
);

CREATE TABLE IF NOT EXISTS meal_record (
  id         INT AUTO_INCREMENT PRIMARY KEY,
  user_id    INT          NOT NULL,
  date       DATE         NOT NULL,
  meal_type  VARCHAR(50)  NOT NULL,
  ingredient VARCHAR(255) NOT NULL,
  quantity   DOUBLE       NOT NULL,
  unit       VARCHAR(50)  NOT NULL,
  calories   DOUBLE       NOT NULL
);


CREATE TABLE IF NOT EXISTS swap_goal (
  id         INT AUTO_INCREMENT PRIMARY KEY,
  nutrient   VARCHAR(100) NOT NULL,
  direction  VARCHAR(20)  NOT NULL,
  amount     DOUBLE       NOT NULL
);
