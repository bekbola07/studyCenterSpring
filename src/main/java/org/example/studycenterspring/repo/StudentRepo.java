package org.example.studycenterspring.repo;

import org.example.studycenterspring.entity.Group;
import org.example.studycenterspring.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Integer> {
}
