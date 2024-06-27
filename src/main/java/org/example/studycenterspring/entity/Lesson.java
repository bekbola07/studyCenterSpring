package org.example.studycenterspring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.example.studycenterspring.entity.base.BaseEntity;
import org.example.studycenterspring.entity.enums.CSEStatus;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Lesson extends BaseEntity {
    private Integer lessonOrder;
    private LocalDate lessonTime;
    private CSEStatus lessonStatus;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.EAGER)
    private List<StudentAttendance> studentAttendances;

    @ManyToOne
    @JsonBackReference
    private TimeTable timeTable;
    @Override
    public String toString() {
        return "Lesson{" +
                "lessonOrder=" + lessonOrder +
                ", lessonTime=" + lessonTime +
                ", lessonStatus=" + lessonStatus +
                '}';
    }
}