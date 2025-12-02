package org.course.student.service;

import org.course.student.dto.CourseMaterialDTO;
import org.course.student.model.Course;
import org.course.student.model.CourseMaterial;
import org.course.student.repository.CourseMaterialRepository;
import org.course.student.repository.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseMaterialService {

    private final CourseMaterialRepository repo;
    private final CourseRepository courseRepo;

    public CourseMaterialService(CourseMaterialRepository repo, CourseRepository courseRepo) {
        this.repo = repo;
        this.courseRepo = courseRepo;
    }

    public CourseMaterial create(CourseMaterialDTO dto) {

        Course course = courseRepo.findById(dto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        CourseMaterial material = new CourseMaterial();
        material.setTitle(dto.getTitle());
        material.setResourceLink(dto.getResourceLink());
        material.setCourse(course);

        return repo.save(material);
    }
}
