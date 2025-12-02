package org.course.student.service;

import org.course.student.dto.StudentProfileDTO;
import org.course.student.model.Address;
import org.course.student.model.StudentProfile;
import org.course.student.repository.StudentProfileRepository;
import org.springframework.stereotype.Service;

import org.course.student.model.Student;
import org.course.student.repository.StudentRepository;

@Service
public class StudentProfileService {

    private final StudentProfileRepository repo;
    private final StudentRepository studentRepo;

    public StudentProfileService(StudentProfileRepository repo, StudentRepository studentRepo) {
        this.repo = repo;
        this.studentRepo = studentRepo;
    }

    public StudentProfile create(StudentProfileDTO dto) {

        Address address = new Address(
                dto.getStreet(),
                dto.getCity(),
                dto.getState(),
                dto.getZip()
        );

        StudentProfile profile = new StudentProfile();
        profile.setBio(dto.getBio());
        profile.setAddress(address);
        
        if (dto.getStudentId() != null) {
            Student student = studentRepo.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            profile.setStudent(student);
        }

        return repo.save(profile);
    }

    public java.util.List<StudentProfile> getAll() {
        return repo.findAll();
    }

    public StudentProfile getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("StudentProfile not found"));
    }

    public StudentProfile update(Long id, StudentProfileDTO dto) {
        StudentProfile profile = getById(id);
        profile.setBio(dto.getBio());

        if (dto.getStreet() != null) {
            Address address = new Address(
                    dto.getStreet(),
                    dto.getCity(),
                    dto.getState(),
                    dto.getZip()
            );
            profile.setAddress(address);
        }
        
        if (dto.getStudentId() != null) {
            Student student = studentRepo.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            profile.setStudent(student);
        }

        return repo.save(profile);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
