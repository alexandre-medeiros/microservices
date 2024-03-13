delete from tb_lessons;
delete from tb_modules;
delete from tb_courses;

-- Insert data into tb_courses table
INSERT INTO tb_courses (id, course_level, course_status, creation_date, description, image_url, last_update_date, name, user_instructor)
values ('f60a230e-7833-41d0-85e9-b4aa78a6b741', 'BEGINNER', 'INPROGRESS', '2023-01-01 08:00:00', 'Python Course', 'python_image.jpg', '2023-01-05 12:30:00', 'Python', 'a0a230e7-7833-41d0-85e9-b4aa78a6b742');
INSERT INTO tb_courses (id, course_level, course_status, creation_date, description, image_url, last_update_date, name, user_instructor)
values ('f60a230e-7833-41d0-85e9-b4aa78a6b743', 'INTERMEDIARY', 'INPROGRESS', '2023-02-01 10:00:00', 'Java Course', 'java_image.jpg', '2023-02-10 15:45:00', 'Java', 'a0a230e7-7833-41d0-85e9-b4aa78a6b744');
INSERT INTO tb_courses (id, course_level, course_status, creation_date, description, image_url, last_update_date, name, user_instructor)
values ('f60a230e-7833-41d0-85e9-b4aa78a6b745', 'ADVANCED', 'INPROGRESS', '2023-03-01 09:30:00', 'MongoDB Course', 'mongodb_image.jpg', '2023-03-15 14:15:00', 'MongoDB', 'a0a230e7-7833-41d0-85e9-b4aa78a6b746');

-- Insert data for tb_modules table
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b747', '2023-01-01 08:00:00', 'Introduction to Python', 'Module 1', 'f60a230e-7833-41d0-85e9-b4aa78a6b741');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b748', '2023-01-02 09:15:00', 'Data Types in Python', 'Module 2', 'f60a230e-7833-41d0-85e9-b4aa78a6b741');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b749', '2023-01-03 10:30:00', 'Control Flow in Python', 'Module 3', 'f60a230e-7833-41d0-85e9-b4aa78a6b741');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b750', '2023-01-04 11:45:00', 'Functions in Python', 'Module 4', 'f60a230e-7833-41d0-85e9-b4aa78a6b741');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b751', '2023-01-05 13:00:00', 'Advanced Python Concepts', 'Module 5', 'f60a230e-7833-41d0-85e9-b4aa78a6b741');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b752', '2023-02-01 10:00:00', 'Introduction to Java', 'Module 1', 'f60a230e-7833-41d0-85e9-b4aa78a6b743');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b753', '2023-02-02 11:15:00', 'Data Types in Java', 'Module 2', 'f60a230e-7833-41d0-85e9-b4aa78a6b743');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b754', '2023-02-03 12:30:00', 'Control Flow in Java', 'Module 3', 'f60a230e-7833-41d0-85e9-b4aa78a6b743');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b755', '2023-02-04 13:45:00', 'Functions in Java', 'Module 4', 'f60a230e-7833-41d0-85e9-b4aa78a6b743');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b756', '2023-02-05 15:00:00', 'Object-Oriented Programming in Java', 'Module 5', 'f60a230e-7833-41d0-85e9-b4aa78a6b743');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b757', '2023-02-06 16:15:00', 'Exception Handling in Java', 'Module 6', 'f60a230e-7833-41d0-85e9-b4aa78a6b743');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b758', '2023-02-07 17:30:00', 'Multithreading in Java', 'Module 7', 'f60a230e-7833-41d0-85e9-b4aa78a6b743');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b759', '2023-02-08 18:45:00', 'Java Networking', 'Module 8', 'f60a230e-7833-41d0-85e9-b4aa78a6b743');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b760', '2023-03-01 09:30:00', 'Introduction to MongoDB', 'Module 1', 'f60a230e-7833-41d0-85e9-b4aa78a6b745');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b761', '2023-03-02 10:45:00', 'CRUD Operations in MongoDB', 'Module 2', 'f60a230e-7833-41d0-85e9-b4aa78a6b745');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b762', '2023-03-03 12:00:00', 'Indexing and Aggregation in MongoDB', 'Module 3', 'f60a230e-7833-41d0-85e9-b4aa78a6b745');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b763', '2023-03-04 13:15:00', 'Data Modeling in MongoDB', 'Module 4', 'f60a230e-7833-41d0-85e9-b4aa78a6b745');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b764', '2023-03-05 14:30:00', 'Transactions and Security in MongoDB', 'Module 5', 'f60a230e-7833-41d0-85e9-b4aa78a6b745');
INSERT INTO tb_modules (id, creation_date, description, title, course_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b765', '2023-03-06 15:45:00', 'Advanced MongoDB Features', 'Module 6', 'f60a230e-7833-41d0-85e9-b4aa78a6b745');

-- Insert data for tb_lessons table (Python Modules)
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b766', '2023-01-01 08:00:00', 'Python Basics', 'Lesson 1', 'python_lesson1.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b747');
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b767', '2023-01-02 09:15:00', 'Variables and Data Types', 'Lesson 2', 'python_lesson2.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b748');
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b768', '2023-01-03 10:30:00', 'Control Structures', 'Lesson 3', 'python_lesson3.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b749');
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b769', '2023-01-04 11:45:00', 'Functions and Modules', 'Lesson 4', 'python_lesson4.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b750');
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b770', '2023-01-05 13:00:00', 'Advanced Python Topics', 'Lesson 5', 'python_lesson5.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b751');

-- Insert data for tb_lessons table (Java Modules)
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b771', '2023-02-01 10:00:00', 'Java Basics', 'Lesson 1', 'java_lesson1.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b752');
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b772', '2023-02-02 11:15:00', 'Data Types in Java', 'Lesson 2', 'java_lesson2.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b753');
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b773', '2023-02-03 12:30:00', 'Control Flow in Java', 'Lesson 3', 'java_lesson3.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b754');
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b774', '2023-02-04 13:45:00', 'Functions in Java', 'Lesson 4', 'java_lesson4.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b755');
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b775', '2023-02-05 15:00:00', 'Object-Oriented Programming in Java', 'Lesson 5', 'java_lesson5.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b756');

-- Insert data for tb_lessons table (MongoDB Modules)
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b776', '2023-03-01 09:30:00', 'MongoDB Basics', 'Lesson 1', 'mongodb_lesson1.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b760');
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b777', '2023-03-02 10:45:00', 'CRUD Operations in MongoDB', 'Lesson 2', 'mongodb_lesson2.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b761');
INSERT INTO tb_lessons (id, creation_date, description, title, video_url, module_id)
VALUES ('f60a230e-7833-41d0-85e9-b4aa78a6b778', '2023-03-03 12:00:00', 'Indexing and Aggregation in MongoDB', 'Lesson 3', 'mongodb_lesson3.mp4', 'f60a230e-7833-41d0-85e9-b4aa78a6b762');
