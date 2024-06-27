package org.example.studycenterspring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.studycenterspring.entity.base.BaseEntity;
import org.example.studycenterspring.entity.enums.CSEStatus;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TimeTable extends BaseEntity {
    private String name;
    private Integer price;
    private CSEStatus timeTableStatus;
    private boolean isStarted;

    @OneToMany(mappedBy = "timeTable", fetch = FetchType.EAGER)
    private List<Lesson> lessons;

    @ManyToOne
    private Group group;

    public List<Lesson> getLessons() {
        return lessons.stream()
                .sorted(Comparator.comparingInt(Lesson::getLessonOrder))
                .collect(Collectors.toList());
    }
    @Override
    public String toString() {
        return "TimeTable{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
