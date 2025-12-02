package org.course.student.service.impl;

import org.course.student.dto.StudentDTO;
import org.course.student.exception.ResourceNotFoundException;
import org.course.student.model.Course;
import org.course.student.model.Student;
import org.course.student.repository.CourseRepository;
import org.course.student.repository.StudentRepository;
import org.course.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private CourseRepository courseRepo;

    private StudentDTO toDTO(Student s) {
        StudentDTO dto = new StudentDTO();
        dto.setId(s.getId());
        dto.setName(s.getName());
        dto.setEmail(s.getEmail());
        dto.setAge(s.getAge());

        dto.setCourseIds(
                s.getCourses().stream()
                        .map(Course::getId)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    private Student toEntity(StudentDTO dto) {
        Student s = new Student();
        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        s.setAge(dto.getAge());

        if (dto.getCourseIds() != null) {
            Set<Course> courses = new HashSet<>(courseRepo.findAllById(dto.getCourseIds()));
            s.setCourses(courses);
        }

        return s;
    }

    @Override
    public StudentDTO createStudent(StudentDTO dto) {
        Student saved = studentRepo.save(toEntity(dto));
        return toDTO(saved);
    }

    @Override
    public Page<StudentDTO> getAllStudents(Pageable pageable) {
        Page<Student> page = studentRepo.findAll(pageable);
        List<StudentDTO> list = page.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student s = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
        return toDTO(s);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO dto) {
        Student s = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));

        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        s.setAge(dto.getAge());

        if (dto.getCourseIds() != null) {
            Set<Course> courses = new HashSet<>(courseRepo.findAllById(dto.getCourseIds()));
            s.setCourses(courses);
        }

        return toDTO(studentRepo.save(s));
    }

    @Override
    public void deleteStudent(Long id) {
        Student s = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));

        s.getCourses().forEach(c -> c.getStudents().remove(s));
        studentRepo.delete(s);
    }

    @Override
    @Transactional
    public StudentDTO addCourse(Long studentId, Long courseId) {
        Student s = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));

        Course c = courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        s.getCourses().add(c);
        c.getStudents().add(s);

        return toDTO(s);
    }
}
