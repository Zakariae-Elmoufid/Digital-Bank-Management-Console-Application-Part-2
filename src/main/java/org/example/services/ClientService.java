package org.example.services;

import org.example.interfaces.ClientInterface;
import org.example.repositories.ClientRepository;

public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }




}
