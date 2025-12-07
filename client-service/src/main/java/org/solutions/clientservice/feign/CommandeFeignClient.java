package org.solutions.clientservice.feign;

import org.solutions.clientservice.dtos.CommandeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "commande-service", url = "${commande.service.url:http://localhost:8082}")
public interface CommandeFeignClient {
    @GetMapping("/api/commandes/client/{clientId}")
    List<CommandeDTO> getCommandesByClientId(@PathVariable("clientId") Long clientId);

    @GetMapping("/api/commandes/client/{clientId}/total")
    Double getTotalCommandesByClientId(@PathVariable("clientId") Long clientId);

    @GetMapping("/api/commandes/client/{clientId}/count")
    Integer countCommandesByClientId(@PathVariable("clientId") Long clientId);
}


