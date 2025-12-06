package org.solutions.commandeservice.services;

import org.solutions.commandeservice.entities.Commande;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommandeService {
    List<Commande> getCommandesByClientId(Long clientId);

    Double getTotalCommandesByClientId(Long clientId);
}
