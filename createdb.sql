CREATE TABLE IF NOT EXISTS users (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	username varchar(32) NOT NULL UNIQUE,
	password binary(60) NOT NULL,
	roles varchar(63) NOT NULL
);

CREATE TABLE IF NOT EXISTS books (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	isbn varchar(15) NOT NULL,
	title varchar(255) NOT NULL,
	authors varchar(255) NOT NULL,
	description varchar(512) NOT NULL
);
