package org.solutions.produitservice.services;

import org.solutions.produitservice.dtos.LigneCommandeDTO;
import org.solutions.produitservice.entities.Produit;
import org.solutions.produitservice.dtos.StockDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProduitService {
    List<Produit> findAll();

    Produit findById(Long id);

    Produit findByReference(String reference);

    Produit create(Produit produit);

    Produit update(Long id, Produit produitDetails);

    void delete(Long id);
    List<Produit> findByFournisseurId(Long fournisseurId);
    List<Produit> findByCategorie(String categorie);
    List<Produit> findByMarque(String marque);
    List<Produit> findByPrixBetween(Double minPrix, Double maxPrix);
    Produit getProduitWithDetails(Long produitId);
    List<LigneCommandeDTO> getLignesCommandeByProduit(Long produitId);
    Integer countCommandesByProduit(Long produitId);
    StockDTO getStockByProduitId(Long produitId);
}
