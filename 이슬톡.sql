-- 이슬톡톡 회원가입 정보 테이블


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


--고객센터 게시판 
CREATE TABLE csboard(
board_number            NUMBER(5)  NOT NULL PRIMARY KEY,
board_title             VARCHAR2(100),
board_content           VARCHAR2(1000),
board_writer            VARCHAR2(20),
board_createdate        DATE,
board_updatedate        DATE,
board_secret            CHAR(1)
);

--고객센터 댓글
CREATE TABLE csreply(
board_number            NUMBER(5),
reply_numer             NUMBER(2),
reply_content           VARCHAR2(1000),
reply_writer            VARCHAR2(20),
reply_createdate        DATE,
reply_updatedate        DATE,
reply_secret            CHAR(1),
FOREIGN KEY (board_number) REFERENCES csboard(board_number) 
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
friend_block       CHAR(1),
FOREIGN KEY (id) REFERENCES member(id) 
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
   'dohyuk','Asdf1234','민도혁','dohyuk@email.com','010-1212-1212',
    '12345','부산 동구로 123','2222호','1','1',   sysdate);
    
INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                )  VALUES (
    'hanmone','Asdf1234','한모네','hanmone@email.com','010-1313-1313',
    '12345','서울시 종로구 종로 123','1111호','1','1',sysdate);
    
INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                )  VALUES (
   'rlatkdcjf86','$2a$10$uCdBWRN4j/bhDz1f237MLuRxsglBeKH6CpoGiQQuj4rCNcmhat7Ty','김상철','rlatkdcjf86@naver.com','010-7775-7510',
    '12345','인천시 서구 완정로 123','102호','1','1',sysdate);
INSERT INTO member(id, password, name, email, phone, zipcode, address1, address2, 
                usage, contract, createdate
                )  VALUES (
   'one99','One99','루피','ruppy@email.com','010-1577-1577',
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
VALUES(1, 'winter123');

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

----------------------------------------------------------------------------------------------


----------------------------------- 멤버 프로필 조회 테스트 ----------------------
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('rlatkdcjf86', '/images/profile/winter.png','/images/background/winterback.jpg', '상철상메성공', '상철대화명성공');
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('winter', '/images/profile/newjean.jpg', '/images/background/newjeanback.jpg', '겨울상태메세지나온다', '겨울대화명나온다');
INSERT INTO profile(id, profile_img, background_img, status_message, nick_name)
        VALUES('winter', '/images/profile/oneruffy.png', '/images/background/one.jpg', '고무고무가 그립다', '해적왕루피');        
        
----------------------------친구 추가 ----------------------------------
--나를 추가한 친구 '0'
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'winter', '0', '1');
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'one99', '0', '1');
            
            
--서로친구 '1'
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'winter', '1', '1');
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'one99', '1', '1');
            INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'winter', '1', '1');
INSERT INTO friend(id, friend_id, friend_approval, friend_block)
            VALUES('rlatkdcjf86', 'one99', '1', '1');

--------------------------------친구 목록 조회 --------------------
--서로친구
SELECT * FROM friend WHERE id='rlatkdcjf86' AND friend_approval=1;
SELECT * FROM friend f, profile p WHERE f.id='rlatkdcjf86' AND friend_approval=1;


--나를 추가한 친구
SELECT * FROM friend WHERE id='rlatkdcjf86' AND friend_approval=0;
SELECT * FROM friend f, profile p WHERE f.id='rlatkdcjf86' AND friend_approval=0;


-----------------------------채팅 조회 테스트 

SELECT * FROM chat_member WHERE room_number=1;

SELECT * FROM chat_message WHERE room_number=1;

SELECT *
FROM chat_member
WHERE room_number IN (SELECT room_number FROM chat_member WHERE member_id='hanmone');


-----------------------------채팅 조회 테스트 

SELECT * FROM chat_member WHERE room_number=1;

SELECT * FROM chat_message WHERE room_number=1;

SELECT *
FROM chat_member
WHERE room_number IN (SELECT room_number FROM chat_member WHERE member_id='hanmone');



----------------------------멤버 조회 ------------------------
SELECT * FROM member WHERE id='winter';

SELECT * FROM profile WHERE id='winter';

