package org.example.studycenterspring.repo;

import org.example.studycenterspring.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group, Integer> {
}
