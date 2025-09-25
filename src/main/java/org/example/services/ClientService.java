package org.example.services;

import org.example.interfaces.ClientInterface;
import org.example.models.Client;
import org.example.models.User;
import org.example.repositories.ClientRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Client createClient(String firstName, String  lastName, String  email, BigDecimal salary, String address, String cin){
        Client client =   this.clientRepository.create(firstName,lastName,email,salary,address,cin);
        if(client != null) return client;
        return null;
    }


    public List<Client> getAllClients() {
        return this.clientRepository.getAll();
    }

    public boolean updateClient(int id ,Map<String, Object> data){
         return this.clientRepository.update(id,data);
    }



}
