package org.solutions.stockservice.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solutions.stockservice.dtos.ProduitDTO;
import org.solutions.stockservice.entities.Stock;
import org.solutions.stockservice.feign.ProduitFeignClient;
import org.solutions.stockservice.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProduitFeignClient produitFeignClient;

    @Override
    public List<Stock> findAll() {
        log.info("Récupération de tous les stocks");
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(Long id) {
        log.info("Recherche du stock avec l'ID: {}", id);
        return stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock non trouvé avec l'ID: " + id));
    }

    @Override
    public Stock findByProduitId(Long produitId) {
        log.info("Recherche du stock du produit: {}", produitId);
        return stockRepository.findByProduitId(produitId)
                .orElseThrow(() -> new RuntimeException("Stock non trouvé pour le produit: " + produitId));
    }

    @Override
    public Stock create(Stock stock) {
        log.info("Création d'un nouveau stock pour le produit: {}", stock.getProduitId());

        if (stockRepository.existsByProduitId(stock.getProduitId())) {
            throw new RuntimeException("Un stock existe déjà pour ce produit: " + stock.getProduitId());
        }

        Stock savedStock = stockRepository.save(stock);
        log.info("Stock créé avec succès - ID: {}", savedStock.getId());
        return savedStock;
    }

    @Override
    public Stock update(Long id, Stock stockDetails) {
        log.info("Mise à jour du stock avec l'ID: {}", id);

        Stock stock = findById(id);
        stock.setQuantiteDisponible(stockDetails.getQuantiteDisponible());
        stock.setQuantiteReservee(stockDetails.getQuantiteReservee());
        stock.setQuantiteEnCommande(stockDetails.getQuantiteEnCommande());
        stock.setEmplacement(stockDetails.getEmplacement());

        Stock updatedStock = stockRepository.save(stock);
        log.info("Stock mis à jour avec succès - ID: {}", updatedStock.getId());
        return updatedStock;
    }

    @Override
    public void delete(Long id) {
        log.info("Suppression du stock avec l'ID: {}", id);
        Stock stock = findById(id);
        stockRepository.delete(stock);
        log.info("Stock supprimé avec succès - ID: {}", id);
    }

    @Override
    public List<Stock> findByEmplacement(String emplacement) {
        log.info("Recherche des stocks dans l'emplacement: {}", emplacement);
        return stockRepository.findByEmplacement(emplacement);
    }

    @Override
    public List<Stock> findStocksFaibles(Integer seuil) {
        log.info("Recherche des stocks faibles (seuil: {})", seuil);
        return stockRepository.findStocksFaibles(seuil);
    }

    @Override
    public List<Stock> findStocksEpuises() {
        log.info("Recherche des stocks épuisés");
        return stockRepository.findStocksEpuises();
    }

    // ========== Méthodes de gestion du stock ==========

    @Override
    public Stock ajouterStock(Long produitId, Integer quantite) {
        log.info("Ajout de {} unités au stock du produit {}", quantite, produitId);

        Stock stock = findByProduitId(produitId);
        stock.setQuantiteDisponible(stock.getQuantiteDisponible() + quantite);

        Stock updatedStock = stockRepository.save(stock);
        log.info("Stock mis à jour - Nouvelle quantité: {}", updatedStock.getQuantiteDisponible());
        return updatedStock;
    }

    @Override
    public Stock retirerStock(Long produitId, Integer quantite) {
        log.info("Retrait de {} unités du stock du produit {}", quantite, produitId);

        Stock stock = findByProduitId(produitId);

        if (stock.getQuantiteDisponible() < quantite) {
            throw new RuntimeException("Stock insuffisant. Disponible: " + stock.getQuantiteDisponible() + ", Demandé: " + quantite);
        }

        stock.setQuantiteDisponible(stock.getQuantiteDisponible() - quantite);

        Stock updatedStock = stockRepository.save(stock);
        log.info("Stock mis à jour - Nouvelle quantité: {}", updatedStock.getQuantiteDisponible());
        return updatedStock;
    }

    @Override
    public Stock reserverStock(Long produitId, Integer quantite) {
        log.info("Réservation de {} unités du stock du produit {}", quantite, produitId);

        Stock stock = findByProduitId(produitId);

        if (stock.getQuantiteDisponible() < quantite) {
            throw new RuntimeException("Stock insuffisant pour réservation. Disponible: " + stock.getQuantiteDisponible());
        }

        stock.setQuantiteDisponible(stock.getQuantiteDisponible() - quantite);
        stock.setQuantiteReservee(stock.getQuantiteReservee() + quantite);

        Stock updatedStock = stockRepository.save(stock);
        log.info("Stock réservé - Disponible: {}, Réservé: {}",
                updatedStock.getQuantiteDisponible(), updatedStock.getQuantiteReservee());
        return updatedStock;
    }

    @Override
    public Stock libererReservation(Long produitId, Integer quantite) {
        log.info("Libération de {} unités réservées du produit {}", quantite, produitId);

        Stock stock = findByProduitId(produitId);

        if (stock.getQuantiteReservee() < quantite) {
            throw new RuntimeException("Quantité réservée insuffisante. Réservé: " + stock.getQuantiteReservee());
        }

        stock.setQuantiteReservee(stock.getQuantiteReservee() - quantite);
        stock.setQuantiteDisponible(stock.getQuantiteDisponible() + quantite);

        Stock updatedStock = stockRepository.save(stock);
        log.info("Réservation libérée - Disponible: {}, Réservé: {}",
                updatedStock.getQuantiteDisponible(), updatedStock.getQuantiteReservee());
        return updatedStock;
    }

    // ========== Méthode avec Feign ==========

    @Override
    public Stock getStockWithProduit(Long stockId) {
        log.info("Récupération du stock {} avec les informations produit", stockId);

        Stock stock = findById(stockId);

        try {
            ProduitDTO produit = produitFeignClient.getProduitById(stock.getProduitId());
            stock.setProduit(produit);
            log.info("Produit récupéré: {}", produit.getNom());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du produit {}", stock.getProduitId(), e);
        }

        return stock;
    }
}