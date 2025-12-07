package org.solutions.stockservice.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solutions.stockservice.entities.Stock;
import org.solutions.stockservice.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockRestController {

    private static final Logger log = LoggerFactory.getLogger(StockRestController.class);

    @Autowired
    private StockService stockService;


    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        log.info("GET /api/stocks - Récupération de tous les stocks");
        List<Stock> stocks = stockService.findAll();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        log.info("GET /api/stocks/{} - Récupération du stock", id);
        try {
            Stock stock = stockService.findById(id);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            log.error("Stock non trouvé avec l'ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}/with-produit")
    public ResponseEntity<Stock> getStockWithProduit(@PathVariable Long id) {
        log.info("GET /api/stocks/{}/with-produit - Récupération avec produit", id);
        try {
            Stock stock = stockService.getStockWithProduit(id);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            log.error("Stock non trouvé avec l'ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/produit/{produitId}")
    public ResponseEntity<Stock> getStockByProduitId(@PathVariable Long produitId) {
        log.info("GET /api/stocks/produit/{} - Récupération du stock", produitId);
        try {
            Stock stock = stockService.findByProduitId(produitId);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            log.error("Stock non trouvé pour le produit: {}", produitId);
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/emplacement/{emplacement}")
    public ResponseEntity<List<Stock>> getStocksByEmplacement(@PathVariable String emplacement) {
        log.info("GET /api/stocks/emplacement/{} - Récupération des stocks", emplacement);
        List<Stock> stocks = stockService.findByEmplacement(emplacement);
        return ResponseEntity.ok(stocks);
    }


    @GetMapping("/faibles")
    public ResponseEntity<List<Stock>> getStocksFaibles(@RequestParam(defaultValue = "10") Integer seuil) {
        log.info("GET /api/stocks/faibles?seuil={} - Récupération des stocks faibles", seuil);
        List<Stock> stocks = stockService.findStocksFaibles(seuil);
        return ResponseEntity.ok(stocks);
    }


    @GetMapping("/epuises")
    public ResponseEntity<List<Stock>> getStocksEpuises() {
        log.info("GET /api/stocks/epuises - Récupération des stocks épuisés");
        List<Stock> stocks = stockService.findStocksEpuises();
        return ResponseEntity.ok(stocks);
    }


    @PostMapping("/{produitId}/ajouter")
    public ResponseEntity<Stock> ajouterStock(
            @PathVariable Long produitId,
            @RequestParam Integer quantite) {
        log.info("POST /api/stocks/{}/ajouter?quantite={} - Ajout de stock", produitId, quantite);
        try {
            Stock stock = stockService.ajouterStock(produitId, quantite);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            log.error("Erreur lors de l'ajout de stock: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/{produitId}/retirer")
    public ResponseEntity<Stock> retirerStock(
            @PathVariable Long produitId,
            @RequestParam Integer quantite) {
        log.info("POST /api/stocks/{}/retirer?quantite={} - Retrait de stock", produitId, quantite);
        try {
            Stock stock = stockService.retirerStock(produitId, quantite);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            log.error("Erreur lors du retrait de stock: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/{produitId}/reserver")
    public ResponseEntity<Stock> reserverStock(
            @PathVariable Long produitId,
            @RequestParam Integer quantite) {
        log.info("POST /api/stocks/{}/reserver?quantite={} - Réservation de stock", produitId, quantite);
        try {
            Stock stock = stockService.reserverStock(produitId, quantite);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            log.error("Erreur lors de la réservation de stock: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{produitId}/liberer")
    public ResponseEntity<Stock> libererReservation(
            @PathVariable Long produitId,
            @RequestParam Integer quantite) {
        log.info("POST /api/stocks/{}/liberer?quantite={} - Libération de réservation", produitId, quantite);
        try {
            Stock stock = stockService.libererReservation(produitId, quantite);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            log.error("Erreur lors de la libération: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        log.info("POST /api/stocks - Création d'un nouveau stock");
        try {
            Stock createdStock = stockService.create(stock);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
        } catch (RuntimeException e) {
            log.error("Erreur lors de la création du stock: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(
            @PathVariable Long id,
            @RequestBody Stock stockDetails) {
        log.info("PUT /api/stocks/{} - Mise à jour du stock", id);
        try {
            Stock updatedStock = stockService.update(id, stockDetails);
            return ResponseEntity.ok(updatedStock);
        } catch (RuntimeException e) {
            log.error("Erreur lors de la mise à jour du stock: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        log.info("DELETE /api/stocks/{} - Suppression du stock", id);
        try {
            stockService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Erreur lors de la suppression du stock: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}