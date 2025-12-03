package org.course.student.service;

import org.course.student.dto.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface StudentService {
    StudentDTO createStudent(StudentDTO dto);

    Page<StudentDTO> getAllStudents(Pageable pageable);

    StudentDTO getStudentById(Long id);

    StudentDTO updateStudent(Long id, StudentDTO dto);

    void deleteStudent(Long id);
}
