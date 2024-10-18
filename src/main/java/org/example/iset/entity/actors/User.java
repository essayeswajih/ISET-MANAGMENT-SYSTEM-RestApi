package org.example.iset.entity.actors;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "IsetUsers")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String tel;
    private Integer cin;
    private String adresse;
    private String fax;
    private String email;
    private String password;
    private String role;
}
