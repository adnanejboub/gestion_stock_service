package org.solutions.stockservice.repositories;

import org.solutions.stockservice.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProduitId(Long produitId);

    boolean existsByProduitId(Long produitId);

    List<Stock> findByEmplacement(String emplacement);

    @Query("SELECT s FROM Stock s WHERE s.quantiteDisponible < :seuil")
    List<Stock> findStocksFaibles(Integer seuil);

    @Query("SELECT s FROM Stock s WHERE s.quantiteDisponible = 0")
    List<Stock> findStocksEpuises();
}
