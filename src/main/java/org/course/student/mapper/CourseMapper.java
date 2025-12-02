package org.course.student.mapper;

import org.course.student.dto.CourseDTO;
import org.course.student.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { EnrollmentMapper.class })
public interface CourseMapper {

    @Mapping(source = "instructor.id", target = "instructorId")
    @Mapping(source = "instructor.name", target = "instructorName")
    CourseDTO toDTO(Course course);

    @Mapping(source = "instructorId", target = "instructor.id")
    Course toEntity(CourseDTO courseDTO);

    @Mapping(source = "instructorId", target = "instructor.id")
    void updateEntityFromDTO(CourseDTO dto, @org.mapstruct.MappingTarget Course course);
}
