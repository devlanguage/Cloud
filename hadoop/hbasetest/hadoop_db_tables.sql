SQL> SET LONG 999999
SQL> set serveroutput on size 999999
SQL> SET LINESIZE 100
SQL> SELECT DBMS_SQLTUNE.REPORT_TUNING_TASK( 'tuning_sql_test') from DUAL;