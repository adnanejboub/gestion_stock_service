package org.solutions.stockservice.services;

import org.solutions.stockservice.entities.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StockService {
    List<Stock> findAll();
    Stock findById(Long id);
    Stock findByProduit_Id(Long produitId);
    Stock create(Stock stock);
    Stock update(Long id, Stock stockDetails);
    void delete(Long id);
    Stock updateQuantiteDisponible(Long id, Integer quantite);
    Stock updateQuantiteReservee(Long id, Integer quantite);
}
