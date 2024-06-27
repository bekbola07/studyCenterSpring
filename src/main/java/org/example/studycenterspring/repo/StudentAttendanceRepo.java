package org.example.studycenterspring.repo;

import org.example.studycenterspring.entity.Group;
import org.example.studycenterspring.entity.StudentAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAttendanceRepo extends JpaRepository<StudentAttendance, Integer> {
}
