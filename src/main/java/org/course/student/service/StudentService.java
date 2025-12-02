package org.course.student.service;

import org.course.student.dto.StudentDTO;
import org.course.student.model.Course;
import org.course.student.model.Student;
import org.course.student.repository.CourseRepository;
import org.course.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;

    public StudentService(StudentRepository studentRepo, CourseRepository courseRepo) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    public Student create(StudentDTO dto) {
        Student s = new Student();
        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        s.setAge(dto.getAge());
        return studentRepo.save(s);
    }

    public Student getById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public void delete(Long id) {
        studentRepo.deleteById(id);
    }

    public Student addCourse(Long studentId, Long courseId) {
        Student s = getById(studentId);
        Course c = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        s.getCourses().add(c);
        return studentRepo.save(s);
    }
}
