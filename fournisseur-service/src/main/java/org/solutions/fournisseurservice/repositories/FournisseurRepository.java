package org.solutions.fournisseurservice.repositories;

import org.solutions.fournisseurservice.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    Optional<Fournisseur> findByEmail(String email);
    Optional<Fournisseur> findByNomEntreprise(String nomEntreprise);
    List<Fournisseur> findByVille(String ville);
    boolean existsByEmail(String email);
    boolean existsByNomEntreprise(String nomEntreprise);
    List<Fournisseur> findByPays(String pays);
}
