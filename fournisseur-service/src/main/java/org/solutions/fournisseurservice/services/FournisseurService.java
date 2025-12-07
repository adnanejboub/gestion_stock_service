package org.solutions.fournisseurservice.services;

import org.solutions.fournisseurservice.dtos.ProduitDTO;
import org.solutions.fournisseurservice.entities.Fournisseur;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FournisseurService {
    List<Fournisseur> findAll();
    Fournisseur findById(Long id);
    Fournisseur findByEmail(String email);
    Fournisseur create(Fournisseur fournisseur);
    Fournisseur update(Long id, Fournisseur fournisseurDetails);
    Fournisseur findByNomEntreprise(String nomEntreprise);
    void delete(Long id);
    List<Fournisseur> findByVille(String ville);
    List<Fournisseur> findByPays(String pays);

    // Méthodes avec Feign
    Fournisseur getFournisseurWithProduits(Long fournisseurId);

    List<ProduitDTO> getProduitsByFournisseur(Long fournisseurId);

    Integer countProduitsByFournisseur(Long fournisseurId);
}
