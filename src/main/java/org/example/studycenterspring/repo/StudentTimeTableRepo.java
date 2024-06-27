package org.example.studycenterspring.repo;

import org.example.studycenterspring.entity.Group;
import org.example.studycenterspring.entity.StudTimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentTimeTableRepo extends JpaRepository<StudTimeTable, Integer> {
    List<StudTimeTable>findAllByTimeTableId(Integer timetableId);

}
