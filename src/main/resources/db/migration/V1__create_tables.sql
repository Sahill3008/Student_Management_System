CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE instructors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    bio VARCHAR(255)
);

CREATE TABLE students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    age INTEGER,
    department_id BIGINT REFERENCES departments(id)
);

CREATE TABLE student_profiles (
    id BIGSERIAL PRIMARY KEY,
    bio VARCHAR(255),
    student_id BIGINT REFERENCES students(id)
);

CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    instructor_id BIGINT REFERENCES instructors(id)
);
