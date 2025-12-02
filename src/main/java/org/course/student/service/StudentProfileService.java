package org.course.student.service;

import org.course.student.dto.StudentProfileDTO;
import org.course.student.model.Address;
import org.course.student.model.StudentProfile;
import org.course.student.repository.StudentProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentProfileService {

    private final StudentProfileRepository repo;

    public StudentProfileService(StudentProfileRepository repo) {
        this.repo = repo;
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

        return repo.save(profile);
    }
}
