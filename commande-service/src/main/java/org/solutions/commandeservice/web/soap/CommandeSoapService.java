package org.solutions.commandeservice.web.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import org.solutions.commandeservice.entities.Commande;
import org.solutions.commandeservice.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@WebService(
    serviceName = "CommandeSoapService",
    portName = "CommandeSoapPort",
    targetNamespace = "http://soap.web.commandeservice.solutions.org/"
)
@Component
public class CommandeSoapService {

    @Autowired
    private CommandeService commandeService;

    @WebMethod(operationName = "getCommandeById")
    @WebResult(name = "commande")
    public Commande getCommandeById(@WebParam(name = "id") Long id) {
        try {
            Commande commande = commandeService.findById(id);
            // Préparer la commande pour la sérialisation SOAP
            prepareCommandeForSerialization(commande);
            return commande;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de la commande: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "getAllCommandes")
    @WebResult(name = "commandes")
    public List<Commande> getAllCommandes() {
        try {
            List<Commande> commandes = commandeService.findAll();
            // Préparer chaque commande pour la sérialisation SOAP
            for (Commande commande : commandes) {
                prepareCommandeForSerialization(commande);
            }
            return commandes;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des commandes: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "createCommande")
    @WebResult(name = "commande")
    public Commande createCommande(@WebParam(name = "commande") Commande commande) {
        try {
            Commande createdCommande = commandeService.create(commande);
            // Préparer la commande pour la sérialisation SOAP
            prepareCommandeForSerialization(createdCommande);
            return createdCommande;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de la commande: " + e.getMessage());
        }
    }

    /**
     * Prépare une commande pour la sérialisation SOAP en gérant les relations lazy
     */
    private void prepareCommandeForSerialization(Commande commande) {
        if (commande != null) {
            try {
                // Initialiser la liste des lignes de commande si elle est lazy
                if (commande.getLignesCommande() != null) {
                    // Forcer l'initialisation en accédant à la taille
                    // Cela peut échouer si la session Hibernate est fermée, donc on catch l'exception
                    try {
                        commande.getLignesCommande().size();
                    } catch (Exception e) {
                        // Si la session est fermée, initialiser avec une liste vide
                        commande.setLignesCommande(new ArrayList<>());
                    }
                } else {
                    // Initialiser avec une liste vide pour éviter les null
                    commande.setLignesCommande(new ArrayList<>());
                }
            } catch (Exception e) {
                // En cas d'erreur, s'assurer au moins que la liste n'est pas null
                if (commande.getLignesCommande() == null) {
                    commande.setLignesCommande(new ArrayList<>());
                }
            }
            // S'assurer que le client transient est null pour éviter les problèmes de sérialisation
            commande.setClient(null);
        }
    }

}
