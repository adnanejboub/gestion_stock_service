package org.solutions.commandeservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.solutions.commandeservice.dtos.ProduitDTO;


@Entity
@Table(name = "lignecommandes")

public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantite;

    @Column(nullable = false)
    private double prixUnitaire;

    @Column
    private double montantLigne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    @JsonIgnore
    private Commande commande;

    @Column(name = "produit_id", nullable = false)
    private Long produitId;

    @Transient
    private ProduitDTO produit;

    // Constructeurs
    public LigneCommande() {
    }

    public LigneCommande(Integer quantite, double prixUnitaire, double montantLigne, Long produitId) {
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.montantLigne = montantLigne;
        this.produitId = produitId;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getMontantLigne() {
        return montantLigne;
    }

    public void setMontantLigne(double montantLigne) {
        this.montantLigne = montantLigne;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }
}
