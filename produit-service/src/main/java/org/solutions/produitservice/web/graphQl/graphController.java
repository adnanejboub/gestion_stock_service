package org.solutions.produitservice.web.graphQl;

import org.solutions.produitservice.dtos.LigneCommandeDTO;
import org.solutions.produitservice.dtos.StockDTO;
import org.solutions.produitservice.entities.Produit;
import org.solutions.produitservice.services.ProduitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class graphController {

    @Autowired
    private ProduitServiceImpl produitService;

    @QueryMapping
    public List<Produit> ProduitList() {
        List<Produit> produits = produitService.findAll();
        return produits;
    }

    @QueryMapping
    public List<Produit> ProduitsByFournisseur(@Argument Long fournisseurId) {
        List<Produit> produits = produitService.findByFournisseurId(fournisseurId);
        return produits;
    }

    @QueryMapping
    public Produit ProduitById(@Argument Long id) {
        return produitService.findById(id);
    }

    @QueryMapping
    public Produit ProduitByReference(@Argument String reference) {
        return produitService.findByReference(reference);
    }

    @QueryMapping
    public List<Produit> ProduitsByCategorie(@Argument String categorie) {
        return produitService.findByCategorie(categorie);
    }

    @QueryMapping
    public List<Produit> ProduitsByMarque(@Argument String marque) {
        return produitService.findByMarque(marque);
    }

    @QueryMapping
    public StockDTO StockByProduitId(@Argument Long produitId) {
        return produitService.getStockByProduitId(produitId);
    }

    @QueryMapping
    public Produit ProduitWithDetails(@Argument Long produitId) {
        return produitService.getProduitWithDetails(produitId);
    }

    @QueryMapping
    public List<LigneCommandeDTO> LignesCommandeByProduit(@Argument Long produitId) {
        return produitService.getLignesCommandeByProduit(produitId);
    }

    @QueryMapping
    public Integer CountCommandesByProduit(@Argument Long produitId) {
        return produitService.countCommandesByProduit(produitId);
    }

    @MutationMapping
    public Produit createProduit(@Argument Produit prod) {
            Produit createdProduit = produitService.create(prod);
            return createdProduit;
    }

    @MutationMapping
    public Produit updateProduit(@Argument Long id, @Argument("prod") Produit prod) {
        return produitService.update(id, prod);
    }

    @MutationMapping
    public Boolean deleteProduit(@Argument Long id) {
        produitService.delete(id);
        return true;
    }
}
