package org.course.student.service;

import org.course.student.dto.CourseDTO;
import org.course.student.model.Course;
import org.course.student.repository.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepo;

    public CourseService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    public Course create(CourseDTO dto) {
        Course c = new Course();
        c.setCourseName(dto.getCourseName());
        c.setDescription(dto.getDescription());
        return courseRepo.save(c);
    }
}
