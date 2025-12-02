CREATE TABLE IF NOT EXISTS course_materials (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    resource_link VARCHAR(255),
    course_id BIGINT REFERENCES courses(id)
);
