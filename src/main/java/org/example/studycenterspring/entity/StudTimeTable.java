package org.example.studycenterspring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.studycenterspring.entity.base.BaseEntity;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class StudTimeTable extends BaseEntity {

    @ManyToOne
    private TimeTable timeTable;
    @ManyToOne
    private Student student;
    @OneToMany(mappedBy = "studTimeTable",fetch = FetchType.EAGER)
    private List<StudentAttendance> studentAttendances;


    public List<StudentAttendance> sortedAttendances(){
        return this.studentAttendances.stream()
                .sorted(Comparator.comparingInt(item->item.getLesson().getLessonOrder()))
                .collect(Collectors.toList());
    }
}
