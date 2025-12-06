package org.solutions.stockservice.web.rest;

import org.solutions.stockservice.entities.Stock;
import org.solutions.stockservice.services.StockServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockRestController {
    
    @Autowired
    private StockServiceImp stockService;

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.findAll();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        try {
            Stock stock = stockService.findById(id);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/produit/{produitId}")
    public ResponseEntity<Stock> getStockByProduitId(@PathVariable Long produitId) {
        try {
            Stock stock = stockService.findByProduit_Id(produitId);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        try {
            Stock createdStock = stockService.create(stock);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(
            @PathVariable Long id,
            @RequestBody Stock stockDetails) {
        try {
            Stock updatedStock = stockService.update(id, stockDetails);
            return ResponseEntity.ok(updatedStock);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/quantite-disponible")
    public ResponseEntity<Stock> updateQuantiteDisponible(
            @PathVariable Long id,
            @RequestBody Integer quantite) {
        try {
            Stock updatedStock = stockService.updateQuantiteDisponible(id, quantite);
            return ResponseEntity.ok(updatedStock);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/quantite-reservee")
    public ResponseEntity<Stock> updateQuantiteReservee(
            @PathVariable Long id,
            @RequestBody Integer quantite) {
        try {
            Stock updatedStock = stockService.updateQuantiteReservee(id, quantite);
            return ResponseEntity.ok(updatedStock);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        try {
            stockService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
