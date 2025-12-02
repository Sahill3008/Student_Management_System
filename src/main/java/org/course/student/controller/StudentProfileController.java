package org.course.student.controller;

import org.course.student.dto.StudentProfileDTO;
import org.course.student.model.StudentProfile;
import org.course.student.service.StudentProfileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
public class StudentProfileController {

    private final StudentProfileService service;

    public StudentProfileController(StudentProfileService service) {
        this.service = service;
    }

    @PostMapping
    public StudentProfile create(@RequestBody StudentProfileDTO dto) {
        return service.create(dto);
    }

    @org.springframework.web.bind.annotation.GetMapping
    public java.util.List<StudentProfile> getAll() {
        return service.getAll();
    }

    @org.springframework.web.bind.annotation.GetMapping("/{id}")
    public StudentProfile get(@org.springframework.web.bind.annotation.PathVariable Long id) {
        return service.getById(id);
    }

    @org.springframework.web.bind.annotation.PutMapping("/{id}")
    public StudentProfile update(@org.springframework.web.bind.annotation.PathVariable Long id, @RequestBody StudentProfileDTO dto) {
        return service.update(id, dto);
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    public void delete(@org.springframework.web.bind.annotation.PathVariable Long id) {
        service.delete(id);
    }
}
