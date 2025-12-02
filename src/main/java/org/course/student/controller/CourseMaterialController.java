package org.course.student.controller;

import org.course.student.dto.CourseMaterialDTO;
import org.course.student.model.CourseMaterial;
import org.course.student.service.CourseMaterialService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materials")
public class CourseMaterialController {

    private final CourseMaterialService service;

    public CourseMaterialController(CourseMaterialService service) {
        this.service = service;
    }

    @PostMapping
    public CourseMaterial create(@RequestBody CourseMaterialDTO dto) {
        return service.create(dto);
    }
}
