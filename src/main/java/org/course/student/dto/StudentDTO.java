package org.course.student.dto;

import java.util.Set;

public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private Long departmentId;
    private String departmentName;
    private Set<EnrollmentDTO> enrollments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Set<EnrollmentDTO> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<EnrollmentDTO> enrollments) {
        this.enrollments = enrollments;
    }
}
