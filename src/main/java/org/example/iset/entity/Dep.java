package org.example.iset.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.iset.entity.actors.DepHead;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dep {
    @Id
    @GeneratedValue
    private Integer id;

    private int name;

    @OneToOne
    private DepHead depHead;

    @OneToMany
    private List<Level> levels;

}
