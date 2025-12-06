package org.solutions.commandeservice.services;

import jakarta.transaction.Transactional;
import org.solutions.commandeservice.entities.LigneCommande;
import org.solutions.commandeservice.repositories.LigneCommandeRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LigneCommandeServiceImp implements LigneCommandeService {

    @Autowired
    private LigneCommandeRepostory ligneCommandeRepository;

    @Override
    public List<LigneCommande> findAll() {
        return ligneCommandeRepository.findAll();
    }

    @Override
    public LigneCommande findById(Long id) {
        return ligneCommandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LigneCommande non trouvée avec l'ID: " + id));
    }

    @Override
    public LigneCommande create(LigneCommande ligneCommande) {
        // Calculer le montant de la ligne si non fourni
        if (ligneCommande.getMontantLigne() == 0) {
            double montant = ligneCommande.getQuantite() * ligneCommande.getPrixUnitaire();
            ligneCommande.setMontantLigne(montant);
        }
        return ligneCommandeRepository.save(ligneCommande);
    }

    @Override
    public LigneCommande update(Long id, LigneCommande ligneCommandeDetails) {
        LigneCommande ligneCommande = findById(id);
        
        ligneCommande.setQuantite(ligneCommandeDetails.getQuantite());
        ligneCommande.setPrixUnitaire(ligneCommandeDetails.getPrixUnitaire());
        
        // Recalculer le montant de la ligne
        double montant = ligneCommande.getQuantite() * ligneCommande.getPrixUnitaire();
        ligneCommande.setMontantLigne(montant);
        
        return ligneCommandeRepository.save(ligneCommande);
    }

    @Override
    public void delete(Long id) {
        LigneCommande ligneCommande = findById(id);
        ligneCommandeRepository.delete(ligneCommande);
    }

    @Override
    public List<LigneCommande> findByCommande_Id(Long commandeId) {
        return ligneCommandeRepository.findByCommande_Id(commandeId);
    }

    @Override
    public List<LigneCommande> findByProduit_Id(Long produitId) {
        return ligneCommandeRepository.findByProduit_Id(produitId);
    }
}
