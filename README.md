# Student Management System

A full-stack Student Management System built with Spring Boot and React.

## Features

- **Students**: Manage students, assign courses, view enrollments.
- **Courses**: Manage courses, view enrolled students.
- **Enrollments**: Track student enrollments, grades, and semesters.
- **Security**: JWT-based authentication (Admin/User roles).
- **Architecture**: Clean architecture with DTOs, Mappers (MapStruct), and Services.
- **Database**: PostgreSQL with Flyway migrations.

## Tech Stack

- **Backend**: Java 17, Spring Boot 3, Spring Security, Spring Data JPA, Flyway, MapStruct, PostgreSQL.
- **Frontend**: React, Vite, Tailwind CSS, React Query, React Hook Form, Zod.

## Setup & Running

### Backend

1.  Ensure PostgreSQL is running and a database named `studentcoursedb` exists.
2.  Configure database credentials in `src/main/resources/application.properties`.
3.  Run the application:
    ```bash
    ./mvnw spring-boot:run
    ```
    Flyway will automatically apply migrations.

### Frontend

1.  Navigate to the frontend directory:
    ```bash
    cd frontend
    ```
2.  Install dependencies:
    ```bash
    npm install
    ```
3.  Start the development server:
    ```bash
    npm run dev
    ```

## API Endpoints

### Auth
- `POST /api/auth/login`: Login and get JWT token.

### Students
- `GET /students`: List all students.
- `GET /students/{id}`: Get student details.
- `POST /students`: Create a student.
- `PUT /students/{id}`: Update a student.
- `DELETE /students/{id}`: Delete a student.
- `POST /students/{id}/courses/{courseId}?semester=...`: Assign a course to a student.
- `GET /students/{id}/courses`: Get enrollments for a student.

### Courses
- `GET /courses`: List all courses.
- `GET /courses/{id}`: Get course details.
- `POST /courses`: Create a course.
- `PUT /courses/{id}`: Update a course.
- `DELETE /courses/{id}`: Delete a course.
- `GET /courses/{id}/students`: Get enrollments for a course.

### Enrollments
- `PUT /api/enrollments/{id}/grade?grade=...`: Update grade for an enrollment.

## Project Structure

- `src/main/java/org/course/student`:
    - `controller`: REST Controllers.
    - `service`: Business logic interfaces and implementations.
    - `repository`: JPA Repositories.
    - `model`: JPA Entities.
    - `dto`: Data Transfer Objects.
    - `mapper`: MapStruct Mappers.
    - `security`: JWT Authentication configuration.
- `src/main/resources/db/migration`: Flyway SQL migrations.
- `frontend`: React application.
