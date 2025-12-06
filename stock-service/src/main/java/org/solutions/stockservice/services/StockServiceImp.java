package org.solutions.stockservice.services;

import jakarta.transaction.Transactional;
import org.solutions.stockservice.entities.Stock;
import org.solutions.stockservice.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StockServiceImp implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock findById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock non trouvé avec l'ID: " + id));
    }

    @Override
    public Stock findByProduit_Id(Long produitId) {
        return stockRepository.findByProduit_Id(produitId)
                .orElseThrow(() -> new RuntimeException("Stock non trouvé pour le produit avec l'ID: " + produitId));
    }

    @Override
    public Stock create(Stock stock) {
        if (stockRepository.existsByProduit_Id(stock.getProduit().getId())) {
            throw new RuntimeException("Un stock existe déjà pour ce produit");
        }
        return stockRepository.save(stock);
    }

    @Override
    public Stock update(Long id, Stock stockDetails) {
        Stock stock = findById(id);
        
        stock.setQuantiteDisponible(stockDetails.getQuantiteDisponible());
        stock.setQuantiteReservee(stockDetails.getQuantiteReservee());
        stock.setQuantiteEnCommande(stockDetails.getQuantiteEnCommande());
        stock.setEmplacement(stockDetails.getEmplacement());
        
        return stockRepository.save(stock);
    }

    @Override
    public void delete(Long id) {
        Stock stock = findById(id);
        stockRepository.delete(stock);
    }

    @Override
    public Stock updateQuantiteDisponible(Long id, Integer quantite) {
        Stock stock = findById(id);
        stock.setQuantiteDisponible(quantite);
        return stockRepository.save(stock);
    }

    @Override
    public Stock updateQuantiteReservee(Long id, Integer quantite) {
        Stock stock = findById(id);
        stock.setQuantiteReservee(quantite);
        return stockRepository.save(stock);
    }
}
