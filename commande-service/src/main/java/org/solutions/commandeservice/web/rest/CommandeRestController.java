package org.solutions.commandeservice.web.rest;

import org.solutions.commandeservice.entities.Commande;
import org.solutions.commandeservice.services.CommandeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeRestController {
    
    @Autowired
    private CommandeServiceImp commandeService;

    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {
        List<Commande> commandes = commandeService.findAll();
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        try {
            Commande commande = commandeService.findById(id);
            return ResponseEntity.ok(commande);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Commande>> getCommandesByClientId(@PathVariable Long clientId) {
        List<Commande> commandes = commandeService.findByClient_Id(clientId);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Commande>> getCommandesByStatut(@PathVariable String statut) {
        List<Commande> commandes = commandeService.findByStatut(statut);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/statut-paiement/{statutPaiement}")
    public ResponseEntity<List<Commande>> getCommandesByStatutPaiement(@PathVariable String statutPaiement) {
        List<Commande> commandes = commandeService.findByStatutPaiement(statutPaiement);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Commande>> getCommandesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Commande> commandes = commandeService.findByDateCommandeBetween(startDate, endDate);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/client/{clientId}/total")
    public ResponseEntity<Double> getTotalCommandesByClientId(@PathVariable Long clientId) {
        Double total = commandeService.getTotalCommandesByClientId(clientId);
        return ResponseEntity.ok(total);
    }

    @PostMapping
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        try {
            Commande createdCommande = commandeService.create(commande);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCommande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(
            @PathVariable Long id,
            @RequestBody Commande commandeDetails) {
        try {
            Commande updatedCommande = commandeService.update(id, commandeDetails);
            return ResponseEntity.ok(updatedCommande);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        try {
            commandeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
