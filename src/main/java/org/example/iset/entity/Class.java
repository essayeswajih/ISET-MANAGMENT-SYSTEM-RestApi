package org.example.iset.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.iset.entity.actors.Student;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @OneToMany
    private List<Student> etudiants;

    @OneToMany
    private List<Course> courses;

}
