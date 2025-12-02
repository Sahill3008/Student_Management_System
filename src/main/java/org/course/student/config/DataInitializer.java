package org.course.student.config;

import org.course.student.model.Course;
import org.course.student.model.Student;
import org.course.student.repository.CourseRepository;
import org.course.student.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public DataInitializer(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) {

        // Prevent duplicate data on every restart
        if (studentRepository.count() > 0 || courseRepository.count() > 0) {
            System.out.println("📌 Database already has data. Skipping initialization.");
            return;
        }

        // Create courses
        Course course1 = new Course();
        course1.setCourseName("Mathematics");
        course1.setDescription("Basic Math");

        Course course2 = new Course();
        course2.setCourseName("Physics");
        course2.setDescription("Basic Physics");

        courseRepository.save(course1);
        courseRepository.save(course2);

        // Create student
        Student student = new Student();
        student.setName("Amit Sharma");
        student.setEmail("amit@example.com");
        student.setAge(20);

        // Assign courses
        student.getCourses().add(course1);
        student.getCourses().add(course2);

        studentRepository.save(student);

        System.out.println("✅ Sample data inserted successfully!");
    }
}