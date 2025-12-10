package org.solutions.commandeservice.repositories;

import org.solutions.commandeservice.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByStatut(String statut);
    List<Commande> findByDateCommandeBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Commande> findByStatutPaiement(String statutPaiement);
    List<Commande> findByClientId(Long clientId);
    @Query("SELECT SUM(c.montantTotal) FROM Commande c WHERE c.clientId = :clientId")
    Double sumMontantTotalByClientId(Long clientId);
    Integer countByClientId(Long clientId);
}
