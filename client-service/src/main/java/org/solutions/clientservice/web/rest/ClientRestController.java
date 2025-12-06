package org.solutions.clientservice.web.rest;


import org.solutions.clientservice.entities.Client;
import org.solutions.clientservice.services.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {
    @Autowired
    private ClientServiceImpl clientService;


    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        try {
            Client client = clientService.findById(id);
            return ResponseEntity.ok(client);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Client> getClientByEmail(@PathVariable String email) {
        try {
            Client client = clientService.findByEmail(email);
            return ResponseEntity.ok(client);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/ville/{ville}")
    public ResponseEntity<List<Client>> getClientsByVille(@PathVariable String ville) {
        List<Client> clients = clientService.findByVille(ville);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/actifs")
    public ResponseEntity<List<Client>> getClientsActifs() {
        List<Client> clients = clientService.findActifs();
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        try {
            Client createdClient = clientService.create(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(
            @PathVariable Long id,
            @RequestBody Client clientDetails) {
        try {
            Client updatedClient = clientService.update(id, clientDetails);
            return ResponseEntity.ok(updatedClient);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        try {
            clientService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Client> deactivateClient(@PathVariable Long id) {
        try {
            Client deactivatedClient = clientService.deactivate(id);
            return ResponseEntity.ok(deactivatedClient);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
