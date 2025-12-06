package org.solutions.commandeservice.services;

import jakarta.transaction.Transactional;
import org.solutions.commandeservice.entities.Commande;
import org.solutions.commandeservice.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CommandeServiceImp implements CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public List<Commande> findAll() {
        return commandeRepository.findAll();
    }

    @Override
    public Commande findById(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec l'ID: " + id));
    }

    @Override
    public Commande create(Commande commande) {
        if (commande.getDateCommande() == null) {
            commande.setDateCommande(LocalDateTime.now());
        }
        return commandeRepository.save(commande);
    }

    @Override
    public Commande update(Long id, Commande commandeDetails) {
        Commande commande = findById(id);
        
        commande.setStatut(commandeDetails.getStatut());
        commande.setMontantTotal(commandeDetails.getMontantTotal());
        commande.setAdresseLivraison(commandeDetails.getAdresseLivraison());
        commande.setVilleLivraison(commandeDetails.getVilleLivraison());
        commande.setModePaiement(commandeDetails.getModePaiement());
        commande.setStatutPaiement(commandeDetails.getStatutPaiement());
        commande.setCommentaire(commandeDetails.getCommentaire());
        commande.setNumeroSuiviLivraison(commandeDetails.getNumeroSuiviLivraison());
        
        return commandeRepository.save(commande);
    }

    @Override
    public void delete(Long id) {
        Commande commande = findById(id);
        commandeRepository.delete(commande);
    }

    @Override
    public List<Commande> findByClient_Id(Long clientId) {
        return commandeRepository.findByClient_Id(clientId);
    }

    @Override
    public List<Commande> findByStatut(String statut) {
        return commandeRepository.findByStatut(statut);
    }

    @Override
    public List<Commande> findByDateCommandeBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return commandeRepository.findByDateCommandeBetween(startDate, endDate);
    }

    @Override
    public List<Commande> findByStatutPaiement(String statutPaiement) {
        return commandeRepository.findByStatutPaiement(statutPaiement);
    }

    @Override
    public Double getTotalCommandesByClientId(Long clientId) {
        List<Commande> commandes = commandeRepository.findByClient_Id(clientId);
        return commandes.stream()
                .mapToDouble(Commande::getMontantTotal)
                .sum();
    }
}
