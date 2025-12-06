package org.solutions.produitservice.web.rest;

import org.solutions.produitservice.entities.Produit;
import org.solutions.produitservice.services.ProduitServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitRestController {
    
    @Autowired
    private ProduitServiceImp produitService;

    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {
        List<Produit> produits = produitService.findAll();
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        try {
            Produit produit = produitService.findById(id);
            return ResponseEntity.ok(produit);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reference/{reference}")
    public ResponseEntity<Produit> getProduitByReference(@PathVariable String reference) {
        try {
            Produit produit = produitService.findByReference(reference);
            return ResponseEntity.ok(produit);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<Produit>> getProduitsByCategorie(@PathVariable String categorie) {
        List<Produit> produits = produitService.findByCategorie(categorie);
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/marque/{marque}")
    public ResponseEntity<List<Produit>> getProduitsByMarque(@PathVariable String marque) {
        List<Produit> produits = produitService.findByMarque(marque);
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<List<Produit>> getProduitsByFournisseur(@PathVariable Long fournisseurId) {
        List<Produit> produits = produitService.findByFournisseur_Id(fournisseurId);
        return ResponseEntity.ok(produits);
    }

    @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        try {
            Produit createdProduit = produitService.create(produit);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduit);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(
            @PathVariable Long id,
            @RequestBody Produit produitDetails) {
        try {
            Produit updatedProduit = produitService.update(id, produitDetails);
            return ResponseEntity.ok(updatedProduit);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        try {
            produitService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
