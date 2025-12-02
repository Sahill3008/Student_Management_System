package org.course.student.controller;

import org.course.student.dto.CourseDTO;
import org.course.student.model.Course;
import org.course.student.service.CourseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@CrossOrigin
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping
    public Course create(@RequestBody CourseDTO dto) {
        return service.create(dto);
    }
}
