package org.solutions.stockservice.feign;
import org.solutions.stockservice.dtos.ProduitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produit-service", url = "${produit.service.url:http://localhost:8081}")
public interface ProduitFeignClient {

    @GetMapping("/api/produits/{id}")
    ProduitDTO getProduitById(@PathVariable("id") Long id);
}
