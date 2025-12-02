package org.course.student.mapper;

import org.course.student.dto.StudentDTO;
import org.course.student.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { EnrollmentMapper.class })
public interface StudentMapper {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.deptName", target = "departmentName")
    StudentDTO toDTO(Student student);

    @Mapping(source = "departmentId", target = "department.id")
    Student toEntity(StudentDTO studentDTO);

    @Mapping(source = "departmentId", target = "department.id")
    void updateEntityFromDTO(StudentDTO dto, @org.mapstruct.MappingTarget Student student);
}
