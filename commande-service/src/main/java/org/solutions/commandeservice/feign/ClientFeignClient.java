package org.solutions.commandeservice.feign;

import org.solutions.commandeservice.dtos.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client-service", url = "${client.service.url:http://localhost:8085}")
public interface ClientFeignClient {
    @GetMapping("/api/clients/{id}")
    ClientDTO getClientById(@PathVariable("id") Long id);

    @GetMapping("/api/clients/email/{email}")
    ClientDTO getClientByEmail(@PathVariable("email") String email);
}
