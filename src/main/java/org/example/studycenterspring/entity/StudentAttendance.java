package org.example.studycenterspring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.studycenterspring.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class StudentAttendance  extends BaseEntity {
    @ManyToOne
    private Lesson lesson;
    private boolean hasInLesson;
    @ManyToOne
    private StudTimeTable studTimeTable;
}
