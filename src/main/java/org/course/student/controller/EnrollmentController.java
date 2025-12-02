package org.course.student.controller;

import org.course.student.dto.EnrollmentDTO;
import org.course.student.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PutMapping("/{id}/grade")
    public ResponseEntity<EnrollmentDTO> updateGrade(@PathVariable Long id, @RequestParam String grade) {
        return ResponseEntity.ok(enrollmentService.updateGrade(id, grade));
    }
}
