package org.solutions.fournisseurservice.web.rest;

import org.solutions.fournisseurservice.entities.Fournisseur;
import org.solutions.fournisseurservice.services.FournisseurServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurRestController {
    
    @Autowired
    private FournisseurServiceImp fournisseurService;

    @GetMapping
    public ResponseEntity<List<Fournisseur>> getAllFournisseurs() {
        List<Fournisseur> fournisseurs = fournisseurService.findAll();
        return ResponseEntity.ok(fournisseurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable Long id) {
        try {
            Fournisseur fournisseur = fournisseurService.findById(id);
            return ResponseEntity.ok(fournisseur);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Fournisseur> getFournisseurByEmail(@PathVariable String email) {
        try {
            Fournisseur fournisseur = fournisseurService.findByEmail(email);
            return ResponseEntity.ok(fournisseur);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ville/{ville}")
    public ResponseEntity<List<Fournisseur>> getFournisseursByVille(@PathVariable String ville) {
        List<Fournisseur> fournisseurs = fournisseurService.findByVille(ville);
        return ResponseEntity.ok(fournisseurs);
    }

    @PostMapping
    public ResponseEntity<Fournisseur> createFournisseur(@RequestBody Fournisseur fournisseur) {
        try {
            Fournisseur createdFournisseur = fournisseurService.create(fournisseur);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFournisseur);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(
            @PathVariable Long id,
            @RequestBody Fournisseur fournisseurDetails) {
        try {
            Fournisseur updatedFournisseur = fournisseurService.update(id, fournisseurDetails);
            return ResponseEntity.ok(updatedFournisseur);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        try {
            fournisseurService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
