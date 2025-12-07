package org.solutions.produitservice.feign;

import org.solutions.produitservice.dtos.StockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stock-service", url = "${stock.service.url:http://localhost:8082}")
public interface StockFeignClient {

    @GetMapping("/api/stocks/produit/{produitId}")
    StockDTO getStockByProduitId(@PathVariable("produitId") Long produitId);
}
