-- 이슬톡톡 회원가입 정보 테이블

DROP TABLE member;

CREATE TABLE member (
id              VARCHAR2(12) NOT NULL PRIMARY KEY,
password        VARCHAR2(16),
email           VARCHAR2(50),
name            VARCHAR2(10),
phone           VARCHAR2(13),
zipcode         VARCHAR2(10),
address1        VARCHAR2(50),
address2        VARCHAR2(50),
use             NUMBER(1) DEFAULT 0,
contract        NUMBER(1) DEFAULT 0
);


