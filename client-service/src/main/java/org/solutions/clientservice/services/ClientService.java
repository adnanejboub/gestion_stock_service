package org.solutions.clientservice.services;

import org.solutions.clientservice.dtos.CommandeDTO;
import org.solutions.clientservice.entities.Client;
import org.solutions.commandeservice.entities.Commande;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public interface ClientService {

    List<Client> findAll();

    Client findById(Long id);

    Client findByEmail(String email);

    Client create(Client client);

    Client update(Long id, Client clientDetails);

    void delete(Long id);

    Client deactivate(Long id);

    List<Client> findByVille(String ville);
    List<Client> findActifs();


    Client getClientWithCommandes(Long clientId);

    List<CommandeDTO> getCommandesByClient(Long clientId);

    Double getTotalDepense(Long clientId);

    Integer countCommandesByClient(Long clientId);
}
