package org.solutions.clientservice.dtos;

import java.time.LocalDateTime;

public class CommandeDTO {

    private Long id;
    private Long clientId;
    private LocalDateTime dateCommande;
    private Double montantTotal;
    private String statut;
    private String adresseLivraison;
    private String villeLivraison;
    private String modePaiement;
    private String statutPaiement;

    public CommandeDTO() {
    }

    public CommandeDTO(Long id, Long clientId, LocalDateTime dateCommande, Double montantTotal,
                       String statut, String adresseLivraison, String villeLivraison,
                       String modePaiement, String statutPaiement) {
        this.id = id;
        this.clientId = clientId;
        this.dateCommande = dateCommande;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.adresseLivraison = adresseLivraison;
        this.villeLivraison = villeLivraison;
        this.modePaiement = modePaiement;
        this.statutPaiement = statutPaiement;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public String getVilleLivraison() {
        return villeLivraison;
    }

    public void setVilleLivraison(String villeLivraison) {
        this.villeLivraison = villeLivraison;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(String statutPaiement) {
        this.statutPaiement = statutPaiement;
    }
}
