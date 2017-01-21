DROP TABLE club_member IF EXISTS;
DROP TABLE towner IF EXISTS; 
DROP TABLE club IF EXISTS;

-- club
CREATE TABLE club ( 
	club_id        INTEGER       AUTO_INCREMENT PRIMARY KEY,
	club_name      VARCHAR(50)   NOT NULL,
	description    VARCHAR(1000) NULL,
	open_date      DATETIME      NOT NULL,
	admin_id       INTEGER       NOT NULL
);
		
-- club_member
CREATE TABLE club_member (
	member_id      VARCHAR(100)  PRIMARY KEY,
	club_id        INTEGER       NOT NULL,
	towner_id      INTEGER       NOT NULL,
	towner_email   VARCHAR(200)  NOT NULL,
	reg_date       DATETIME      NOT NULL -- 조회는 하지 않는 데이터 입력일시 참고 목적
);
		
-- towner
CREATE TABLE towner (
	towner_id      INTEGER       AUTO_INCREMENT PRIMARY KEY,
	towner_name    VARCHAR(50)   NOT NULL,
	email          VARCHAR(100)  NOT NULL,
	password       VARCHAR(50)   NOT NULL,
	gender         CHAR(1)       NULL,
	reg_date       DATETIME      NOT NULL -- 조회는 하지 않는 데이터 입력일시 참고 목적
);