package org.solutions.commandeservice.services;

import org.solutions.commandeservice.entities.LigneCommande;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LigneCommandeService {
    List<LigneCommande> findAll();
    LigneCommande findById(Long id);
    LigneCommande create(LigneCommande ligneCommande);
    LigneCommande update(Long id, LigneCommande ligneCommandeDetails);
    void delete(Long id);
    List<LigneCommande> findByCommande_Id(Long commandeId);
    List<LigneCommande> findByProduit_Id(Long produitId);
}
