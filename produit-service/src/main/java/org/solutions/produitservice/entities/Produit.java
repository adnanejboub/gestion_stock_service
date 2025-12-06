package org.solutions.produitservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solutions.commandeservice.entities.LigneCommande;
import org.solutions.fournisseurservice.entities.Fournisseur;
import org.solutions.stockservice.entities.Stock;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "produits")
@AllArgsConstructor
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String reference;

    @Column(nullable = false, length = 200)
    private String nom;

    @Column(precision = 10, scale = 2)
    private double prix;

    @Column(length = 100)
    private String categorie;

    @Column(length = 50)
    private String marque;

    @Column(nullable = false)
    private Integer stockMinimum;

    @OneToOne(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Stock stock;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommande> lignesCommande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fournisseur_id", nullable = false)
    private Fournisseur fournisseur;
}
