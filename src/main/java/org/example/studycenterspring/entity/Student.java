package org.example.studycenterspring.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.studycenterspring.entity.base.BaseEntity;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Student extends BaseEntity {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    private List<StudTimeTable> studTimeTables;

    public String getFullName(){
        return this.firstName+" "+this.lastName;
    }

}
