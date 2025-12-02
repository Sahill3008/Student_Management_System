package org.course.student.service.impl;

import org.course.student.dto.InstructorDTO;
import org.course.student.exception.ResourceNotFoundException;
import org.course.student.model.Department;
import org.course.student.model.Instructor;
import org.course.student.repository.DepartmentRepository;
import org.course.student.repository.InstructorRepository;
import org.course.student.service.InstructorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final DepartmentRepository departmentRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository, DepartmentRepository departmentRepository) {
        this.instructorRepository = instructorRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public InstructorDTO createInstructor(InstructorDTO instructorDTO) {
        Instructor instructor = new Instructor();
        instructor.setName(instructorDTO.getName());
        instructor.setEmail(instructorDTO.getEmail());

        if (instructorDTO.getDepartmentName() != null) {
            Department department = departmentRepository.findByDeptName(instructorDTO.getDepartmentName())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found with name: " + instructorDTO.getDepartmentName()));
            instructor.setDepartment(department);
        }

        Instructor savedInstructor = instructorRepository.save(instructor);
        return mapToDTO(savedInstructor);
    }

    @Override
    public InstructorDTO getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));
        return mapToDTO(instructor);
    }

    @Override
    public List<InstructorDTO> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InstructorDTO updateInstructor(Long id, InstructorDTO instructorDTO) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));

        instructor.setName(instructorDTO.getName());
        instructor.setEmail(instructorDTO.getEmail());

        if (instructorDTO.getDepartmentName() != null) {
            Department department = departmentRepository.findByDeptName(instructorDTO.getDepartmentName())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found with name: " + instructorDTO.getDepartmentName()));
            instructor.setDepartment(department);
        }

        Instructor updatedInstructor = instructorRepository.save(instructor);
        return mapToDTO(updatedInstructor);
    }

    @Override
    public void deleteInstructor(Long id) {
        if (!instructorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Instructor not found with id: " + id);
        }
        instructorRepository.deleteById(id);
    }

    private InstructorDTO mapToDTO(Instructor instructor) {
        InstructorDTO dto = new InstructorDTO();
        dto.setId(instructor.getId());
        dto.setName(instructor.getName());
        dto.setEmail(instructor.getEmail());
        if (instructor.getDepartment() != null) {
            dto.setDepartmentName(instructor.getDepartment().getDeptName());
        }
        return dto;
    }
}
