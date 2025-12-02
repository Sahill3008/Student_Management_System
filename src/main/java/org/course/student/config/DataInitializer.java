package org.course.student.config;

import org.course.student.model.*;
import org.course.student.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final InstructorRepository instructorRepository;
    private final EnrollmentRepository enrollmentRepository;

    public DataInitializer(StudentRepository studentRepository, CourseRepository courseRepository,
            DepartmentRepository departmentRepository, InstructorRepository instructorRepository,
            EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
        this.instructorRepository = instructorRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public void run(String... args) {

        // Prevent duplicate data on every restart
        if (studentRepository.count() > 0 || courseRepository.count() > 0) {
            System.out.println("📌 Database already has data. Skipping initialization.");
            return;
        }

        // Create Department
        Department dept = new Department("Computer Science");
        departmentRepository.save(dept);

        // Create Instructor
        Instructor instructor = new Instructor();
        instructor.setName("Dr. Smith");
        instructor.setEmail("smith@example.com");
        instructor.setDepartment(dept);
        instructorRepository.save(instructor);

        // Create courses
        Course course1 = new Course();
        course1.setCourseName("Mathematics");
        course1.setDescription("Basic Math");
        course1.setInstructor(instructor);

        Course course2 = new Course();
        course2.setCourseName("Physics");
        course2.setDescription("Basic Physics");
        course2.setInstructor(instructor);

        courseRepository.save(course1);
        courseRepository.save(course2);

        // Create student
        Student student = new Student();
        student.setName("Amit Sharma");
        student.setEmail("amit@example.com");
        student.setAge(20);
        student.setDepartment(dept);
        studentRepository.save(student);

        // Enroll student
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student);
        enrollment1.setCourse(course1);

        enrollment1.setGrade("A");
        enrollmentRepository.save(enrollment1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student);
        enrollment2.setCourse(course2);

        enrollment2.setGrade("B+");
        enrollmentRepository.save(enrollment2);

        System.out.println("✅ Sample data inserted successfully!");
    }
}