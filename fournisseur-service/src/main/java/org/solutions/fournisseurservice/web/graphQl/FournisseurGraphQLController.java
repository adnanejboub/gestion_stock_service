package org.solutions.fournisseurservice.web.graphQl;

import org.solutions.fournisseurservice.dtos.ProduitDTO;
import org.solutions.fournisseurservice.entities.Fournisseur;
import org.solutions.fournisseurservice.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class FournisseurGraphQLController {

    @Autowired
    private FournisseurService fournisseurService;

    @QueryMapping
    public List<Fournisseur> FournisseurList() {
        return fournisseurService.findAll();
    }

    @QueryMapping
    public Fournisseur FournisseurById(@Argument Long id) {
        return fournisseurService.findById(id);
    }

    @QueryMapping
    public Fournisseur FournisseurByEmail(@Argument String email) {
        return fournisseurService.findByEmail(email);
    }

    @QueryMapping
    public Fournisseur FournisseurByNomEntreprise(@Argument String nomEntreprise) {
        return fournisseurService.findByNomEntreprise(nomEntreprise);
    }

    @QueryMapping
    public List<Fournisseur> FournisseursByVille(@Argument String ville) {
        return fournisseurService.findByVille(ville);
    }

    @QueryMapping
    public List<Fournisseur> FournisseursByPays(@Argument String pays) {
        return fournisseurService.findByPays(pays);
    }

    // Feign-backed data
    @QueryMapping
    public Fournisseur FournisseurWithProduits(@Argument Long id) {
        return fournisseurService.getFournisseurWithProduits(id);
    }

    @QueryMapping
    public List<ProduitDTO> ProduitsByFournisseur(@Argument Long id) {
        return fournisseurService.getProduitsByFournisseur(id);
    }

    @QueryMapping
    public Integer CountProduitsByFournisseur(@Argument Long id) {
        return fournisseurService.countProduitsByFournisseur(id);
    }

    @MutationMapping
    public Fournisseur createFournisseur(@Argument("fournisseur") Fournisseur fournisseur) {
        return fournisseurService.create(fournisseur);
    }

    @MutationMapping
    public Fournisseur updateFournisseur(@Argument Long id, @Argument("fournisseur") Fournisseur fournisseur) {
        return fournisseurService.update(id, fournisseur);
    }

    @MutationMapping
    public Boolean deleteFournisseur(@Argument Long id) {
        fournisseurService.delete(id);
        return true;
    }
}

