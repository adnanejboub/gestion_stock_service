package org.solutions.fournisseurservice.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solutions.fournisseurservice.dtos.ProduitDTO;
import org.solutions.fournisseurservice.entities.Fournisseur;
import org.solutions.fournisseurservice.feign.ProduitFeignClient;
import org.solutions.fournisseurservice.repositories.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FournisseurServiceImpl implements FournisseurService {

    private static final Logger log = LoggerFactory.getLogger(FournisseurServiceImpl.class);

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private ProduitFeignClient produitFeignClient;

    @Override
    public List<Fournisseur> findAll() {
        log.info("Récupération de tous les fournisseurs");
        return fournisseurRepository.findAll();
    }

    @Override
    public Fournisseur findById(Long id) {
        log.info("Recherche du fournisseur avec l'ID: {}", id);
        return fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID: " + id));
    }

    @Override
    public Fournisseur findByEmail(String email) {
        log.info("Recherche du fournisseur avec l'email: {}", email);
        return fournisseurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'email: " + email));
    }

    @Override
    public Fournisseur findByNomEntreprise(String nomEntreprise) {
        log.info("Recherche du fournisseur avec le nom d'entreprise: {}", nomEntreprise);
        return fournisseurRepository.findByNomEntreprise(nomEntreprise)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec le nom: " + nomEntreprise));
    }

    @Override
    public Fournisseur create(Fournisseur fournisseur) {
        log.info("Création d'un nouveau fournisseur: {}", fournisseur.getNomEntreprise());

        if (fournisseurRepository.existsByEmail(fournisseur.getEmail())) {
            throw new RuntimeException("Un fournisseur avec cet email existe déjà: " + fournisseur.getEmail());
        }

        if (fournisseurRepository.existsByNomEntreprise(fournisseur.getNomEntreprise())) {
            throw new RuntimeException("Un fournisseur avec ce nom existe déjà: " + fournisseur.getNomEntreprise());
        }

        Fournisseur savedFournisseur = fournisseurRepository.save(fournisseur);
        log.info("Fournisseur créé avec succès - ID: {}", savedFournisseur.getId());
        return savedFournisseur;
    }

    @Override
    public Fournisseur update(Long id, Fournisseur fournisseurDetails) {
        log.info("Mise à jour du fournisseur avec l'ID: {}", id);

        Fournisseur fournisseur = findById(id);

        if (!fournisseur.getEmail().equals(fournisseurDetails.getEmail()) &&
                fournisseurRepository.existsByEmail(fournisseurDetails.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé par un autre fournisseur");
        }

        if (!fournisseur.getNomEntreprise().equals(fournisseurDetails.getNomEntreprise()) &&
                fournisseurRepository.existsByNomEntreprise(fournisseurDetails.getNomEntreprise())) {
            throw new RuntimeException("Ce nom d'entreprise est déjà utilisé");
        }

        fournisseur.setNomEntreprise(fournisseurDetails.getNomEntreprise());
        fournisseur.setNomContact(fournisseurDetails.getNomContact());
        fournisseur.setPrenomContact(fournisseurDetails.getPrenomContact());
        fournisseur.setEmail(fournisseurDetails.getEmail());
        fournisseur.setTelephone(fournisseurDetails.getTelephone());
        fournisseur.setAdresse(fournisseurDetails.getAdresse());
        fournisseur.setVille(fournisseurDetails.getVille());
        fournisseur.setPays(fournisseurDetails.getPays());

        Fournisseur updatedFournisseur = fournisseurRepository.save(fournisseur);
        log.info("Fournisseur mis à jour avec succès - ID: {}", updatedFournisseur.getId());
        return updatedFournisseur;
    }

    @Override
    public void delete(Long id) {
        log.info("Suppression du fournisseur avec l'ID: {}", id);
        Fournisseur fournisseur = findById(id);
        fournisseurRepository.delete(fournisseur);
        log.info("Fournisseur supprimé avec succès - ID: {}", id);
    }

    @Override
    public List<Fournisseur> findByVille(String ville) {
        log.info("Recherche des fournisseurs dans la ville: {}", ville);
        return fournisseurRepository.findByVille(ville);
    }

    @Override
    public List<Fournisseur> findByPays(String pays) {
        log.info("Recherche des fournisseurs dans le pays: {}", pays);
        return fournisseurRepository.findByPays(pays);
    }

    // ========== Méthodes avec Feign ==========

    @Override
    public Fournisseur getFournisseurWithProduits(Long fournisseurId) {
        log.info("Récupération du fournisseur {} avec ses produits", fournisseurId);

        Fournisseur fournisseur = findById(fournisseurId);

        try {
            List<ProduitDTO> produits = produitFeignClient.getProduitsByFournisseurId(fournisseurId);
            fournisseur.setProduits(produits);
            fournisseur.setNombreProduits(produits.size());

            log.info("Fournisseur {} a {} produits", fournisseurId, produits.size());

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des produits du fournisseur {}", fournisseurId, e);
            fournisseur.setNombreProduits(0);
        }

        return fournisseur;
    }

    @Override
    public List<ProduitDTO> getProduitsByFournisseur(Long fournisseurId) {
        log.info("Récupération des produits du fournisseur {}", fournisseurId);

        // Vérifier que le fournisseur existe
        findById(fournisseurId);

        try {
            List<ProduitDTO> produits = produitFeignClient.getProduitsByFournisseurId(fournisseurId);
            log.info("Nombre de produits récupérés: {}", produits.size());
            return produits;

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des produits du fournisseur {}", fournisseurId, e);
            throw new RuntimeException("Impossible de récupérer les produits. Service Produit indisponible.", e);
        }
    }

    @Override
    public Integer countProduitsByFournisseur(Long fournisseurId) {
        log.info("Comptage des produits du fournisseur {}", fournisseurId);

        // Vérifier que le fournisseur existe
        findById(fournisseurId);

        try {
            Integer count = produitFeignClient.countProduitsByFournisseurId(fournisseurId);
            log.info("Nombre de produits du fournisseur {}: {}", fournisseurId, count);
            return count;

        } catch (Exception e) {
            log.error("Erreur lors du comptage des produits du fournisseur {}", fournisseurId, e);
            return 0;
        }
    }
}