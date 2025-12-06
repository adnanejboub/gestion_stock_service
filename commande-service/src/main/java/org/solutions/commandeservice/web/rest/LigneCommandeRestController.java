package org.solutions.commandeservice.web.rest;

import org.solutions.commandeservice.entities.LigneCommande;
import org.solutions.commandeservice.services.LigneCommandeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lignes-commande")
public class LigneCommandeRestController {
    
    @Autowired
    private LigneCommandeServiceImp ligneCommandeService;

    @GetMapping
    public ResponseEntity<List<LigneCommande>> getAllLignesCommande() {
        List<LigneCommande> lignesCommande = ligneCommandeService.findAll();
        return ResponseEntity.ok(lignesCommande);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigneCommande> getLigneCommandeById(@PathVariable Long id) {
        try {
            LigneCommande ligneCommande = ligneCommandeService.findById(id);
            return ResponseEntity.ok(ligneCommande);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<LigneCommande>> getLignesCommandeByCommandeId(@PathVariable Long commandeId) {
        List<LigneCommande> lignesCommande = ligneCommandeService.findByCommande_Id(commandeId);
        return ResponseEntity.ok(lignesCommande);
    }

    @GetMapping("/produit/{produitId}")
    public ResponseEntity<List<LigneCommande>> getLignesCommandeByProduitId(@PathVariable Long produitId) {
        List<LigneCommande> lignesCommande = ligneCommandeService.findByProduit_Id(produitId);
        return ResponseEntity.ok(lignesCommande);
    }

    @PostMapping
    public ResponseEntity<LigneCommande> createLigneCommande(@RequestBody LigneCommande ligneCommande) {
        try {
            LigneCommande createdLigneCommande = ligneCommandeService.create(ligneCommande);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLigneCommande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LigneCommande> updateLigneCommande(
            @PathVariable Long id,
            @RequestBody LigneCommande ligneCommandeDetails) {
        try {
            LigneCommande updatedLigneCommande = ligneCommandeService.update(id, ligneCommandeDetails);
            return ResponseEntity.ok(updatedLigneCommande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLigneCommande(@PathVariable Long id) {
        try {
            ligneCommandeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
