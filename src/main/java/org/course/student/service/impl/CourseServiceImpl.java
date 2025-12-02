package org.course.student.service.impl;

import org.course.student.dto.CourseDTO;
import org.course.student.exception.ResourceNotFoundException;
import org.course.student.model.Course;
import org.course.student.repository.CourseRepository;
import org.course.student.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repo;

    private CourseDTO toDTO(Course c) {
        CourseDTO dto = new CourseDTO();
        dto.setId(c.getId());
        dto.setCourseName(c.getCourseName());
        dto.setDescription(c.getDescription());
        return dto;
    }

    private Course toEntity(CourseDTO dto) {
        Course c = new Course();
        c.setCourseName(dto.getCourseName());
        c.setDescription(dto.getDescription());
        return c;
    }

    @Override
    public CourseDTO createCourse(CourseDTO dto) {
        return toDTO(repo.save(toEntity(dto)));
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));
        return toDTO(c);
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO dto) {
        Course c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));

        c.setCourseName(dto.getCourseName());
        c.setDescription(dto.getDescription());

        return toDTO(repo.save(c));
    }

    @Override
    public void deleteCourse(Long id) {
        Course c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));
        c.getStudents().forEach(s -> s.getCourses().remove(c));
        repo.delete(c);
    }
}
