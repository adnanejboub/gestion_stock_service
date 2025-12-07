package org.solutions.fournisseurservice.feign;

import org.solutions.fournisseurservice.dtos.ProduitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "produit-service", url = "${produit.service.url:http://localhost:8081}")
public interface ProduitFeignClient {

    @GetMapping("/api/produits/fournisseur/{fournisseurId}")
    List<ProduitDTO> getProduitsByFournisseurId(@PathVariable("fournisseurId") Long fournisseurId);

    @GetMapping("/api/produits/fournisseur/{fournisseurId}/count")
    Integer countProduitsByFournisseurId(@PathVariable("fournisseurId") Long fournisseurId);
}
