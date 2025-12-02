package org.course.student.service;

import org.course.student.dto.InstructorDTO;
import java.util.List;

public interface InstructorService {
    InstructorDTO createInstructor(InstructorDTO instructorDTO);

    InstructorDTO getInstructorById(Long id);

    List<InstructorDTO> getAllInstructors();

    InstructorDTO updateInstructor(Long id, InstructorDTO instructorDTO);

    void deleteInstructor(Long id);
}
