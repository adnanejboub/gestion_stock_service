package org.solutions.commandeservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solutions.clientservice.entities.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateCommande;

    @Column(nullable = false)
    private String statut;


    @Column(precision = 10, scale = 2)
    private double montantTotal ;


    @Column(length = 255)
    private String adresseLivraison;

    @Column(length = 100)
    private String villeLivraison;

    @Column
    private String modePaiement;

    @Column
    private String statutPaiement ;

    @Column(length = 1000)
    private String commentaire;

    @Column(length = 100)
    private String numeroSuiviLivraison;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LigneCommande> lignesCommande = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

}
