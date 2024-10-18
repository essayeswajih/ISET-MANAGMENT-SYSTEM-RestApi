package org.example.iset.entity.actors;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.iset.entity.Course;
import org.example.iset.entity.Dep;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professor {
    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    private User user;

    @OneToMany
    private List<Course> courses;

    @OneToMany
    private List<Dep> depList;

}
