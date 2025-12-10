package org.solutions.fournisseurservice.web.soap;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.solutions.fournisseurservice.entities.Fournisseur;
import org.solutions.fournisseurservice.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@WebService(serviceName = "FournisseurSoapService", portName = "FournisseurSoapPort")
@Component
public class FournisseurSoapService {

    @Autowired
    private FournisseurService fournisseurService;

    @WebMethod(operationName = "getFournisseurById")
    public Fournisseur getFournisseurById(@WebParam(name = "id") Long id) {
        return fournisseurService.findById(id);
    }

    @WebMethod(operationName = "getAllFournisseurs")
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurService.findAll();
    }

    @WebMethod(operationName = "createFournisseur")
    public Fournisseur createFournisseur(@WebParam(name = "fournisseur") Fournisseur fournisseur) {
        return fournisseurService.create(fournisseur);
    }
}

