package org.solutions.commandeservice.repositories;

import org.solutions.commandeservice.entities.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneCommandeRepostory extends JpaRepository<LigneCommande, Long> {
    List<LigneCommande> findByCommande_Id(Long commandeId);
    List<LigneCommande> findByProduit_Id(Long produitId);
}
