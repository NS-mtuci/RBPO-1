-- Пользователи
INSERT INTO USERS (id, username, password, role)
VALUES (1, 'teacher', '$2a$12$eAT1xrxfGYPvp8lZ1atMK.t1pgxSOydeKGy/sX7b2mi6N/LAda8Ei', 'TEACHER');

INSERT INTO USERS (id, username, password, role)
VALUES (2, 'student', '$2a$12$eAT1xrxfGYPvp8lZ1atMK.t1pgxSOydeKGy/sX7b2mi6N/LAda8Ei', 'STUDENT');

ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 3;

-- Студенты
INSERT INTO STUDENTS (id, name, email, user_id)
VALUES (1, 'Alice', 'alice@example.com', 2),
       (2, 'Bob', 'bob@example.com', NULL);

ALTER TABLE STUDENTS ALTER COLUMN ID RESTART WITH 3;

-- Курсы
INSERT INTO COURSES (id, title, description, teacher_id)
VALUES (1, 'RBPO', 'Basics of securing apps', 1),
       (2, 'Database Basics', 'Intro to SQL', 1);

ALTER TABLE COURSES ALTER COLUMN ID RESTART WITH 3;