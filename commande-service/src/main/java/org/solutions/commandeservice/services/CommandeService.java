package org.solutions.commandeservice.services;

import org.solutions.commandeservice.entities.Commande;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface CommandeService {
    List<Commande> findAll();
    Commande findById(Long id);
    Commande create(Commande commande);
    Commande update(Long id, Commande commandeDetails);
    void delete(Long id);
    List<Commande> findByClient_Id(Long clientId);
    List<Commande> findByStatut(String statut);
    List<Commande> findByDateCommandeBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Commande> findByStatutPaiement(String statutPaiement);
    Double getTotalCommandesByClientId(Long clientId);
}
