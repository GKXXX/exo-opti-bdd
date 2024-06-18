package com.exo2.Exercice2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "adresse",indexes = {@Index(name = "index_id_adresse",columnList = "adresse_id"),@Index(name = "index_ville_adresse",columnList = "ville")})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adresse_id")
    private Long id;

    private String ville;
    private String complementAdresse;
    private int codePostal;

    @OneToOne(mappedBy = "adresse")
    private Ecole ecole;
}
