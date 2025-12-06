package org.solutions.stockservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solutions.produitservice.entities.Produit;



@Entity
@Data
@NoArgsConstructor
@Table(name = "stocks")
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false, unique = true)
    private Produit produit;

    @Column(nullable = false)
    private Integer quantiteDisponible = 0;

    @Column(nullable = false)
    private Integer quantiteReservee = 0;

    @Column
    private Integer quantiteEnCommande = 0;

    @Column(length = 255)
    private String emplacement;

}