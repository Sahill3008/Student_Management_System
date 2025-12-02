package org.course.student.controller;

import org.course.student.dto.DepartmentDTO;
import org.course.student.model.Department;
import org.course.student.service.DepartmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping
    public Department create(@RequestBody DepartmentDTO dto) {
        return service.create(dto);
    }

    @org.springframework.web.bind.annotation.GetMapping
    public java.util.List<Department> getAll() {
        return service.getAll();
    }

    @org.springframework.web.bind.annotation.GetMapping("/{id}")
    public Department get(@org.springframework.web.bind.annotation.PathVariable Long id) {
        return service.getById(id);
    }

    @org.springframework.web.bind.annotation.PutMapping("/{id}")
    public Department update(@org.springframework.web.bind.annotation.PathVariable Long id, @RequestBody DepartmentDTO dto) {
        return service.update(id, dto);
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    public void delete(@org.springframework.web.bind.annotation.PathVariable Long id) {
        service.delete(id);
    }
}
