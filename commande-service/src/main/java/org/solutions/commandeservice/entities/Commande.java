package org.solutions.commandeservice.entities;

import jakarta.persistence.*;
import org.solutions.commandeservice.dtos.ClientDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateCommande;

    @Column(nullable = false)
    private String statut;

    @Column(nullable = false)
    private double montantTotal;

    @Column(length = 255)
    private String adresseLivraison;

    @Column(length = 100)
    private String villeLivraison;

    @Column
    private String modePaiement;

    @Column
    private String statutPaiement;



    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LigneCommande> lignesCommande = new ArrayList<>();

    // Champ transient pour les données du client (récupérées via Feign)
    @Transient
    private ClientDTO client;

    // Constructeurs
    public Commande() {
    }

    public Commande(Long id, LocalDateTime dateCommande, String statut, double montantTotal,
                    String adresseLivraison, String villeLivraison, String modePaiement,
                    String statutPaiement, String numeroSuiviLivraison,
                    Long clientId) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.montantTotal = montantTotal;
        this.adresseLivraison = adresseLivraison;
        this.villeLivraison = villeLivraison;
        this.modePaiement = modePaiement;
        this.statutPaiement = statutPaiement;
        this.clientId = clientId;
        this.lignesCommande = new ArrayList<>();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
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


    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public List<LigneCommande> getLignesCommande() {
        return lignesCommande;
    }

    public void setLignesCommande(List<LigneCommande> lignesCommande) {
        this.lignesCommande = lignesCommande;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }
}