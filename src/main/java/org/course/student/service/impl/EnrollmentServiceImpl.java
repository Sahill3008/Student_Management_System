package org.course.student.service.impl;

import org.course.student.dto.EnrollmentDTO;
import org.course.student.exception.ResourceNotFoundException;
import org.course.student.mapper.EnrollmentMapper;
import org.course.student.model.Course;
import org.course.student.model.Enrollment;
import org.course.student.model.Student;
import org.course.student.repository.CourseRepository;
import org.course.student.repository.EnrollmentRepository;
import org.course.student.repository.StudentRepository;
import org.course.student.service.EnrollmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    @Override
    public EnrollmentDTO createEnrollment(Long studentId, Long courseId, String semester) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        Enrollment enrollment = new Enrollment(student, course, semester);
        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDTO(enrollment);
    }

    @Override
    public EnrollmentDTO updateGrade(Long enrollmentId, String grade) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with id: " + enrollmentId));
        enrollment.setGrade(grade);
        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDTO(enrollment);
    }

    @Override
    public List<EnrollmentDTO> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(enrollmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentDTO> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(enrollmentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
