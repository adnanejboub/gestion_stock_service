package org.solutions.fournisseurservice.entities;

import jakarta.persistence.*;
import org.solutions.fournisseurservice.dtos.ProduitDTO;

import java.util.List;

@Entity
@Table(name = "fournisseurs")
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

    // Liste des produits (récupérée via Feign, pas stockée en BDD)
    @Transient
    private List<ProduitDTO> produits;

    @Transient
    private Integer nombreProduits;

    // Constructeurs
    public Fournisseur() {
    }

    public Fournisseur(Long id, String nomEntreprise, String nomContact, String prenomContact,
                       String email, String telephone, String adresse, String ville, String pays) {
        this.id = id;
        this.nomEntreprise = nomEntreprise;
        this.nomContact = nomContact;
        this.prenomContact = prenomContact;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.ville = ville;
        this.pays = pays;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getNomContact() {
        return nomContact;
    }

    public void setNomContact(String nomContact) {
        this.nomContact = nomContact;
    }

    public String getPrenomContact() {
        return prenomContact;
    }

    public void setPrenomContact(String prenomContact) {
        this.prenomContact = prenomContact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public List<ProduitDTO> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitDTO> produits) {
        this.produits = produits;
    }

    public Integer getNombreProduits() {
        return nombreProduits;
    }

    public void setNombreProduits(Integer nombreProduits) {
        this.nombreProduits = nombreProduits;
    }
}