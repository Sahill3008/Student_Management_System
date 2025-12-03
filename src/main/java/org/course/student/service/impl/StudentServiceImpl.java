package org.course.student.service.impl;

import org.course.student.dto.StudentDTO;
import org.course.student.exception.ResourceNotFoundException;
import org.course.student.mapper.StudentMapper;
import org.course.student.model.Department;
import org.course.student.model.Student;
import org.course.student.repository.DepartmentRepository;
import org.course.student.repository.StudentRepository;
import org.course.student.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;
    private final DepartmentRepository deptRepo;

    @org.springframework.beans.factory.annotation.Autowired
    private StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepo, DepartmentRepository deptRepo) {
        this.studentRepo = studentRepo;
        this.deptRepo = deptRepo;
    }

    @Override
    public StudentDTO createStudent(StudentDTO dto) {
        Student student = studentMapper.toEntity(dto);
        if (dto.getDepartmentId() != null) {
            Department dept = deptRepo.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found: " + dto.getDepartmentId()));
            student.setDepartment(dept);
        }
        Student saved = studentRepo.save(student);
        return studentMapper.toDTO(saved);
    }

    @Override
    public Page<StudentDTO> getAllStudents(Pageable pageable) {
        Page<Student> page = studentRepo.findAll(pageable);
        List<StudentDTO> list = page.stream().map(studentMapper::toDTO).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student s = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
        return studentMapper.toDTO(s);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO dto) {
        Student s = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));

        studentMapper.updateEntityFromDTO(dto, s);

        if (dto.getDepartmentId() != null) {
            Department d = deptRepo.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found: " + dto.getDepartmentId()));
            s.setDepartment(d);
        } else {
            s.setDepartment(null);
        }

        return studentMapper.toDTO(studentRepo.save(s));
    }

    @Override
    public void deleteStudent(Long id) {
        Student s = studentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
        studentRepo.delete(s);
    }
}
