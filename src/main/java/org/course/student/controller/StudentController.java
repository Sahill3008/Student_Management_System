package org.course.student.controller;

import org.course.student.dto.StudentDTO;
import org.course.student.model.Student;
import org.course.student.service.StudentService;
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
    public Student create(@RequestBody StudentDTO dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{id}/courses/{cid}")
    public Student addCourse(@PathVariable Long id, @PathVariable Long cid) {
        return service.addCourse(id, cid);
    }
}
