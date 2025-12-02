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

    public java.util.List<CourseMaterial> getAll() {
        return repo.findAll();
    }

    public CourseMaterial getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("CourseMaterial not found"));
    }

    public CourseMaterial update(Long id, CourseMaterialDTO dto) {
        CourseMaterial material = getById(id);
        material.setTitle(dto.getTitle());
        material.setResourceLink(dto.getResourceLink());

        if (dto.getCourseId() != null) {
            Course course = courseRepo.findById(dto.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            material.setCourse(course);
        }

        return repo.save(material);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
