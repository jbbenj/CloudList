# -----------------------------------------
# SQL script to create the tables for the 
# CloudList Database (CloudListDB)
# Created by Afiq Yusof for CloudList app
# -----------------------------------------

/*
Tables to be dropped must be listed in a logical order based on dependency.
UserFile and UserPhoto depend on User. Therefore, they must be dropped before User.
*/
DROP TABLE IF EXISTS PublicBook, UserBook, PublicAlbum, UserAlbum, PublicMovie, UserMovie, UserFile, UserPhoto, User;

/* The User table contains attributes of interest of a User. */
CREATE TABLE User
(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(256) NOT NULL,         /* To store Salted and Hashed Password Parts */
    first_name VARCHAR(32) NOT NULL,
    middle_name VARCHAR(32),
    last_name VARCHAR(32) NOT NULL,
    cc_number VARCHAR(19) NOT NULL,         /* Credit Card Number, max 19 digits */
    cc_expires VARCHAR(4) NOT NULL,         /* Expiration date in MMYY format, e.g., 0523 meaning May 2023 */
    cc_security_code VARCHAR(4) NOT NULL,   /* American Express 4 digits; other credit cards 3 digits */
    address1 VARCHAR(128) NOT NULL,
    address2 VARCHAR(128),
    city VARCHAR(64) NOT NULL,
    state VARCHAR(2) NOT NULL,
    zipcode VARCHAR(10) NOT NULL,           /* e.g., 24060-1804 */
    security_question_number INT NOT NULL,  /* Refers to the number of the selected security question */
    security_answer VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,      
    PRIMARY KEY (id)
);

/* The UserPhoto table contains attributes of interest of a user's photo. */
CREATE TABLE UserPhoto
(
       id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       extension ENUM('jpeg', 'jpg', 'png', 'gif') NOT NULL,
       user_id INT UNSIGNED,
       FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);


/* The UserFile table contains attributes of interest of a user's uploaded file. */
CREATE TABLE UserFile
(
       id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       filename VARCHAR(256) NOT NULL,
       user_id INT UNSIGNED,
       FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

CREATE TABLE PublicMovie
(
	   id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	   title VARCHAR(255) NOT NULL,
	   youtube_trailer_id VARCHAR(32) NOT NULL,
	   genres VARCHAR(128) NOT NULL,
	   release_date DATE NOT NULL,
	   director VARCHAR(128) NOT NULL,
	   stars VARCHAR(255) NOT NULL,
	   film_rating VARCHAR(8) NOT NULL,   /* e.g., PG-13 */
	   percent_liked VARCHAR(8) NOT NULL,
       average_price INT UNSIGNED,
	   PRIMARY KEY (id)
);

CREATE TABLE UserMovie
(
	   id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       user_id INT UNSIGNED,
	   title VARCHAR(255) NOT NULL,
	   youtube_trailer_id VARCHAR(32) NOT NULL,
	   genres VARCHAR(128) NOT NULL,
	   release_date DATE NOT NULL,
	   director VARCHAR(128) NOT NULL,
	   stars VARCHAR(255) NOT NULL,
	   film_rating VARCHAR(8) NOT NULL,   /* e.g., PG-13 */
	   percent_liked VARCHAR(8) NOT NULL,
       average_price INT UNSIGNED,
	   FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

CREATE TABLE PublicAlbum
(
	   id INT UNSIGNED NOT NULL AUTO_INCREMENT,
       title VARCHAR(255) NOT NULL,
       artist VARCHAR(128) NOT NULL,
	   release_year INT UNSIGNED NOT NULL,
       track_num INT UNSIGNED NOT NULL,
       genres VARCHAR(128) NOT NULL,
       average_price INT UNSIGNED,
       PRIMARY KEY (id)
);

CREATE TABLE UserAlbum
(
	   id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       user_id INT UNSIGNED,
       title VARCHAR(255) NOT NULL,
       artist VARCHAR(128) NOT NULL,
       album VARCHAR(255) NOT NULL,
	   release_year INT UNSIGNED NOT NULL,
       track_num INT UNSIGNED NOT NULL,
       genres VARCHAR(128) NOT NULL,
       average_price INT UNSIGNED,
       FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);

CREATE TABLE PublicBook
(
	   id INT UNSIGNED NOT NULL AUTO_INCREMENT,
       title VARCHAR(255) NOT NULL,
       author VARCHAR(128) NOT NULL,
       publication_year INT UNSIGNED NOT NULL,
       isbn INT UNSIGNED NOT NULL,
       genres VARCHAR(128) NOT NULL,
       average_price INT UNSIGNED,
       PRIMARY KEY (id)
);

CREATE TABLE UserBook
(
	   id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       user_id INT UNSIGNED,
       title VARCHAR(255) NOT NULL,
       author VARCHAR(128) NOT NULL,
       publication_year INT UNSIGNED NOT NULL,
       isbn INT UNSIGNED NOT NULL,
       genres VARCHAR(128) NOT NULL,
       average_price INT UNSIGNED,
       FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
);