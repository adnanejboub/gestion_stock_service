package org.solutions.stockservice.web.graphQl;

import org.solutions.stockservice.entities.Stock;
import org.solutions.stockservice.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StockGraphQLCobntroller {

    @Autowired
    private StockService stockService;

    @QueryMapping
    public List<Stock> StockList() {
        return stockService.findAll();
    }

    @QueryMapping
    public Stock StockById(@Argument Long id) {
        return stockService.findById(id);
    }

    @QueryMapping
    public Stock StockByProduitId(@Argument Long produitId) {
        return stockService.findByProduitId(produitId);
    }

    @QueryMapping
    public Stock StockWithProduit(@Argument Long id) {
        return stockService.getStockWithProduit(id);
    }

    @QueryMapping
    public List<Stock> StocksByEmplacement(@Argument String emplacement) {
        return stockService.findByEmplacement(emplacement);
    }

    @QueryMapping
    public List<Stock> StocksFaibles(@Argument Integer seuil) {
        return stockService.findStocksFaibles(seuil);
    }

    @QueryMapping
    public List<Stock> StocksEpuises() {
        return stockService.findStocksEpuises();
    }

    @MutationMapping
    public Stock createStock(@Argument("stock") Stock stock) {
        return stockService.create(stock);
    }

    @MutationMapping
    public Stock updateStock(@Argument Long id, @Argument("stock") Stock stock) {
        return stockService.update(id, stock);
    }

    @MutationMapping
    public Boolean deleteStock(@Argument Long id) {
        stockService.delete(id);
        return true;
    }

    @MutationMapping
    public Stock ajouterStock(@Argument Long produitId, @Argument Integer quantite) {
        return stockService.ajouterStock(produitId, quantite);
    }

    @MutationMapping
    public Stock retirerStock(@Argument Long produitId, @Argument Integer quantite) {
        return stockService.retirerStock(produitId, quantite);
    }

    @MutationMapping
    public Stock reserverStock(@Argument Long produitId, @Argument Integer quantite) {
        return stockService.reserverStock(produitId, quantite);
    }

    @MutationMapping
    public Stock libererReservation(@Argument Long produitId, @Argument Integer quantite) {
        return stockService.libererReservation(produitId, quantite);
    }
}
