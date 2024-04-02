DECLARE
    v_section_id SECTION.SECTION_ID%TYPE := 1; 
    v_num_students NUMBER; --store nr of students registered in a section

    --custom exception
    e_no_students_enrolled EXCEPTION;
BEGIN
    -- getting the number of students registered in the provided section
    SELECT COUNT(*)
    INTO v_num_students
    FROM ENROLLMENT
    WHERE SECTION_ID = v_section_id;

    --check if the number of students is equal to 0
    IF v_num_students = 0 THEN
        --raise custom exception e_no_students_enrolled and display an error message
        RAISE_APPLICATION_ERROR(-20001, 'PROBLEM: 0 STUDENTS IN THIS SECTION ' || v_section_id);
    ELSE
        --display the number of students in the section
        DBMS_OUTPUT.PUT_LINE('Number of students in section ' || v_section_id || ': ' || v_num_students);
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No data found for section ' || v_section_id);
    WHEN e_no_students_enrolled THEN
        DBMS_OUTPUT.PUT_LINE(SQLERRM);
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END;
/
