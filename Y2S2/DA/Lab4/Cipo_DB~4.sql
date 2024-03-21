SET SERVEROUTPUT ON
DECLARE
    v_instructor_id NUMBER := &sv_instructor_id; 
    v_section_count NUMBER;
BEGIN

    SELECT COUNT(*)
    INTO v_section_count
    FROM SECTION
    WHERE INSTRUCTOR_ID = v_instructor_id;

    IF v_section_count >= 3 THEN
        DBMS_OUTPUT.PUT_LINE ('This instructor needs a vacation.');
    ELSE
        DBMS_OUTPUT.PUT_LINE ('This instructor is teaching ' || v_section_count || ' sections.');
    END IF;
END;
/
