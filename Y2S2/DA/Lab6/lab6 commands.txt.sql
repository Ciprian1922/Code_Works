DECLARE
    -- Cursor to fetch students with last name beginning with J
    CURSOR c_student IS
        SELECT *
        FROM STUDENT
        WHERE LAST_NAME LIKE 'J%';

    -- Cursor to fetch courses for a given student
    CURSOR c_course (p_student_id STUDENT.STUDENT_ID%TYPE) IS
        SELECT sec.SECTION_ID, c.DESCRIPTION
        FROM COURSE c
        JOIN SECTION sec ON c.COURSE_NO = sec.COURSE_NO
        JOIN ENROLLMENT e ON sec.SECTION_ID = e.SECTION_ID
        WHERE e.STUDENT_ID = p_student_id;

    --cursor to fetch grades for a given student and course
    CURSOR c_grade (p_section_id SECTION.SECTION_ID%TYPE, p_student_id STUDENT.STUDENT_ID%TYPE) IS
        SELECT AVG(
                   CASE
                       WHEN e.FINAL_GRADE = 'A' THEN 4
                       WHEN e.FINAL_GRADE = 'B' THEN 3
                       WHEN e.FINAL_GRADE = 'C' THEN 2
                       WHEN e.FINAL_GRADE = 'D' THEN 1
                       ELSE 0 --F is 0
                   END
                 ) AS avg_grade
        FROM ENROLLMENT e
        WHERE e.SECTION_ID = p_section_id AND e.STUDENT_ID = p_student_id;
BEGIN
    -- Cursor to fetch students with last name beginning with J
    FOR student_rec IN c_student LOOP
        -- Display student name
        DBMS_OUTPUT.PUT_LINE('Student Name: ' || student_rec.FIRST_NAME || ' ' || student_rec.LAST_NAME);
        
        -- Cursor to fetch courses for the current student
        FOR course_rec IN c_course(student_rec.STUDENT_ID) LOOP
            -- Display course description
            DBMS_OUTPUT.PUT_LINE('Course Description: ' || course_rec.DESCRIPTION);
            
            -- Cursor to fetch grades for the current student and course
            FOR grade_rec IN c_grade(course_rec.SECTION_ID, student_rec.STUDENT_ID) LOOP
                -- Display average grade
                DBMS_OUTPUT.PUT_LINE('Average Grade: ' || grade_rec.avg_grade);
            END LOOP;
        END LOOP;
    END LOOP;
END;
/

SELECT *
FROM STUDENT
WHERE FIRST_NAME = 'Michael' AND LAST_NAME = 'Johnson';

SELECT e.FINAL_GRADE
FROM STUDENT s
JOIN ENROLLMENT e ON s.STUDENT_ID = e.STUDENT_ID
JOIN SECTION sec ON e.SECTION_ID = sec.SECTION_ID
JOIN COURSE c ON sec.COURSE_NO = c.COURSE_NO
WHERE s.FIRST_NAME = 'Michael'
AND s.LAST_NAME = 'Johnson'
AND c.DESCRIPTION = 'Web Development';

