package org.solutions.commandeservice.web.soap;

import jakarta.xml.ws.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CommandeSoapPublisher implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CommandeSoapService commandeSoapService = applicationContext.getBean(CommandeSoapService.class);
        Endpoint.publish("http://0.0.0.0:8091/ws/commande", commandeSoapService);
        System.out.println("Service SOAP Commande publié sur http://0.0.0.0:8091/ws/commande");
        System.out.println("WSDL disponible sur http://0.0.0.0:8091/ws/commande?wsdl");
    }
}

