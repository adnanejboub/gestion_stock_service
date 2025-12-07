package org.solutions.stockservice.services;

import org.solutions.stockservice.entities.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockService {
    List<Stock> findAll();
    Stock findById(Long id);
    Stock create(Stock stock);
    Stock update(Long id, Stock stockDetails);
    void delete(Long id);
    List<Stock> findByEmplacement(String emplacement);
    List<Stock> findStocksFaibles(Integer seuil);
    List<Stock> findStocksEpuises();
    // Méthodes de gestion du stock
    Stock ajouterStock(Long produitId, Integer quantite);
    Stock retirerStock(Long produitId, Integer quantite);
    Stock reserverStock(Long produitId, Integer quantite);
    Stock libererReservation(Long produitId, Integer quantite);
    // Méthode avec Feign
    Stock getStockWithProduit(Long stockId);
    Stock findByProduitId(Long produitId);
}
