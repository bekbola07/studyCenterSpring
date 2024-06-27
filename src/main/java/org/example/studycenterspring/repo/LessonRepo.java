package org.example.studycenterspring.repo;

import org.example.studycenterspring.entity.Lesson;
import org.example.studycenterspring.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepo extends JpaRepository<Lesson, Integer> {
}
