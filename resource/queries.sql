--- 1.. Write a query that prints the number of sections of the first chapter of a textbook.
select count(*) as total_number_of_sections from `section` as sec
where sec.chapter_id = "chap01" and sec.textbook_id = 101;

--- 2. Print the names of faculty and TAs of all courses. For each person print their role next to their names.
select user.first_name, user.last_name, role.name as role_name, course.title
from course
inner join user on course.faculty_id = user.id
inner join role on user.role_id  = role.id
union
select user.first_name, user.last_name, "TA" as role_name, course.title
from ta_course_map tcm
inner join user on tcm.ta_user_id  = user.id
inner join active_course ac on ac.token = tcm.course_token
inner join course on ac.course_id  = course.id;

-- 3. For each active course, print the course id, faculty member and total number of students
select c.id as course_id , CONCAT(u.first_name , u.last_name) as faculty_member, count(*) as total_number_of_students
from `user` su
join enrollment e ON e.student_id = su.id
join active_course ac on ac.token  = e.course_token
join course c on c.id = ac.course_id
join `user` u on u.id = c.faculty_id
group by c.id, u.id, u.first_name , u.last_name ;

-- 4.Find the course which the largest waiting list, print the course id and the total number of people on the list
WITH temp as (
select course_token, count(*) as waitlist_size from pending_approval group by course_token having count(*) = (select max(wl_size) from (select count(*) as wl_size from pending_approval group by course_token) as temp_table)
)
select c.course_id, t.waitlist_size from active_course c inner join temp t on c.token = t.course_token;

-- Alternative Solution for this question

select c.id as course_id, count(*) as waitlist_size from course c join active_course a on c.id = a.course_id join pending_approval p on a.token = p.course_token group by course_token having count(*) = (select max(wl_size) from (select count(*) as wl_size from pending_approval group by course_token) as temp_table);


--- 5. Print the contents of Chapter 02 of textbook 101 in proper sequence.
SELECT
   c.section_id,
   c.content_type,
   c.content AS content_text,
   a.unique_activity_id,
   q.question_text,
   q.option_1,
   q.explanation_1,
   q.option_2,
   q.explanation_2,
   q.option_3,
   q.explanation_3,
   q.option_4,
   q.explanation_4,
   q.correct_option
FROM
   content_block AS c
LEFT JOIN
   activity AS a ON c.block_id = a.content_block_id
               AND c.section_id = a.section_id
               AND c.chapter_id = a.chapter_id
               AND c.textbook_id = a.textbook_id
LEFT JOIN
   question AS q ON a.id = q.activity_id
               AND a.section_id = q.section_id
               AND a.chapter_id = q.chapter_id
               AND a.textbook_id = q.textbook_id
WHERE
   c.chapter_id = 'chap02'
   AND c.textbook_id = 101
   AND c.hidden = 'no' -- Only show visible content blocks
ORDER BY
   c.section_id, -- Ensures sections are grouped together in the chapter
   CASE
       WHEN c.content_type = 'text' THEN 1
       WHEN c.content_type = 'picture' THEN 2
       WHEN c.content_type = 'activity' THEN 3
   END;

-- For Q2 of Activity0, print the incorrect answers for that question and their corresponding explanations.
select q.question_id, q.question_text,
CASE WHEN correct_option != 1 THEN option_1 END AS incorrect_option_1,
CASE WHEN correct_option != 1 THEN explanation_1 END AS explanation_1,
CASE WHEN correct_option != 2 THEN option_2 END AS incorrect_option_2,
CASE WHEN correct_option != 2 THEN explanation_2 END AS explanation_2,
CASE WHEN correct_option != 3 THEN option_3 END AS incorrect_option_3,
CASE WHEN correct_option != 3 THEN explanation_3 END AS explanation_3,
CASE WHEN correct_option != 4 THEN option_4 END AS incorrect_option_4,
CASE WHEN correct_option != 4 THEN explanation_4 END AS explanation_4
from question q
join activity a on a.id  = q.activity_id
where a.unique_activity_id ="ACT0"
and a.textbook_id = 101
and a.chapter_id = 'chap01'
and q.question_id = "Q2";

-- 7. Find any book that is in active status by one instructor but evaluation status by a different instructor.
select distinct c1.textbook_id from course c1 join course c2 on c1.textbook_id = c2.textbook_id where c1.faculty_id != c2.faculty_id and c1.type != c2.type;
