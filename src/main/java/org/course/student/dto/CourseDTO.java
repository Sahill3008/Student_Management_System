package org.course.student.dto;

import java.util.Set;

public class CourseDTO {
    private Long id;
    private String courseName;
    private String description;
    private Long instructorId;
    private String instructorName;
    private Set<EnrollmentDTO> enrollments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public Set<EnrollmentDTO> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<EnrollmentDTO> enrollments) {
        this.enrollments = enrollments;
    }
}
