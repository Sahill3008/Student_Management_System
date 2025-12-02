package org.course.student.controller;

import org.course.student.dto.InstructorDTO;
import org.course.student.service.InstructorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
@CrossOrigin
public class InstructorController {

    private final InstructorService service;

    public InstructorController(InstructorService service) {
        this.service = service;
    }

    @PostMapping
    public InstructorDTO create(@RequestBody InstructorDTO dto) {
        return service.createInstructor(dto);
    }

    @GetMapping
    public List<InstructorDTO> getAll() {
        return service.getAllInstructors();
    }

    @GetMapping("/{id}")
    public InstructorDTO get(@PathVariable Long id) {
        return service.getInstructorById(id);
    }

    @PutMapping("/{id}")
    public InstructorDTO update(@PathVariable Long id, @RequestBody InstructorDTO dto) {
        return service.updateInstructor(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteInstructor(id);
    }
}
