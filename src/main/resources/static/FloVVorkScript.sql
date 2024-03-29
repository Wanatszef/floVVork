-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema flovvork
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema flovvork
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS flovvork DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE flovvork ;

-- -----------------------------------------------------
-- Table flovvork.`document_values`
-- -----------------------------------------------------
DROP TABLE IF EXISTS flovvork.`document_values` ;

CREATE TABLE IF NOT EXISTS flovvork.`document_values` (
                                                          document_values_id INT NOT NULL AUTO_INCREMENT,
                                                          text1 VARCHAR(45) NULL DEFAULT NULL,
    text2 VARCHAR(45) NULL DEFAULT NULL,
    text3 VARCHAR(45) NULL DEFAULT NULL,
    text4 VARCHAR(45) NULL DEFAULT NULL,
    text5 VARCHAR(45) NULL DEFAULT NULL,
    text6 VARCHAR(45) NULL DEFAULT NULL,
    text7 VARCHAR(45) NULL DEFAULT NULL,
    text8 VARCHAR(45) NULL DEFAULT NULL,
    text9 VARCHAR(45) NULL DEFAULT NULL,
    text10 VARCHAR(45) NULL DEFAULT NULL,
    text11 VARCHAR(45) NULL DEFAULT NULL,
    text12 VARCHAR(45) NULL DEFAULT NULL,
    text13 VARCHAR(45) NULL DEFAULT NULL,
    text14 VARCHAR(45) NULL DEFAULT NULL,
    text15 VARCHAR(45) NULL DEFAULT NULL,
    text16 VARCHAR(45) NULL DEFAULT NULL,
    text17 VARCHAR(45) NULL DEFAULT NULL,
    text18 VARCHAR(45) NULL DEFAULT NULL,
    text19 VARCHAR(45) NULL DEFAULT NULL,
    text20 VARCHAR(45) NULL DEFAULT NULL,
    number1 INT NULL DEFAULT NULL,
    number2 INT NULL DEFAULT NULL,
    number3 INT NULL DEFAULT NULL,
    number4 INT NULL DEFAULT NULL,
    number5 INT NULL DEFAULT NULL,
    number6 INT NULL DEFAULT NULL,
    number7 INT NULL DEFAULT NULL,
    number8 INT NULL DEFAULT NULL,
    number9 INT NULL DEFAULT NULL,
    number10 INT NULL DEFAULT NULL,
    date1 DATE NULL DEFAULT NULL,
    date2 DATE NULL DEFAULT NULL,
    date3 DATE NULL DEFAULT NULL,
    Date4 DATE NULL DEFAULT NULL,
    Date5 DATE NULL DEFAULT NULL,
    PRIMARY KEY (document_values_id))
    ENGINE = InnoDB
    AUTO_INCREMENT = 113
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table flovvork.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS flovvork.`roles` ;

CREATE TABLE IF NOT EXISTS flovvork.`roles` (
                                                role_id INT NOT NULL,
                                                name VARCHAR(45) NOT NULL,
    hierarchy INT NOT NULL,
    PRIMARY KEY (role_id))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table flovvork.`user_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS flovvork.`user_details` ;

CREATE TABLE IF NOT EXISTS flovvork.`user_details` (
                                                       user_details_id INT NOT NULL AUTO_INCREMENT,
                                                       first_name VARCHAR(45) NULL DEFAULT NULL,
    second_name VARCHAR(45) NULL DEFAULT NULL,
    email VARCHAR(45) NULL DEFAULT NULL,
    phone_number VARCHAR(45) NULL DEFAULT NULL,
    role_id INT NULL DEFAULT NULL,
    PRIMARY KEY (user_details_id),
    INDEX roleID_idx (role_id ASC) VISIBLE,
    CONSTRAINT roleID
    FOREIGN KEY (role_id)
    REFERENCES flovvork.`roles` (role_id))
    ENGINE = InnoDB
    AUTO_INCREMENT = 14
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table flovvork.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS flovvork.`user` ;

CREATE TABLE IF NOT EXISTS flovvork.`user` (
                                               id_user INT NOT NULL AUTO_INCREMENT,
                                               username VARCHAR(45) NOT NULL,
    password CHAR(68) NOT NULL,
    user_details_id INT NULL DEFAULT NULL,
    PRIMARY KEY (id_user),
    INDEX fk_user_1_idx (user_details_id ASC) VISIBLE,
    CONSTRAINT fk_user_1
    FOREIGN KEY (user_details_id)
    REFERENCES flovvork.`user_details` (user_details_id))
    ENGINE = InnoDB
    AUTO_INCREMENT = 6
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table flovvork.`document`
-- -----------------------------------------------------
DROP TABLE IF EXISTS flovvork.`document` ;

CREATE TABLE IF NOT EXISTS flovvork.`document` (
                                                   document_id INT NOT NULL AUTO_INCREMENT,
                                                   id_user INT NULL DEFAULT NULL,
                                                   document_values_id INT NULL DEFAULT NULL,
                                                   document_file VARCHAR(45) NULL DEFAULT NULL,
    active INT NULL DEFAULT NULL,
    expire_date DATE NULL DEFAULT NULL,
    create_date DATE NULL DEFAULT NULL,
    title VARCHAR(45) NULL DEFAULT NULL,
    update_date DATE NULL DEFAULT NULL,
    previous_user_id INT NULL DEFAULT NULL,
    PRIMARY KEY (document_id),
    INDEX fk_document_1_idx (id_user ASC) VISIBLE,
    INDEX fk_document_2_idx (previous_user_id ASC) VISIBLE,
    INDEX document_values_id_idx (document_values_id ASC) VISIBLE,
    CONSTRAINT document_values_id
    FOREIGN KEY (document_values_id)
    REFERENCES flovvork.`document_values` (document_values_id),
    CONSTRAINT fk_document_1
    FOREIGN KEY (id_user)
    REFERENCES flovvork.`user` (id_user),
    CONSTRAINT fk_document_2
    FOREIGN KEY (previous_user_id)
    REFERENCES flovvork.`user` (id_user))
    ENGINE = InnoDB
    AUTO_INCREMENT = 61
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table flovvork.`message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS flovvork.`message` ;

CREATE TABLE IF NOT EXISTS flovvork.`message` (
                                                  id INT NOT NULL AUTO_INCREMENT,
                                                  content TEXT NULL DEFAULT NULL,
                                                  timestamp TIMESTAMP NULL DEFAULT NULL,
                                                  sender_id INT NULL DEFAULT NULL,
                                                  receiver_id INT NULL DEFAULT NULL,
                                                  PRIMARY KEY (id),
    INDEX fk_message_1_idx (sender_id ASC) VISIBLE,
    INDEX fk_message_2_idx (receiver_id ASC) VISIBLE,
    CONSTRAINT fk_message_1
    FOREIGN KEY (sender_id)
    REFERENCES flovvork.`user` (id_user),
    CONSTRAINT fk_message_2
    FOREIGN KEY (receiver_id)
    REFERENCES flovvork.`user` (id_user))
    ENGINE = InnoDB
    AUTO_INCREMENT = 59
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table flovvork.`task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS flovvork.`task` ;

CREATE TABLE IF NOT EXISTS flovvork.`task` (
                                               idtask INT NOT NULL,
                                               task_name VARCHAR(45) NOT NULL,
    task_start_file VARCHAR(45) NOT NULL,
    PRIMARY KEY (idtask))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table flovvork.`task_access`
-- -----------------------------------------------------
DROP TABLE IF EXISTS flovvork.`task_access` ;

CREATE TABLE IF NOT EXISTS flovvork.`task_access` (
                                                      task_access_id INT NOT NULL,
                                                      id_task INT NULL DEFAULT NULL,
                                                      user_id INT NULL DEFAULT NULL,
                                                      PRIMARY KEY (task_access_id),
    INDEX document_name_idx (id_task ASC) VISIBLE,
    INDEX fk_task_access_1_idx (user_id ASC) VISIBLE,
    CONSTRAINT fk_task_access_1
    FOREIGN KEY (user_id)
    REFERENCES flovvork.`user` (id_user),
    CONSTRAINT id_task
    FOREIGN KEY (id_task)
    REFERENCES flovvork.`task` (idtask))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;