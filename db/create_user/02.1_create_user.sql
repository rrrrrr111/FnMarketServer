-----------------------------------------------------------------
-- Скрипт создания пользователя БД
-----------------------------------------------------------------

SET SERVEROUTPUT ON SIZE UNLIMITED
SET HEADING OFF
SET FEEDBACK OFF
SET TERMOUT OFF
SET VERIFY OFF
SET TRIMOUT ON
SET TRIMSPOOL ON
SET AUTOPRINT OFF
SET SHOWMODE ON
SET TIME ON
SET PAGESIZE 0 LINESIZE 2000 LONGCHUNKSIZE 2000 LONG 1000000

SPOOL _02.1_create_user.log

PROMPT Запущен скрипт создания пользователя БД

DEFINE USERNAME=''
DEFINE USERPWD=''
DEFINE DEFTBS=''
DEFINE TMPTBS=''

COLUMN USER_NAME NEW_VALUE USERNAME
COLUMN USER_PWD NEW_VALUE USERPWD
COLUMN DEF_TBS NEW_VALUE DEFTBS
COLUMN TMP_TBS NEW_VALUE TMPTBS

SELECT UPPER ('&1') AS USER_NAME
     ,  LOWER ('&2') AS USER_PWD
     ,  UPPER ('&3') AS DEF_TBS
     ,  UPPER ('&4') AS TMP_TBS
  FROM DUAL
/


PROMPT Запущен скрипт создания пользователя &&USERNAME. 

SET HEADING OFF
SET ECHO ON

DROP USER &&USERNAME. CASCADE
/

DECLARE
   l_usr_name   all_users.username%TYPE;
   csql         VARCHAR2 (2000);
BEGIN

   SELECT username
     INTO l_usr_name
     FROM all_users
    WHERE username = UPPER ('&&USERNAME.');

   DBMS_OUTPUT.put_line ('Пользователь &&USERNAME. уже существует!');
EXCEPTION
   WHEN NO_DATA_FOUND
   THEN
      csql := 'create user &&USERNAME. identified by &&USERPWD. DEFAULT TABLESPACE &&DEFTBS. TEMPORARY TABLESPACE &&TMPTBS.';

      EXECUTE IMMEDIATE csql;

      DBMS_OUTPUT.put_line (csql);
   WHEN OTHERS
   THEN
      DBMS_OUTPUT.put_line (SQLERRM);
      RAISE;
END;
/


@@02.2_grant_user.sql

PROMPT Пользователь &&USERNAME. создан

SPOOL OFF

UNDEF USERNAME
UNDEF USERPWD
UNDEF DEFTBS
UNDEF TMPTBS

exit