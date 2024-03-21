SET SERVEROUTPUT ON
DECLARE
    v_date DATE;
    v_day VARCHAR2(15);
BEGIN
    v_date := TO_DATE('23-MAR-2024', 'DD-MON-YYYY'); 
    v_day := RTRIM(TO_CHAR(v_date, 'DAY'));
    
    IF v_day IN ('SATURDAY', 'SUNDAY') THEN
        DBMS_OUTPUT.PUT_LINE (TO_CHAR(v_date, 'DD-MON-YYYY') || ' falls on a weekend.');
    ELSE
        DBMS_OUTPUT.PUT_LINE (TO_CHAR(v_date, 'DD-MON-YYYY') || ' does not fall on a weekend.');
    END IF;
    
    DBMS_OUTPUT.PUT_LINE ('Done...');
END;