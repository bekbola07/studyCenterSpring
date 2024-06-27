package org.example.studycenterspring.repo;

import org.example.studycenterspring.entity.Group;
import org.example.studycenterspring.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeTableRepo extends JpaRepository<TimeTable, Integer> {
    List<TimeTable> findAllByGroupId(Integer groupId);
}
