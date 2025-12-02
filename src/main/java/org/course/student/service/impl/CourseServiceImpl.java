package org.course.student.service.impl;

import org.course.student.dto.CourseDTO;
import org.course.student.exception.ResourceNotFoundException;
import org.course.student.mapper.CourseMapper;
import org.course.student.model.Course;
import org.course.student.model.Instructor;
import org.course.student.repository.CourseRepository;
import org.course.student.repository.InstructorRepository;
import org.course.student.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repo;
    private final InstructorRepository instructorRepo;
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository repo, InstructorRepository instructorRepo, CourseMapper courseMapper) {
        this.repo = repo;
        this.instructorRepo = instructorRepo;
        this.courseMapper = courseMapper;
    }

    @Override
    public CourseDTO createCourse(CourseDTO dto) {
        Course course = courseMapper.toEntity(dto);
        if (dto.getInstructorId() != null) {
            Instructor instructor = instructorRepo.findById(dto.getInstructorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Instructor not found: " + dto.getInstructorId()));
            course.setInstructor(instructor);
        }
        return courseMapper.toDTO(repo.save(course));
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return repo.findAll().stream().map(courseMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));
        return courseMapper.toDTO(c);
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO dto) {
        Course c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));

        courseMapper.updateEntityFromDTO(dto, c);

        if (dto.getInstructorId() != null) {
            Instructor instructor = instructorRepo.findById(dto.getInstructorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Instructor not found: " + dto.getInstructorId()));
            c.setInstructor(instructor);
        } else {
            c.setInstructor(null);
        }

        return courseMapper.toDTO(repo.save(c));
    }

    @Override
    public void deleteCourse(Long id) {
        Course c = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));
        repo.delete(c);
    }
}
