package org.solutions.produitservice.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solutions.produitservice.dtos.FournisseurDTO;
import org.solutions.produitservice.dtos.LigneCommandeDTO;
import org.solutions.produitservice.dtos.StockDTO;
import org.solutions.produitservice.entities.Produit;
import org.solutions.produitservice.feign.CommandeFeignClient;
import org.solutions.produitservice.feign.FournisseurFeignClient;
import org.solutions.produitservice.feign.StockFeignClient;
import org.solutions.produitservice.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProduitServiceImpl implements ProduitService {

    private static final Logger log = LoggerFactory.getLogger(ProduitServiceImpl.class);

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private FournisseurFeignClient fournisseurFeignClient;

    @Autowired
    private StockFeignClient stockFeignClient;

    @Autowired
    private CommandeFeignClient commandeFeignClient;

    @Override
    public List<Produit> findAll() {
        log.info("Récupération de tous les produits");
        return produitRepository.findAll();
    }

    @Override
    public Produit findById(Long id) {
        log.info("Recherche du produit avec l'ID: {}", id);
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID: " + id));
    }

    @Override
    public Produit findByReference(String reference) {
        log.info("Recherche du produit avec la référence: {}", reference);
        return produitRepository.findByReference(reference)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec la référence: " + reference));
    }

    @Override
    public Produit create(Produit produit) {
        log.info("Création d'un nouveau produit avec la référence: {}", produit.getReference());

        if (produitRepository.existsByReference(produit.getReference())) {
            throw new RuntimeException("Un produit avec cette référence existe déjà: " + produit.getReference());
        }

        Produit savedProduit = produitRepository.save(produit);
        log.info("Produit créé avec succès - ID: {}", savedProduit.getId());
        return savedProduit;
    }

    @Override
    public Produit update(Long id, Produit produitDetails) {
        log.info("Mise à jour du produit avec l'ID: {}", id);

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
        produit.setFournisseurId(produitDetails.getFournisseurId());

        Produit updatedProduit = produitRepository.save(produit);
        log.info("Produit mis à jour avec succès - ID: {}", updatedProduit.getId());
        return updatedProduit;
    }

    @Override
    public void delete(Long id) {
        log.info("Suppression du produit avec l'ID: {}", id);
        Produit produit = findById(id);
        produitRepository.delete(produit);
        log.info("Produit supprimé avec succès - ID: {}", id);
    }

    @Override
    public List<Produit> findByFournisseurId(Long fournisseurId) {
        log.info("Recherche des produits du fournisseur: {}", fournisseurId);
        return produitRepository.findByFournisseurId(fournisseurId);
    }

    @Override
    public List<Produit> findByCategorie(String categorie) {
        log.info("Recherche des produits de la catégorie: {}", categorie);
        return produitRepository.findByCategorie(categorie);
    }

    @Override
    public List<Produit> findByMarque(String marque) {
        log.info("Recherche des produits de la marque: {}", marque);
        return produitRepository.findByMarque(marque);
    }

    @Override
    public List<Produit> findByPrixBetween(Double minPrix, Double maxPrix) {
        log.info("Recherche des produits entre {} et {} MAD", minPrix, maxPrix);
        return produitRepository.findByPrixBetween(minPrix, maxPrix);
    }

    // ========== Méthodes avec appels Feign ==========

    @Override
    public Produit getProduitWithDetails(Long produitId) {
        log.info("Récupération du produit {} avec tous les détails", produitId);

        Produit produit = findById(produitId);

        // Récupérer le fournisseur
        try {
            FournisseurDTO fournisseur = fournisseurFeignClient.getFournisseurById(produit.getFournisseurId());
            produit.setFournisseur(fournisseur);
            log.info("Fournisseur récupéré: {}", fournisseur.getNomEntreprise());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du fournisseur {}", produit.getFournisseurId(), e);
        }

        // Récupérer le stock
        try {
            StockDTO stock = stockFeignClient.getStockByProduitId(produitId);
            produit.setStock(stock);
            log.info("Stock récupéré - Quantité disponible: {}", stock.getQuantiteDisponible());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du stock du produit {}", produitId, e);
        }

        return produit;
    }

    @Override
    public List<LigneCommandeDTO> getLignesCommandeByProduit(Long produitId) {
        log.info("Récupération des lignes de commande pour le produit {}", produitId);

        // Vérifier que le produit existe
        findById(produitId);

        try {
            List<LigneCommandeDTO> lignesCommande = commandeFeignClient.getLignesCommandeByProduitId(produitId);
            log.info("Nombre de lignes de commande récupérées: {}", lignesCommande.size());
            return lignesCommande;

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des lignes de commande du produit {}", produitId, e);
            throw new RuntimeException("Impossible de récupérer les lignes de commande. Service Commande indisponible.", e);
        }
    }

    @Override
    public Integer countCommandesByProduit(Long produitId) {
        log.info("Comptage des commandes contenant le produit {}", produitId);

        // Vérifier que le produit existe
        findById(produitId);

        try {
            Integer count = commandeFeignClient.countLignesCommandeByProduitId(produitId);
            log.info("Nombre de commandes contenant le produit {}: {}", produitId, count);
            return count;

        } catch (Exception e) {
            log.error("Erreur lors du comptage des commandes du produit {}", produitId, e);
            return 0;
        }
    }
}