package org.course.student.service;

import org.course.student.dto.DepartmentDTO;
import org.course.student.model.Department;
import org.course.student.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentRepository repo;

    public DepartmentService(DepartmentRepository repo) {
        this.repo = repo;
    }

    public Department create(DepartmentDTO dto) {
        Department d = new Department();
        d.setDeptName(dto.getDeptName());
        return repo.save(d);
    }

    public java.util.List<Department> getAll() {
        return repo.findAll();
    }

    public Department getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    public Department update(Long id, DepartmentDTO dto) {
        Department d = getById(id);
        d.setDeptName(dto.getDeptName());
        return repo.save(d);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
