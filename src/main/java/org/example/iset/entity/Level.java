package org.example.iset.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Level {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany
    private List<Class> classes;

    @OneToMany
    private List<Course> firstSemCourses;

    @OneToMany
    private List<Course> secSemCourses;
}
