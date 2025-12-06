package org.solutions.produitservice.services;

import org.solutions.produitservice.entities.Produit;
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
    List<Produit> findByCategorie(String categorie);
    List<Produit> findByMarque(String marque);
    List<Produit> findByFournisseur_Id(Long fournisseurId);
}
