package org.example.iset.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSession {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Course course;

    private Date dateDebut;
    private Date dateFin;
}
