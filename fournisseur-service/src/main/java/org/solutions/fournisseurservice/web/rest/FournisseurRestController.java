package org.solutions.fournisseurservice.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solutions.fournisseurservice.dtos.ProduitDTO;
import org.solutions.fournisseurservice.entities.Fournisseur;
import org.solutions.fournisseurservice.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurRestController {
    private static final Logger log = LoggerFactory.getLogger(FournisseurRestController.class);

    @Autowired
    private FournisseurService fournisseurService;


    @GetMapping
    public ResponseEntity<List<Fournisseur>> getAllFournisseurs() {
        log.info("GET /api/fournisseurs - Récupération de tous les fournisseurs");
        List<Fournisseur> fournisseurs = fournisseurService.findAll();
        return ResponseEntity.ok(fournisseurs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable Long id) {
        log.info("GET /api/fournisseurs/{} - Récupération du fournisseur", id);
        try {
            Fournisseur fournisseur = fournisseurService.findById(id);
            return ResponseEntity.ok(fournisseur);
        } catch (RuntimeException e) {
            log.error("Fournisseur non trouvé avec l'ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}/with-produits")
    public ResponseEntity<Fournisseur> getFournisseurWithProduits(@PathVariable Long id) {
        log.info("GET /api/fournisseurs/{}/with-produits - Récupération avec produits", id);
        try {
            Fournisseur fournisseur = fournisseurService.getFournisseurWithProduits(id);
            return ResponseEntity.ok(fournisseur);
        } catch (RuntimeException e) {
            log.error("Fournisseur non trouvé avec l'ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Fournisseur> getFournisseurByEmail(@PathVariable String email) {
        log.info("GET /api/fournisseurs/email/{} - Récupération du fournisseur", email);
        try {
            Fournisseur fournisseur = fournisseurService.findByEmail(email);
            return ResponseEntity.ok(fournisseur);
        } catch (RuntimeException e) {
            log.error("Fournisseur non trouvé avec l'email: {}", email);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/entreprise/{nomEntreprise}")
    public ResponseEntity<Fournisseur> getFournisseurByNomEntreprise(@PathVariable String nomEntreprise) {
        log.info("GET /api/fournisseurs/entreprise/{} - Récupération du fournisseur", nomEntreprise);
        try {
            Fournisseur fournisseur = fournisseurService.findByNomEntreprise(nomEntreprise);
            return ResponseEntity.ok(fournisseur);
        } catch (RuntimeException e) {
            log.error("Fournisseur non trouvé avec le nom: {}", nomEntreprise);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ville/{ville}")
    public ResponseEntity<List<Fournisseur>> getFournisseursByVille(@PathVariable String ville) {
        log.info("GET /api/fournisseurs/ville/{} - Récupération des fournisseurs", ville);
        List<Fournisseur> fournisseurs = fournisseurService.findByVille(ville);
        return ResponseEntity.ok(fournisseurs);
    }


    @GetMapping("/pays/{pays}")
    public ResponseEntity<List<Fournisseur>> getFournisseursByPays(@PathVariable String pays) {
        log.info("GET /api/fournisseurs/pays/{} - Récupération des fournisseurs", pays);
        List<Fournisseur> fournisseurs = fournisseurService.findByPays(pays);
        return ResponseEntity.ok(fournisseurs);
    }


    @GetMapping("/{id}/produits")
    public ResponseEntity<List<ProduitDTO>> getProduitsByFournisseur(@PathVariable Long id) {
        log.info("GET /api/fournisseurs/{}/produits - Récupération des produits", id);
        try {
            List<ProduitDTO> produits = fournisseurService.getProduitsByFournisseur(id);
            return ResponseEntity.ok(produits);
        } catch (RuntimeException e) {
            log.error("Erreur lors de la récupération des produits: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{id}/produits/count")
    public ResponseEntity<Integer> countProduitsByFournisseur(@PathVariable Long id) {
        log.info("GET /api/fournisseurs/{}/produits/count - Comptage des produits", id);
        try {
            Integer count = fournisseurService.countProduitsByFournisseur(id);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            log.error("Erreur lors du comptage des produits: {}", e.getMessage());
            return ResponseEntity.ok(0);
        }
    }


    @PostMapping
    public ResponseEntity<Fournisseur> createFournisseur(@RequestBody Fournisseur fournisseur) {
        log.info("POST /api/fournisseurs - Création d'un nouveau fournisseur");
        try {
            Fournisseur createdFournisseur = fournisseurService.create(fournisseur);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFournisseur);
        } catch (RuntimeException e) {
            log.error("Erreur lors de la création du fournisseur: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(
            @PathVariable Long id,
            @RequestBody Fournisseur fournisseurDetails) {
        log.info("PUT /api/fournisseurs/{} - Mise à jour du fournisseur", id);
        try {
            Fournisseur updatedFournisseur = fournisseurService.update(id, fournisseurDetails);
            return ResponseEntity.ok(updatedFournisseur);
        } catch (RuntimeException e) {
            log.error("Erreur lors de la mise à jour du fournisseur: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        log.info("DELETE /api/fournisseurs/{} - Suppression du fournisseur", id);
        try {
            fournisseurService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Erreur lors de la suppression du fournisseur: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
