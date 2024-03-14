DECLARE
    v_total_days NUMBER := &sv_total_days;
    v_years NUMBER;
    v_months NUMBER;
    v_remaining_days NUMBER;
BEGIN
    v_years := v_total_days / 365;
    v_months := MOD(v_total_days, 365) / 30;
    v_remaining_days := MOD(v_total_days, 365) MOD 30;

    DBMS_OUTPUT.PUT_LINE('years: ' || TRUNC(v_years));
    DBMS_OUTPUT.PUT_LINE('months: ' || TRUNC(v_months));
    DBMS_OUTPUT.PUT_LINE('days: ' || v_remaining_days);
END;
