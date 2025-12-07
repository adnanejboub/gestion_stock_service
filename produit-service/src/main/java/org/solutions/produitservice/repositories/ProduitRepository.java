package org.solutions.produitservice.repositories;

import org.solutions.produitservice.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Optional<Produit> findByReference(String reference);
    List<Produit> findByCategorie(String categorie);
    List<Produit> findByMarque(String marque);
    List<Produit> findByFournisseurId(Long fournisseurId);
    boolean existsByReference(String reference);
    List<Produit> findByPrixBetween(Double minPrix, Double maxPrix);
}
