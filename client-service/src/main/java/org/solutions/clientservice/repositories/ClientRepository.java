package org.solutions.clientservice.repositories;

import org.solutions.clientservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    List<Client> findByVille(String ville);
    List<Client> findByActif(Boolean actif);
    Optional<Client> findByTelephone(String telephone);
    boolean existsByEmail(String email);





}
