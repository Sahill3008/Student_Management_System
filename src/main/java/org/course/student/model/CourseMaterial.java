package org.course.student.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course_materials")
public class CourseMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String resourceLink;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public CourseMaterial() {
    }

    public CourseMaterial(String title, String resourceLink, Course course) {
        this.title = title;
        this.resourceLink = resourceLink;
        this.course = course;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResourceLink() {
        return resourceLink;
    }

    public void setResourceLink(String resourceLink) {
        this.resourceLink = resourceLink;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
