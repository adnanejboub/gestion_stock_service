package org.solutions.produitservice.feign;

import org.solutions.produitservice.dtos.FournisseurDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "fournisseur-service", url = "${fournisseur.service.url:http://localhost:8083}")
public interface FournisseurFeignClient {

    @GetMapping("/api/fournisseurs/{id}")
    FournisseurDTO getFournisseurById(@PathVariable("id") Long id);
}
