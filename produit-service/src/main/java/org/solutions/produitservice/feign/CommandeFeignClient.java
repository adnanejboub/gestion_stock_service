package org.solutions.produitservice.feign;

import org.solutions.produitservice.dtos.LigneCommandeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@FeignClient(name = "commande-service", url = "${commande.service.url:http://localhost:8084}")
public interface CommandeFeignClient {

    @GetMapping("/api/lignes-commande/produit/{produitId}")
    List<LigneCommandeDTO> getLignesCommandeByProduitId(@PathVariable("produitId") Long produitId);

    @GetMapping("/api/lignes-commande/produit/{produitId}/count")
    Integer countLignesCommandeByProduitId(@PathVariable("produitId") Long produitId);
}