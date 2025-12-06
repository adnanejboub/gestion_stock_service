package org.solutions.fournisseurservice.services;

import jakarta.transaction.Transactional;
import org.solutions.fournisseurservice.entities.Fournisseur;
import org.solutions.fournisseurservice.repositories.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FournisseurServiceImp implements FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Override
    public List<Fournisseur> findAll() {
        return fournisseurRepository.findAll();
    }

    @Override
    public Fournisseur findById(Long id) {
        return fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'ID: " + id));
    }

    @Override
    public Fournisseur findByEmail(String email) {
        return fournisseurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé avec l'email: " + email));
    }

    @Override
    public Fournisseur create(Fournisseur fournisseur) {
        if (fournisseurRepository.existsByEmail(fournisseur.getEmail())) {
            throw new RuntimeException("Un fournisseur avec cet email existe déjà: " + fournisseur.getEmail());
        }
        if (fournisseurRepository.existsByNomEntreprise(fournisseur.getNomEntreprise())) {
            throw new RuntimeException("Un fournisseur avec ce nom d'entreprise existe déjà: " + fournisseur.getNomEntreprise());
        }
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public Fournisseur update(Long id, Fournisseur fournisseurDetails) {
        Fournisseur fournisseur = findById(id);
        
        if (!fournisseur.getEmail().equals(fournisseurDetails.getEmail()) &&
                fournisseurRepository.existsByEmail(fournisseurDetails.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé par un autre fournisseur");
        }
        
        if (!fournisseur.getNomEntreprise().equals(fournisseurDetails.getNomEntreprise()) &&
                fournisseurRepository.existsByNomEntreprise(fournisseurDetails.getNomEntreprise())) {
            throw new RuntimeException("Ce nom d'entreprise est déjà utilisé par un autre fournisseur");
        }
        
        fournisseur.setNomEntreprise(fournisseurDetails.getNomEntreprise());
        fournisseur.setNomContact(fournisseurDetails.getNomContact());
        fournisseur.setPrenomContact(fournisseurDetails.getPrenomContact());
        fournisseur.setEmail(fournisseurDetails.getEmail());
        fournisseur.setTelephone(fournisseurDetails.getTelephone());
        fournisseur.setAdresse(fournisseurDetails.getAdresse());
        fournisseur.setVille(fournisseurDetails.getVille());
        fournisseur.setPays(fournisseurDetails.getPays());
        
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public void delete(Long id) {
        Fournisseur fournisseur = findById(id);
        fournisseurRepository.delete(fournisseur);
    }

    @Override
    public List<Fournisseur> findByVille(String ville) {
        return fournisseurRepository.findByVille(ville);
    }
}
