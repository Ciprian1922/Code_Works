select myData.* from (

select function(column name, 'input data') as relevance, col2, col, 3 from table

) as myData order by relevance desc limit 0, 20
/*testare func*/

select function(column name, 'input data') as test from dual;


select 'mihai' as nume, 22 as age from dual;




