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
@Table(name = "groups")
public class Group extends BaseEntity {
    private String name;

}
