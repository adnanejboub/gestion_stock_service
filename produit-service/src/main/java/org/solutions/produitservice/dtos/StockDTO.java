package org.solutions.produitservice.dtos;

public class StockDTO {

    private Long id;
    private Long produitId;
    private Integer quantiteDisponible;
    private Integer quantiteReservee;
    private Integer quantiteEnCommande;
    private String emplacement;

    public StockDTO() {
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
}


