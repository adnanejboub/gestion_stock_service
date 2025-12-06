package org.solutions.fournisseurservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solutions.produitservice.entities.Produit;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "fournisseurs")
@NoArgsConstructor
@AllArgsConstructor
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String nomEntreprise;

    @Column(nullable = false, length = 100)
    private String nomContact;

    @Column(nullable = false, length = 100)
    private String prenomContact;

    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Column(nullable = false, length = 20)
    private String telephone;


    @Column(length = 255)
    private String adresse;

    @Column(length = 100)
    private String ville;

    @Column(length = 100)
    private String pays;

    @OneToMany(mappedBy = "fournisseur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produit> produits;


}
