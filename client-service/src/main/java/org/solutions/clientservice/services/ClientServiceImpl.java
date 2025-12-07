package org.solutions.clientservice.services;

import jakarta.transaction.Transactional;
import org.solutions.clientservice.dtos.CommandeDTO;
import org.solutions.clientservice.entities.Client;
import org.solutions.clientservice.feign.CommandeFeignClient;
import org.solutions.clientservice.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CommandeFeignClient commandeFeignClient;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID: " + id));
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'email: " + email));
    }

    @Override
    public Client create(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new RuntimeException("Un client avec cet email existe déjà: " + client.getEmail());
        }
        Client savedClient = clientRepository.save(client);
        return savedClient;
    }

    @Override
    public Client update(Long id, Client clientDetails) {
        log.info("Mise à jour du client avec l'ID: {}", id);

        Client client = findById(id);
        client.setNom(clientDetails.getNom());
        client.setPrenom(clientDetails.getPrenom());

        if (!client.getEmail().equals(clientDetails.getEmail()) &&
                clientRepository.existsByEmail(clientDetails.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé par un autre client");
        }

        client.setEmail(clientDetails.getEmail());
        client.setTelephone(clientDetails.getTelephone());
        client.setAdresse(clientDetails.getAdresse());
        client.setVille(clientDetails.getVille());
        client.setPays(clientDetails.getPays());

        Client updatedClient = clientRepository.save(client);
        log.info("Client mis à jour avec succès - ID: {}", updatedClient.getId());
        return updatedClient;
    }

    @Override
    public void delete(Long id) {
        Client client = findById(id);
        clientRepository.delete(client);
        log.info("Client supprimé avec succès - ID: {}", id);
    }

    @Override
    public Client deactivate(Long id) {
        Client client = findById(id);
        client.setActif(false);
        Client deactivatedClient = clientRepository.save(client);
        return deactivatedClient;
    }

    @Override
    public List<Client> findByVille(String ville) {
        log.info("Recherche des clients dans la ville: {}", ville);
        return clientRepository.findByVille(ville);
    }

    @Override
    public List<Client> findActifs() {
        log.info("Récupération de tous les clients actifs");
        return clientRepository.findByActif(true);
    }

    // ========== Méthodes pour GraphQL (avec appels Feign) ==========

    @Override
    public Client getClientWithCommandes(Long clientId) {
        log.info("Récupération du client {} avec ses commandes", clientId);

        Client client = findById(clientId);

        try {
            List<CommandeDTO> commandes = commandeFeignClient.getCommandesByClientId(clientId);
            client.setCommandes(commandes);
            client.setNombreCommandes(commandes.size());

            Double totalDepense = commandes.stream()
                    .mapToDouble(CommandeDTO::getMontantTotal)
                    .sum();
            client.setTotalDepense(totalDepense);

            log.info("Client {} a {} commandes pour un total de {} MAD",
                    clientId, commandes.size(), totalDepense);

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des commandes du client {}", clientId, e);
            client.setNombreCommandes(0);
            client.setTotalDepense(0.0);
        }

        return client;
    }

    @Override
    public List<CommandeDTO> getCommandesByClient(Long clientId) {
        log.info("Récupération des commandes du client {}", clientId);
        findById(clientId);

        try {
            List<CommandeDTO> commandes = commandeFeignClient.getCommandesByClientId(clientId);
            log.info("Nombre de commandes récupérées pour le client {}: {}", clientId, commandes.size());
            return commandes;

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des commandes du client {}", clientId, e);
            throw new RuntimeException("Impossible de récupérer les commandes du client. Service Commande indisponible.", e);
        }
    }

    @Override
    public Double getTotalDepense(Long clientId) {
        log.info("Calcul du total des dépenses du client {}", clientId);
        findById(clientId);

        try {
            Double total = commandeFeignClient.getTotalCommandesByClientId(clientId);
            log.info("Total des dépenses du client {}: {} MAD", clientId, total);
            return total;

        } catch (Exception e) {
            log.error("Erreur lors du calcul du total des dépenses du client {}", clientId, e);
            log.warn("Retour de 0.0 par défaut pour le client {}", clientId);
            return 0.0;
        }
    }

    @Override
    public Integer countCommandesByClient(Long clientId) {
        findById(clientId);
        try {
            Integer count = commandeFeignClient.countCommandesByClientId(clientId);
            log.info("Nombre de commandes du client {}: {}", clientId, count);
            return count;

        } catch (Exception e) {
            log.error("Erreur lors du comptage des commandes du client {}", clientId, e);
            return 0;
        }
    }
}