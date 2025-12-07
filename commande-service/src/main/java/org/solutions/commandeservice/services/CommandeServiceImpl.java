package org.solutions.commandeservice.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solutions.commandeservice.entities.Commande;
import org.solutions.commandeservice.entities.LigneCommande;
import org.solutions.commandeservice.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CommandeServiceImpl implements CommandeService {

    private static final Logger log = LoggerFactory.getLogger(CommandeServiceImpl.class);

    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public List<Commande> findAll() {
        log.info("Récupération de toutes les commandes");
        return commandeRepository.findAll();
    }

    @Override
    public Commande findById(Long id) {
        log.info("Recherche de la commande avec l'ID: {}", id);
        return commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID: " + id));
    }

    @Override
    public List<Commande> findByClientId(Long clientId) {
        log.info("Recherche des commandes du client: {}", clientId);
        return commandeRepository.findByClientId(clientId);
    }

    @Override
    public Commande create(Commande commande) {
        if (commande.getDateCommande() == null) {
            commande.setDateCommande(LocalDateTime.now());
        }

        if (commande.getLignesCommande() != null && !commande.getLignesCommande().isEmpty()) {
            for (LigneCommande ligne : commande.getLignesCommande()) {
                ligne.setCommande(commande);
            }
        }

        return commandeRepository.save(commande);
    }

    @Override
    public Commande update(Long id, Commande commandeDetails) {
        log.info("Mise à jour de la commande avec l'ID: {}", id);

        Commande commande = findById(id);
        commande.setDateCommande(commandeDetails.getDateCommande());
        commande.setStatut(commandeDetails.getStatut());
        commande.setMontantTotal(commandeDetails.getMontantTotal());
        commande.setAdresseLivraison(commandeDetails.getAdresseLivraison());
        commande.setVilleLivraison(commandeDetails.getVilleLivraison());
        commande.setModePaiement(commandeDetails.getModePaiement());
        commande.setStatutPaiement(commandeDetails.getStatutPaiement());

        Commande updatedCommande = commandeRepository.save(commande);
        log.info("Commande mise à jour avec succès - ID: {}", updatedCommande.getId());
        return updatedCommande;
    }

    @Override
    public void delete(Long id) {
        log.info("Suppression de la commande avec l'ID: {}", id);
        Commande commande = findById(id);
        commandeRepository.delete(commande);
        log.info("Commande supprimée avec succès - ID: {}", id);
    }

    @Override
    public Double calculateTotalByClientId(Long clientId) {
        log.info("Calcul du total des commandes pour le client: {}", clientId);
        Double total = commandeRepository.sumMontantTotalByClientId(clientId);
        return total != null ? total : 0.0;
    }

    @Override
    public Integer countByClientId(Long clientId) {
        log.info("Comptage des commandes pour le client: {}", clientId);
        return commandeRepository.countByClientId(clientId);
    }

    @Override
    public List<Commande> findByStatut(String statut) {
        log.info("Recherche des commandes par statut: {}", statut);
        return commandeRepository.findByStatut(statut);
    }

    @Override
    public List<Commande> findByStatutPaiement(String statutPaiement) {
        log.info("Recherche des commandes par statut de paiement: {}", statutPaiement);
        return commandeRepository.findByStatutPaiement(statutPaiement);
    }

    @Override
    public List<Commande> findByDateCommandeBetween(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Recherche des commandes entre {} et {}", startDate, endDate);
        return commandeRepository.findByDateCommandeBetween(startDate, endDate);
    }


}