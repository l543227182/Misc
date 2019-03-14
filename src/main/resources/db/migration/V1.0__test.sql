use test;
	CREATE TABLE testUpCase(
	`id` INT(11) PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(30) NOT NULL COMMENT 'name'
	)
	 DEFAULT CHARSET=utf8 COMMENT 'test';

INSERT INTO  testUpCase(name) VALUES ("testUpCase")