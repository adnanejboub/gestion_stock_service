package org.solutions.clientservice.services;

import jakarta.transaction.Transactional;
import org.solutions.clientservice.entities.Client;
import org.solutions.clientservice.repositories.ClientRepository;
import org.solutions.commandeservice.entities.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;



    @Override
    public List<Client> findAll() {
        List<Client> clients = clientRepository.findAll();
        return clients;
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> {
                    return new RuntimeException("Client non trouvé avec l'ID: " + id);
                });
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> {
                    return new RuntimeException("Client non trouvé avec l'email: " + email);
                });
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
        Client client = findById(id);
        client.setNom(clientDetails.getNom());
        client.setPrenom(clientDetails.getPrenom());

        if (!client.getEmail().equals(clientDetails.getEmail()) &&
                clientRepository.existsByEmail(clientDetails.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé par un autre client");
        }
        Client updatedClient = clientRepository.save(clientDetails);
        return updatedClient;
    }

    @Override
    public void delete(Long id) {
        Client client = findById(id);
        clientRepository.delete(client);
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
        List<Client> clients = clientRepository.findByVille(ville);
        return clients;
    }

    @Override
    public List<Client> findActifs() {
        List<Client> clients = clientRepository.findByActif(true);
        return clients;
    }

}
