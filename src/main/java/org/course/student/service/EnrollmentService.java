package org.course.student.service;

import org.course.student.dto.EnrollmentDTO;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDTO createEnrollment(Long studentId, Long courseId, String semester);

    EnrollmentDTO updateGrade(Long enrollmentId, String grade);

    List<EnrollmentDTO> getEnrollmentsByStudent(Long studentId);

    List<EnrollmentDTO> getEnrollmentsByCourse(Long courseId);
}
