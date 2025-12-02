package org.course.student.controller;

import org.course.student.dto.StudentDTO;
import org.course.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@CrossOrigin
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public StudentDTO create(@RequestBody StudentDTO dto) {
        return service.createStudent(dto);
    }

    @GetMapping
    public org.springframework.data.domain.Page<StudentDTO> getAll(org.springframework.data.domain.Pageable pageable) {
        return service.getAllStudents(pageable);
    }

    @GetMapping("/{id}")
    public StudentDTO get(@PathVariable Long id) {
        return service.getStudentById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteStudent(id);
    }

}
