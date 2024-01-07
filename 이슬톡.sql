--125번째줄까지만 그 아래는 테스트
-- 테이블 삭제
DROP TABLE member               CASCADE CONSTRAINTS; 
DROP TABLE profile              CASCADE CONSTRAINTS;
DROP TABLE friend               CASCADE CONSTRAINTS;
DROP TABLE chat_member          CASCADE CONSTRAINTS;
DROP TABLE chat_message         CASCADE CONSTRAINTS;
DROP TABLE csboard              CASCADE CONSTRAINTS;
DROP TABLE csreply              CASCADE CONSTRAINTS;
DROP TABLE chat_room            CASCADE CONSTRAINTS;
DROP SEQUENCE csboard_seq;
DROP SEQUENCE room_seq;
DROP SEQUENCE reply_seq;


-- 고객센터 게시판 시퀀스
CREATE SEQUENCE csboard_seq
    INCREMENT BY 1
    START WITH 1
    NOCACHE;


-- 채팅방번호 시퀀스    
CREATE SEQUENCE room_seq
    INCREMENT BY 1
    START WITH 1
    NOCACHE;
    
-- 댓글 시퀀스
CREATE SEQUENCE reply_seq
    INCREMENT BY 1
    START WITH 1
    NOCACHE;

--고객센터 게시판 
CREATE TABLE csboard(
board_number            NUMBER(5)  NOT NULL PRIMARY KEY,
board_title             VARCHAR2(100),
board_content           VARCHAR2(1000),
board_writer            VARCHAR2(20),
board_createdate        DATE,
board_updatedate        DATE,
board_secret            CHAR(1),
reply_count             NUMBER(2) DEFAULT '0'
);

--고객센터 댓글
CREATE TABLE csreply(
board_number            NUMBER(5),
reply_number            NUMBER(5), 
reply_content           VARCHAR2(1000),
reply_writer            VARCHAR2(20),
reply_createdate        DATE,
reply_updatedate        DATE,
reply_secret            CHAR(1),
reply_count             int DEFAULT '0',
reply_level             int DEFAULT '0',
reply_deep              int DEFAULT '0',
reply_ref               int DEFAULT '0',
FOREIGN KEY (board_number) REFERENCES csboard(board_number) on delete cascade
);



--회원가입정보
CREATE TABLE member(
id              VARCHAR2(20) PRIMARY KEY,
password        VARCHAR2(100),
name            VARCHAR2(20),
email           VARCHAR2(50),
phone           VARCHAR2(20),
zipcode         VARCHAR2(10),
address1        VARCHAR2(100),
address2        VARCHAR2(50),
usage           CHAR(1),
contract        CHAR(1),
createdate      DATE
);


--친구목록
CREATE TABLE friend(
id                 VARCHAR2(20),
friend_id          VARCHAR2(20),
friend_approval    CHAR(1),
friend_block       CHAR(1)
--,FOREIGN KEY (id) REFERENCES member(id) 
);


--회원 프로필정보
CREATE TABLE profile(
id                 VARCHAR2(20),
profile_img        VARCHAR2(100),
background_img     VARCHAR2(100),
status_message     VARCHAR2(100),
nick_name          VARCHAR2(100),
FOREIGN KEY (id) REFERENCES member(id) 
);

--채팅방번호
CREATE TABLE chat_room(
room_number              NUMBER(5) NOT NULL PRIMARY KEY,
room_createdate          DATE
);

--채팅방 인원
CREATE TABLE chat_member(
room_number             NUMBER(5) NOT NULL,
id                      VARCHAR2(20),
FOREIGN KEY (room_number) REFERENCES chat_room(room_number) 
);



--채팅방 메세지
CREATE TABLE chat_message(
room_number          NUMBER(5) NOT NULL,
message              VARCHAR2(1000),
id                   VARCHAR2(20),
message_check        CHAR(1),
message_createdate  DATE,
FOREIGN KEY (id) REFERENCES member(id),
FOREIGN KEY (room_number) REFERENCES chat_room(room_number) 
);


-------------------------------------여기까지만 테이블 생성------------------
-------------------------------아래는 테스트-----------------------
------------------------------- 테스트용 회원 추가 --------------------
INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                )  VALUES (
   'winter','$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty','한겨울','winter@email.com','010-1111-1111',
    '12345','서울시 종로구 종로 123','1111호','1','1',sysdate);

INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                ) VALUES (
   'gumlahee', '$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty', '금라희', 'lahee@email.com', '010-7777-7777',
    '12345', '서울시 강남구 언주로 123', '1212호', '1', '1',   sysdate);
    
    INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                ) VALUES (
   'dohyuk','$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty','민도혁','dohyuk@email.com','010-1212-1212',
    '12345','부산 동구로 123','2222호','1','1',   sysdate);
    
INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                )  VALUES (
    'hanmone','$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty','한모네','hanmone@email.com','010-1313-1313',
    '12345','서울시 종로구 종로 123','1111호','1','1',sysdate);
    
INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                )  VALUES (
   'rlatkdcjf86','$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty','김상철','rlatkdcjf86@naver.com','010-7775-7510',
    '12345','인천시 서구 완정로 123','102호','1','1',sysdate);
INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                )  VALUES (
   'one99','$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty','루피','ruppy@email.com','010-1577-1577',
    '12345','서울시 위대한 항로 123','봉우리1호','1','1',sysdate);
--------------------------------------------------------------


------------채팅방 생성 테스트----------------------------
INSERT INTO chat_room(room_number, room_createdate)
VALUES(room_seq.NEXTVAL, sysdate);



---------채팅방 참가 테스트 -------------------------------------------
INSERT INTO chat_member(room_number, id)
VALUES(1, 'rlatkdcjf86');
INSERT INTO chat_member(room_number, id)
VALUES(1, 'winter');

INSERT INTO chat_member(room_number, id)
VALUES(2, 'rlatkdcjf86');
INSERT INTO chat_member(room_number, id)
VALUES(2, 'hanmone');

INSERT INTO chat_member(room_number, id)
VALUES(3, 'rlatkdcjf86');
INSERT INTO chat_member(room_number, id)
VALUES(3, 'gumlahee');
INSERT INTO chat_member(room_number, id)
VALUES(3, 'dohyuk');
-----------------------------------------------------


--------------------------채팅 메시지 생성 테스트 ------
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (1, '안녕하세요', 'rlatkdcjf86', '1', sysdate);
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (1, '안녕하세요', 'winter', '1', sysdate);
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (1, '갈께요', 'winter', '1', sysdate);
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (1, '마지막메세지테스트', 'winter', '1', sysdate);
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (1, '웹소켓인서트확인용', 'rlatkdcjf86', '1', sysdate);
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (2, '대답해주세요', 'hanmone', '1', sysdate);
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (3, '내가 아들 맞다고', 'dohyuk', '1', sysdate);

----------------------------------------------------------------------------------------------


----------------------------------- 멤버 프로필 조회 테스트 ----------------------
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('rlatkdcjf86', '/images/profile/winter.png','/images/background/winterback.jpg', '상철상메성공', '상철대화명성공');
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('winter', '/images/profile/newjean.jpg', '/images/background/newjeanback.jpg', '겨울상태메세지나온다', '겨울대화명나온다');
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('one99', '/images/profile/oneruffy.png', '/images/background/one.jpg', '고무고무가 그립다', '해적왕루피');
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('dohyuk', '/images/profile/mindopro.png', '/images/background/mindoback.jpg', '두배로 갚아주겠어', '심준석아님');
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('hanmone', '/images/profile/monepro.png', '/images/background/moneback.jpg', '고마워 팽희야', '진짜엄마');
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('gumlahee', '/images/profile/laheepro.png', '/images/background/laheeback.jpg', '10억이야!!!', '금라');   
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('asdf1234', '/images/profile/no_image.png', '/images/background/no_image.png', '제발!!실행좀', '테스터임');          
----------------------------친구 추가 ----------------------------------
-- 서로 친구 '1'
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'winter', '1', '1');
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'one99', '1', '1');
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'asdf1234', '1', '1');            
            
-- 나를 추가한 친구 '0'
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'dohyuk', '0', '1');
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'gumlahee', '0', '1');
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'hanmone', '0', '1');
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('winter', 'one99', '0', '1');

--------------------------------친구 목록 조회 --------------------
--서로친구 + 프로필

SELECT * FROM profile WHERE 
id IN 
(SELECT friend_id FROM friend WHERE id='rlatkdcjf86' AND friend_approval=1) OR 
id IN 
(SELECT id FROM friend WHERE friend_id='rlatkdcjf86' AND friend_approval=1);

--나를 추가한 친구 + 프로필
SELECT * FROM profile WHERE id IN (SELECT friend_id FROM friend WHERE id='rlatkdcjf86' AND friend_approval=0);




--방번호별 참가자 프로필 연결
SELECT c.room_number, c.id, p.profile_img, p.background_img, p.nick_name, p.status_message, m.message, m.message_check, m.message_createdate
FROM chat_member c LEFT JOIN profile p ON c.id = p.id LEFT JOIN chat_message m ON c.room_number = m.room_number
WHERE c.room_number IN (SELECT room_number FROM chat_member WHERE id='rlatkdcjf86') ORDER BY m.message_createdate;
-- AND c.id NOT IN('rlatkdcjf86') AND m.id NOT IN('rlatkdcjf86');





WITH RankedMessages AS (
    SELECT
        c.room_number,
        c.id,
        p.profile_img,
        p.background_img,
        p.nick_name,
        p.status_message,
        m.message,
        m.message_check,
        m.message_createdate,
        ROW_NUMBER() OVER (PARTITION BY c.id ORDER BY m.message_createdate DESC) AS rnk
    FROM
        chat_member c
        LEFT JOIN profile p ON c.id = p.id
        LEFT JOIN chat_message m ON c.room_number = m.room_number
    WHERE
        c.room_number IN (SELECT room_number FROM chat_member WHERE id = 'rlatkdcjf86')
   -- AND c.id NOT IN ('rlatkdcjf86')
   -- AND m.id NOT IN ('rlatkdcjf86')
)
SELECT
    room_number,
    id,
    profile_img,
    background_img,
    nick_name,
    status_message, --지울거
    message,
    message_check,
    message_createdate
FROM
    RankedMessages
WHERE
    rnk = 1 ORDER BY message_createdate DESC;

--방번호별 참가자
SELECT *
FROM chat_member
WHERE room_number IN (SELECT room_number FROM chat_member WHERE id='rlatkdcjf86') AND
id NOT IN('rlatkdcjf86');


SELECT *
FROM chat_member
WHERE room_number IN (SELECT room_number FROM chat_member WHERE id='rlatkdcjf86');



--내가 참가한 방번호 목록
SELECT DISTINCT room_number
FROM chat_member
WHERE room_number IN (SELECT room_number FROM chat_member WHERE id='rlatkdcjf86');


-----------------------------채팅 조회 테스트 
SELECT c.room_number, c.id, c.message, c.message_check, c.message_createdate, p.profile_img, p.nick_name  FROM chat_message c LEFT JOIN profile p ON c.id = p.id WHERE room_number=1;


SELECT * FROM chat_member WHERE room_number=1;

SELECT * FROM chat_message WHERE room_number=1 AND id='winter';

SELECT *
FROM chat_member
WHERE room_number IN (SELECT room_number FROM chat_member WHERE member_id='hanmone');



----------------------------멤버 조회 ------------------------
SELECT * FROM member WHERE id='winter';

SELECT * FROM profile WHERE id='winter';



-----------------------------채팅 조회 테스트 

SELECT * FROM chat_member WHERE room_number=1;
--방 채팅 모두 불러오기
SELECT cm.room_number, cm.message, cm.id, cm.message_check, cm.message_createdate,
    p.profile_img, p.nick_name
FROM chat_message cm JOIN profile p ON cm.id = p.id
WHERE room_number=1 ORDER BY cm.message_createdate;



SELECT * FROM profile WHERE id='rlatkdcjf86';



-------------------------------- 댓글 조회 테스트

SELECT count(*)
        FROM csreply
        WHERE board_number = 127;

SELECT c.board_number, c.board_title, c.board_content, c.board_writer, 
            c.board_createdate,c.board_updatedate,c.board_secret, r.reply_number, 
            r.reply_content, r.reply_writer, r.reply_createdate, r.reply_updatedate, r.reply_secret
            FROM csboard c JOIN csreply r 
      ON c.board_number=r.board_number 
      WHERE c.board_number=127;
SELECT * FROM csreply
        WHERE board_number=127;

update csboard b
   set b.reply_count = (select count(reply_number) from csreply where board_number = 1)
   where b.board_number = 1;
--------------------------------------------------채팅 방생성  테스트
SELECT r.room_number, m.id FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='winter';

INSERT ALL
INTO chat_room(room_number, room_createdate)
VALUES(room_seq.NEXTVAL, sysdate)
INTO chat_member(room_number, id)
VALUES(4, 'rlatkdcjf86')
INTO chat_member(room_number, id)
VALUES(4, 'winter')
SELECT * FROM DUAL;

SELECT room_number FROM chat_room WHERE ROWNUM = 1 ORDER BY room_number DESC;
SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME='ROOM_SEQ';



INSERT ALL
INTO chat_room(room_number, room_createdate)
VALUES(room_seq.NEXTVAL, sysdate)
INTO chat_member(room_number, id)
VALUES((SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME='ROOM_SEQ'), 'rlatkdcjf86')
INTO chat_member(room_number, id)
VALUES((SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME='ROOM_SEQ'), 'winter')
SELECT * FROM DUAL;



SELECT r.room_number, m.id FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='rlatkdcjf86' OR m.id='one99';
SELECT r.room_number, m.id FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='rlatkdcjf86';
SELECT r.room_number FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='one99';
SELECT r.room_number, m.id FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number;
SELECT r.room_number, m.id FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='rlatkdcjf86' AND m.id='one99';




SELECT r.room_number FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE r.room_number 
IN (SELECT r.room_number FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='rlatkdcjf86')  AND id IN ('one99');

SELECT r.room_number FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='one99';



SELECT room_number, message, id, message_check, message_createdate, profile_img, nick_name
FROM (
    SELECT cm.room_number, cm.message, cm.id, cm.message_check, cm.message_createdate,
        p.profile_img, p.nick_name,
        ROW_NUMBER() OVER (ORDER BY cm.message_createdate) AS rnum
    FROM chat_message cm
    JOIN profile p ON cm.id = p.id
    WHERE cm.room_number = 1
)
WHERE rnum >= 3;

SELECT cm.room_number, cm.message, cm.id, cm.message_check, cm.message_createdate,
        p.profile_img, p.nick_name,
        ROW_NUMBER() OVER (ORDER BY cm.message_createdate) AS rnum
    FROM chat_message cm
    JOIN profile p ON cm.id = p.id
    WHERE cm.room_number = 1;

SELECT friend_id FROM friend WHERE NOT id = 'winter' AND NOT friend_id='winter';

SELECT * FROM profile WHERE id IN (SELECT friend_id FROM friend WHERE NOT id = 'winter' AND NOT friend_id='winter');


--- 친구 찾기 검색 용 -------------------------------------------------------------
SELECT m.id, m.phone, m.email, p.profile_img, p.nick_name FROM member m JOIN profile p ON m.id = p.id WHERE m.id IN (SELECT friend_id FROM friend WHERE NOT id = 'winter' AND NOT friend_id='winter')
AND m.id='rlatkdcjf86';
-- 필요한것 내아이디 .조건 .검색어 

SELECT * FROM profile WHERE id IN (SELECT id FROM member m WHERE NOT m.id='winter'
MINUS
SELECT friend_id FROM friend f WHERE NOT f.id='winter' AND NOT f.friend_id='winter');



--친구 추가 테스트


SELECT * FROM profile WHERE id IN (SELECT id FROM 
(SELECT id AS id FROM friend WHERE NOT id='winter' AND NOT friend_id='winter' GROUP BY id
UNION
SELECT friend_id AS id FROM friend WHERE NOT id='winter' AND NOT friend_id='winter' GROUP BY friend_id) WHERE NOT id IN (SELECT friend_id AS id FROM friend WHERE id = 'winter'
UNION
SELECT id AS id FROM friend WHERE friend_id = 'winter') AND id='one99');

SELECT id FROM 
(SELECT id AS id FROM friend WHERE NOT id='winter' AND NOT friend_id='winter' GROUP BY id
UNION
SELECT friend_id AS id FROM friend WHERE NOT id='winter' AND NOT friend_id='winter' GROUP BY friend_id) WHERE NOT id IN (SELECT friend_id AS id FROM friend WHERE id = 'winter'
UNION
SELECT id AS id FROM friend WHERE friend_id = 'winter') AND id='one99';

SELECT m.id, m.name, m.phone, m.email, p.profile_img
			FROM member m JOIN profile p ON m.id = p.id  WHERE m.id IN (SELECT id FROM 
			(SELECT id AS id FROM friend WHERE NOT id='winter' AND NOT friend_id='winter' GROUP BY id
			UNION
			SELECT friend_id AS id FROM friend WHERE NOT id='winter' AND NOT friend_id='winter' GROUP BY friend_id) WHERE NOT id IN (SELECT friend_id AS id FROM friend WHERE id = 'winter'
			UNION
			SELECT id AS id FROM friend WHERE friend_id = 'winter') AND id='hanmone');


SELECT * FROM profile WHERE id IN (SELECT id FROM friend WHERE friend_id='winter' AND friend_approval=0);

-------------------- 친구 신청 삭제 ---------------------------------
SELECT * FROM friend WHERE id='one99' AND friend_id='winter';

DELETE FROM friend WHERE id='one99' AND friend_id='winter';

SELECT * FROM friend WHERE id='winter' AND friend_id='one99';

UPDATE friend SET friend_approval = 1 WHERE id='winter' AND friend_id='one99';


UPDATE profile SET nick_name='abc' WHERE id='winter';



--------------------------------이미지 변경 ---------------------------------

UPDATE profile SET profile_img='abc' WHERE id='winter';

UPDATE profile SET background_img='abc' WHERE id='winter';


--------------------------비밀 번호 변경 --------------------
UPDATE member SET password='abc' WHERE id='winter';



------------------------------- 테스트용 회원 추가 --------------------
INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                )  VALUES (
   'winter','$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty','한겨울','winter@email.com','010-1111-1111',
    '12345','서울시 종로구 종로 123','1111호','1','1',sysdate);

INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                ) VALUES (
   'gumlahee', '$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty', '금라희', 'lahee@email.com', '010-7777-7777',
    '12345', '서울시 강남구 언주로 123', '1212호', '1', '1',   sysdate);
    
    INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                ) VALUES (
   'dohyuk','$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty','민도혁','dohyuk@email.com','010-1212-1212',
    '12345','부산 동구로 123','2222호','1','1',   sysdate);
    
INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                )  VALUES (
    'hanmone','$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty','한모네','hanmone@email.com','010-1313-1313',
    '12345','서울시 종로구 종로 123','1111호','1','1',sysdate);
    
INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                )  VALUES (
   'rlatkdcjf86','$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty','김상철','rlatkdcjf86@naver.com','010-7775-7510',
    '12345','인천시 서구 완정로 123','102호','1','1',sysdate);
INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                )  VALUES (
   'one99','$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty','루피','ruppy@email.com','010-1577-1577',
    '12345','서울시 위대한 항로 123','봉우리1호','1','1',sysdate);
--------------------------------------------------------------


------------채팅방 생성 테스트----------------------------
INSERT INTO chat_room(room_number, room_createdate)
VALUES(room_seq.NEXTVAL, sysdate);
INSERT INTO chat_room(room_number, room_createdate)
VALUES(room_seq.NEXTVAL, sysdate);
INSERT INTO chat_room(room_number, room_createdate)
VALUES(room_seq.NEXTVAL, sysdate);


---------채팅방 참가 테스트 -------------------------------------------
INSERT INTO chat_member(room_number, id)
VALUES(1, 'rlatkdcjf86');
INSERT INTO chat_member(room_number, id)
VALUES(1, 'winter');

INSERT INTO chat_member(room_number, id)
VALUES(2, 'rlatkdcjf86');
INSERT INTO chat_member(room_number, id)
VALUES(2, 'hanmone');

INSERT INTO chat_member(room_number, id)
VALUES(3, 'rlatkdcjf86');
INSERT INTO chat_member(room_number, id)
VALUES(3, 'gumlahee');
INSERT INTO chat_member(room_number, id)
VALUES(3, 'dohyuk');
-----------------------------------------------------


--------------------------채팅 메시지 생성 테스트 ------
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (1, '안녕하세요', 'rlatkdcjf86', '1', sysdate);
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (1, '안녕하세요', 'winter', '1', sysdate);
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (1, '갈께요', 'winter', '1', sysdate);
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (1, '마지막메세지테스트', 'winter', '1', sysdate);
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (1, '웹소켓인서트확인용', 'rlatkdcjf86', '1', sysdate);
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (2, '대답해주세요', 'hanmone', '1', sysdate);
INSERT INTO chat_message(room_number, message, id, message_check, message_createdate)
VALUES (3, '내가 아들 맞다고', 'dohyuk', '1', sysdate);

----------------------------------------------------------------------------------------------


----------------------------------- 멤버 프로필 조회 테스트 ----------------------
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('rlatkdcjf86', '/images/profile/winter.png','/images/background/winterback.jpg', '상철상메성공', '상철대화명성공');
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('winter', '/images/profile/newjean.jpg', '/images/background/newjeanback.jpg', '겨울상태메세지나온다', '겨울대화명나온다');
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('one99', '/images/profile/oneruffy.png', '/images/background/one.jpg', '고무고무가 그립다', '해적왕루피');
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('dohyuk', '/images/profile/mindopro.png', '/images/background/mindoback.jpg', '두배로 갚아주겠어', '심준석아님');
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('hanmone', '/images/profile/monepro.png', '/images/background/moneback.jpg', '고마워 팽희야', '진짜엄마');
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('gumlahee', '/images/profile/laheepro.png', '/images/background/laheeback.jpg', '10억이야!!!', '금라');   
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('asdf1234', '/images/profile/no_image.png', '/images/background/no_image.png', '제발!!실행좀', '테스터임');          
----------------------------친구 추가 ----------------------------------
-- 서로 친구 '1'
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'winter', '1', '1');
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'one99', '1', '1');
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'asdf1234', '1', '1');            
            
-- 나를 추가한 친구 '0'
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'dohyuk', '0', '1');
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'gumlahee', '0', '1');
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'hanmone', '0', '1');


--------------------------------친구 목록 조회 --------------------
--서로친구 + 프로필

SELECT * FROM profile WHERE 
id IN 
(SELECT friend_id FROM friend WHERE id='rlatkdcjf86' AND friend_approval=1) OR 
id IN 
(SELECT id FROM friend WHERE friend_id='rlatkdcjf86' AND friend_approval=1);

--나를 추가한 친구 + 프로필
SELECT * FROM profile WHERE id IN (SELECT friend_id FROM friend WHERE id='rlatkdcjf86' AND friend_approval=0);




--방번호별 참가자 프로필 연결
SELECT c.room_number, c.id, p.profile_img, p.background_img, p.nick_name, p.status_message, m.message, m.message_check, m.message_createdate
FROM chat_member c LEFT JOIN profile p ON c.id = p.id LEFT JOIN chat_message m ON c.room_number = m.room_number
WHERE c.room_number IN (SELECT room_number FROM chat_member WHERE id='rlatkdcjf86') ORDER BY m.message_createdate;
-- AND c.id NOT IN('rlatkdcjf86') AND m.id NOT IN('rlatkdcjf86');





WITH RankedMessages AS (
    SELECT
        c.room_number,
        c.id,
        p.profile_img,
        p.background_img,
        p.nick_name,
        p.status_message,
        m.message,
        m.message_check,
        m.message_createdate,
        ROW_NUMBER() OVER (PARTITION BY c.id ORDER BY m.message_createdate DESC) AS rnk
    FROM
        chat_member c
        LEFT JOIN profile p ON c.id = p.id
        LEFT JOIN chat_message m ON c.room_number = m.room_number
    WHERE
        c.room_number IN (SELECT room_number FROM chat_member WHERE id = 'rlatkdcjf86')
   -- AND c.id NOT IN ('rlatkdcjf86')
   -- AND m.id NOT IN ('rlatkdcjf86')
)
SELECT
    room_number,
    id,
    profile_img,
    background_img,
    nick_name,
    status_message, --지울거
    message,
    message_check,
    message_createdate
FROM
    RankedMessages
WHERE
    rnk = 1 ORDER BY message_createdate DESC;

--방번호별 참가자
SELECT *
FROM chat_member
WHERE room_number IN (SELECT room_number FROM chat_member WHERE id='rlatkdcjf86') AND
id NOT IN('rlatkdcjf86');


SELECT *
FROM chat_member
WHERE room_number IN (SELECT room_number FROM chat_member WHERE id='rlatkdcjf86');



--내가 참가한 방번호 목록
SELECT DISTINCT room_number
FROM chat_member
WHERE room_number IN (SELECT room_number FROM chat_member WHERE id='rlatkdcjf86');


-----------------------------채팅 조회 테스트 
SELECT c.room_number, c.id, c.message, c.message_check, c.message_createdate, p.profile_img, p.nick_name  FROM chat_message c LEFT JOIN profile p ON c.id = p.id WHERE room_number=1;


SELECT * FROM chat_member WHERE room_number=1;

SELECT * FROM chat_message WHERE room_number=1 AND id='winter';

SELECT *
FROM chat_member
WHERE room_number IN (SELECT room_number FROM chat_member WHERE member_id='hanmone');



----------------------------멤버 조회 ------------------------
SELECT * FROM member WHERE id='winter';

SELECT * FROM profile WHERE id='winter';



-----------------------------채팅 조회 테스트 

SELECT * FROM chat_member WHERE room_number=1;
--방 채팅 모두 불러오기
SELECT cm.room_number, cm.message, cm.id, cm.message_check, cm.message_createdate,
    p.profile_img, p.nick_name
FROM chat_message cm JOIN profile p ON cm.id = p.id
WHERE room_number=1 ORDER BY cm.message_createdate;



SELECT * FROM profile WHERE id='rlatkdcjf86';



-------------------------------- 댓글 조회 테스트

SELECT count(*)
        FROM csreply
        WHERE board_number = 127;

SELECT c.board_number, c.board_title, c.board_content, c.board_writer, 
            c.board_createdate,c.board_updatedate,c.board_secret, r.reply_number, 
            r.reply_content, r.reply_writer, r.reply_createdate, r.reply_updatedate, r.reply_secret
            FROM csboard c JOIN csreply r 
      ON c.board_number=r.board_number 
      WHERE c.board_number=127;
SELECT * FROM csreply
        WHERE board_number=127;

update csboard b
   set b.reply_count = (select count(reply_number) from csreply where board_number = 1)
   where b.board_number = 1;
--------------------------------------------------채팅 방생성  테스트
SELECT r.room_number, m.id FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='winter';

INSERT ALL
INTO chat_room(room_number, room_createdate)
VALUES(room_seq.NEXTVAL, sysdate)
INTO chat_member(room_number, id)
VALUES(4, 'rlatkdcjf86')
INTO chat_member(room_number, id)
VALUES(4, 'winter')
SELECT * FROM DUAL;

SELECT room_number FROM chat_room WHERE ROWNUM = 1 ORDER BY room_number DESC;
SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME='ROOM_SEQ';



INSERT ALL
INTO chat_room(room_number, room_createdate)
VALUES(room_seq.NEXTVAL, sysdate)
INTO chat_member(room_number, id)
VALUES((SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME='ROOM_SEQ'), 'rlatkdcjf86')
INTO chat_member(room_number, id)
VALUES((SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME='ROOM_SEQ'), 'winter')
SELECT * FROM DUAL;



SELECT r.room_number, m.id FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='rlatkdcjf86' OR m.id='one99';
SELECT r.room_number, m.id FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='rlatkdcjf86';
SELECT r.room_number FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='one99';
SELECT r.room_number, m.id FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number;
SELECT r.room_number, m.id FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='rlatkdcjf86' AND m.id='one99';




SELECT r.room_number FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE r.room_number 
IN (SELECT r.room_number FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='rlatkdcjf86')  AND id IN ('one99');

SELECT r.room_number FROM chat_room r JOIN chat_member m ON r.room_number = m.room_number WHERE m.id='one99';



SELECT room_number, message, id, message_check, message_createdate, profile_img, nick_name
FROM (
    SELECT cm.room_number, cm.message, cm.id, cm.message_check, cm.message_createdate,
        p.profile_img, p.nick_name,
        ROW_NUMBER() OVER (ORDER BY cm.message_createdate) AS rnum
    FROM chat_message cm
    JOIN profile p ON cm.id = p.id
    WHERE cm.room_number = 1
)
WHERE rnum >= 3;

SELECT cm.room_number, cm.message, cm.id, cm.message_check, cm.message_createdate,
        p.profile_img, p.nick_name,
        ROW_NUMBER() OVER (ORDER BY cm.message_createdate) AS rnum
    FROM chat_message cm
    JOIN profile p ON cm.id = p.id
    WHERE cm.room_number = 1;

SELECT friend_id FROM friend WHERE NOT id = 'winter' AND NOT friend_id='winter';

SELECT * FROM profile WHERE id IN (SELECT friend_id FROM friend WHERE NOT id = 'winter' AND NOT friend_id='winter');


--- 친구 찾기 검색 용 -------------------------------------------------------------
SELECT m.id, m.phone, m.email, p.profile_img, p.nick_name FROM member m JOIN profile p ON m.id = p.id WHERE m.id IN (SELECT friend_id FROM friend WHERE NOT id = 'winter' AND NOT friend_id='winter')
AND m.id='one99';
-- 필요한것 내아이디 .조건 .검색어 

SELECT * FROM profile WHERE id IN (SELECT id FROM member m WHERE NOT m.id='winter'
MINUS
SELECT friend_id FROM friend f WHERE NOT f.id='winter' AND NOT f.friend_id='winter');



commit;
