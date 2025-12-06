package org.solutions.produitservice.services;

import jakarta.transaction.Transactional;
import org.solutions.produitservice.entities.Produit;
import org.solutions.produitservice.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProduitServiceImp implements ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    @Override
    public Produit findById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));
    }

    @Override
    public Produit findByReference(String reference) {
        return produitRepository.findByReference(reference)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec la référence: " + reference));
    }

    @Override
    public Produit create(Produit produit) {
        if (produitRepository.existsByReference(produit.getReference())) {
            throw new RuntimeException("Un produit avec cette référence existe déjà: " + produit.getReference());
        }
        return produitRepository.save(produit);
    }

    @Override
    public Produit update(Long id, Produit produitDetails) {
        Produit produit = findById(id);
        
        if (!produit.getReference().equals(produitDetails.getReference()) &&
                produitRepository.existsByReference(produitDetails.getReference())) {
            throw new RuntimeException("Cette référence est déjà utilisée par un autre produit");
        }
        
        produit.setReference(produitDetails.getReference());
        produit.setNom(produitDetails.getNom());
        produit.setPrix(produitDetails.getPrix());
        produit.setCategorie(produitDetails.getCategorie());
        produit.setMarque(produitDetails.getMarque());
        produit.setStockMinimum(produitDetails.getStockMinimum());
        produit.setFournisseur(produitDetails.getFournisseur());
        
        return produitRepository.save(produit);
    }

    @Override
    public void delete(Long id) {
        Produit produit = findById(id);
        produitRepository.delete(produit);
    }

    @Override
    public List<Produit> findByCategorie(String categorie) {
        return produitRepository.findByCategorie(categorie);
    }

    @Override
    public List<Produit> findByMarque(String marque) {
        return produitRepository.findByMarque(marque);
    }

    @Override
    public List<Produit> findByFournisseur_Id(Long fournisseurId) {
        return produitRepository.findByFournisseur_Id(fournisseurId);
    }
}
