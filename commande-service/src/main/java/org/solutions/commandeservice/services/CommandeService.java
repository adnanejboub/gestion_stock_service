package org.solutions.commandeservice.services;

import org.solutions.commandeservice.entities.Commande;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface CommandeService {

    List<Commande> findAll();

    Commande findById(Long id);

    List<Commande> findByClientId(Long clientId);

    Commande create(Commande commande);

    Commande update(Long id, Commande commandeDetails);

    void delete(Long id);

    Double calculateTotalByClientId(Long clientId);

    Integer countByClientId(Long clientId);

    List<Commande> findByStatut(String statut);

    List<Commande> findByStatutPaiement(String statutPaiement);

    List<Commande> findByDateCommandeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
