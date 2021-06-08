CREATE DATABASE ws_students_db;

CREATE TABLE "students" (
                            id bigserial NOT NULL,
                            name character varying(200),
                            surname character varying(200),
                            age integer,
                            student_id integer,
                            mark character varying(50),
                            CONSTRAINT "Students_pkey" PRIMARY KEY (id)
);

INSERT INTO students(name, surname, age, student_id, mark) values ('Петр', 'Петров', 25, 234541, 'хорошо');
INSERT INTO students(name, surname, age, student_id, mark) values ('Владимир', 'Иванов', 26, 234542, 'отлично');
INSERT INTO students(name, surname, age, student_id, mark) values ('Иван', 'Иванов', 27, 234543, 'удовлетворительно');
INSERT INTO students(name, surname, age, student_id, mark) values ('Иммануил', 'Кант', 28, 234544, 'неудовлетворительно');
INSERT INTO students(name, surname, age, student_id, mark) values ('Джордж', 'Клуни', 29, 234545, 'отлично');
INSERT INTO students(name, surname, age, student_id, mark) values ('Билл', 'Рубцов', 30, 234546, 'хорошо');
INSERT INTO students(name, surname, age, student_id, mark) values ('Марк', 'Марков', 31, 234547, 'удовлетворительно');