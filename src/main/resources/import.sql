INSERT INTO speaker(email, name) VALUES ('bungubbang@nate.com', 'bungubbang');
INSERT INTO audience(user_key) values ('w-179f11c659-16a21-17aa8-10173-1402563029809');
INSERT INTO audience(user_key) values ('w-179f11c659-16a21-17aa8-10173-1402563029808');

-- OX
INSERT INTO presentation_dashboard (choice_count, created_date, presentation_type, status, title, speaker_id) VALUES (null, CURRENT_DATE, 'OX', true, 'This is first Question!?', 1);
INSERT INTO answer(created_date, modify_date, result, user_agent, audience_id, dashboard_id) VALUES ('2014-06-12 17:55:27', '2014-06-12 17:55:29', 'O', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36', '1', '1');
INSERT INTO question(answer_list) VALUES ('O');
INSERT INTO question(answer_list) VALUES ('X');
INSERT INTO presentation_dashboard_questions(presentation_dashboard_id, questions_id) VALUES (1, 1);
INSERT INTO presentation_dashboard_questions(presentation_dashboard_id, questions_id) VALUES (1, 2);

-- MULTI CHOICE
INSERT INTO presentation_dashboard (choice_count, created_date, presentation_type, status, title, speaker_id) VALUES (4, CURRENT_DATE, 'MULTIPLE_CHOICE', true, 'What is your favorite color?', 1);
INSERT INTO answer(created_date, modify_date, result, user_agent, audience_id, dashboard_id) VALUES ('2014-06-12 17:55:27', '2014-06-12 17:55:29', '3', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36', '2', '2');
INSERT INTO question(answer_list) VALUES ('red');
INSERT INTO question(answer_list) VALUES ('blue');
INSERT INTO question(answer_list) VALUES ('white');
INSERT INTO question(answer_list) VALUES ('black');
INSERT INTO presentation_dashboard_questions(presentation_dashboard_id, questions_id) VALUES (2, 3);
INSERT INTO presentation_dashboard_questions(presentation_dashboard_id, questions_id) VALUES (2, 4);
INSERT INTO presentation_dashboard_questions(presentation_dashboard_id, questions_id) VALUES (2, 5);
INSERT INTO presentation_dashboard_questions(presentation_dashboard_id, questions_id) VALUES (2, 6);

-- QNS
INSERT INTO presentation_dashboard (choice_count, created_date, presentation_type, status, title, speaker_id) VALUES (null, CURRENT_DATE, 'QNA', true, 'Do you have any Questions?', 1);
INSERT INTO question(answer_list) VALUES ('Q');
INSERT INTO presentation_dashboard_questions(presentation_dashboard_id, questions_id) VALUES (3, 7);
