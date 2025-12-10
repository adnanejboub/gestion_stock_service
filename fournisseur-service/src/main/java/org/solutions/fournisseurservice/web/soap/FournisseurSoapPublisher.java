package org.solutions.fournisseurservice.web.soap;

import jakarta.xml.ws.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FournisseurSoapPublisher implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        FournisseurSoapService fournisseurSoapService = applicationContext.getBean(FournisseurSoapService.class);
        Endpoint.publish("http://0.0.0.0:8090/ws/fournisseur", fournisseurSoapService);
        System.out.println("Service SOAP Fournisseur publié sur http://0.0.0.0:8090/ws/fournisseur");
        System.out.println("WSDL disponible sur http://0.0.0.0:8090/ws/fournisseur?wsdl");
    }
}

