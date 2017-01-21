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

-- towner data
INSERT INTO towner (towner_id, towner_name, email, password, gender, reg_date)
VALUES (1, '나무소리', 'namoosori@nextree.co.kr', '1234', 'M', sysdate);

INSERT INTO towner (towner_id, towner_name, email, password, gender, reg_date)
VALUES (2, '홍길동', 'gdhong@nextree.co.kr', '1234', 'M', sysdate);

-- club data
INSERT INTO club (club_id, club_name, description, open_date, admin_id)
VALUES (1, '축구 클럽', '축구를 사랑하는 사람들의 모임', sysdate, 1);

INSERT INTO club (club_id, club_name, description, open_date, admin_id)
VALUES (2, '농구 클럽', '농구를 사랑하는 사람들의 모임', sysdate, 2);

INSERT INTO club_member (member_id, club_id, towner_id, towner_email, reg_date)
VALUES ('1-1', 1, 1, 'namoosori@nextree.co.kr', sysdate);

INSERT INTO club_member (member_id, club_id, towner_id, towner_email, reg_date)
VALUES ('2-2', 2, 2, 'gdhong@nextree.co.kr', sysdate);


