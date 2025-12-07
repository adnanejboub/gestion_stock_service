package org.solutions.stockservice.entities;

import jakarta.persistence.*;
import org.solutions.stockservice.dtos.ProduitDTO;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID du produit (pas de relation JPA)
    @Column(name = "produit_id", nullable = false, unique = true)
    private Long produitId;

    @Column(nullable = false)
    private Integer quantiteDisponible = 0;

    @Column(nullable = false)
    private Integer quantiteReservee = 0;

    @Column
    private Integer quantiteEnCommande = 0;

    @Column(length = 255)
    private String emplacement;

    // Données du produit (récupérées via Feign)
    @Transient
    private ProduitDTO produit;

    // Constructeurs
    public Stock() {
        this.quantiteDisponible = 0;
        this.quantiteReservee = 0;
        this.quantiteEnCommande = 0;
    }

    public Stock(Long id, Long produitId, Integer quantiteDisponible, Integer quantiteReservee,
                 Integer quantiteEnCommande, String emplacement) {
        this.id = id;
        this.produitId = produitId;
        this.quantiteDisponible = quantiteDisponible;
        this.quantiteReservee = quantiteReservee;
        this.quantiteEnCommande = quantiteEnCommande;
        this.emplacement = emplacement;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public Integer getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public void setQuantiteDisponible(Integer quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }

    public Integer getQuantiteReservee() {
        return quantiteReservee;
    }

    public void setQuantiteReservee(Integer quantiteReservee) {
        this.quantiteReservee = quantiteReservee;
    }

    public Integer getQuantiteEnCommande() {
        return quantiteEnCommande;
    }

    public void setQuantiteEnCommande(Integer quantiteEnCommande) {
        this.quantiteEnCommande = quantiteEnCommande;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }
}