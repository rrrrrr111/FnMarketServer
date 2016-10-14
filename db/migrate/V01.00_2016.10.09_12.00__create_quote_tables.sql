CREATE SEQUENCE ID_SEQ;


CREATE TABLE sbrf_splice__m1 (
  ID             NUMBER(18)   NOT NULL,
  OPEN_PRICE     NUMBER(20)   NOT NULL,
  HIGHT_PRICE    NUMBER(20)   NOT NULL,
  LOW_PRICE      NUMBER(20)   NOT NULL,
  CLOSE_PRICE    NUMBER(20)   NOT NULL,
  CLOSE_DATETIME TIMESTAMP(0) NOT NULL,
  VOLUME         NUMBER(20)   NOT NULL,
  CONSTRAINT sbrf_splice_m1_pk PRIMARY KEY (CLOSE_DATETIME)
);


CREATE TABLE sbrf_splice__d1 (
  ID             NUMBER(18)   NOT NULL,
  OPEN_PRICE     NUMBER(20)   NOT NULL,
  HIGHT_PRICE    NUMBER(20)   NOT NULL,
  LOW_PRICE      NUMBER(20)   NOT NULL,
  CLOSE_PRICE    NUMBER(20)   NOT NULL,
  CLOSE_DATETIME TIMESTAMP(0) NOT NULL,
  VOLUME         NUMBER(20)   NOT NULL,
  CONSTRAINT sbrf_splice_d1_pk PRIMARY KEY (CLOSE_DATETIME)
);