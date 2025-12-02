package org.course.student.controller;

import org.course.student.dto.CourseDTO;
import org.course.student.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CourseDTO create(@RequestBody CourseDTO dto) {
        return service.createCourse(dto);
    }

    @GetMapping
    public java.util.List<CourseDTO> getAll() {
        return service.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseDTO get(@PathVariable Long id) {
        return service.getCourseById(id);
    }

    @PutMapping("/{id}")
    public CourseDTO update(@PathVariable Long id, @RequestBody CourseDTO dto) {
        return service.updateCourse(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteCourse(id);
    }

    @Autowired
    private org.course.student.service.EnrollmentService enrollmentService;

    @GetMapping("/{id}/students")
    public java.util.List<org.course.student.dto.EnrollmentDTO> getEnrollments(@PathVariable Long id) {
        return enrollmentService.getEnrollmentsByCourse(id);
    }
}
