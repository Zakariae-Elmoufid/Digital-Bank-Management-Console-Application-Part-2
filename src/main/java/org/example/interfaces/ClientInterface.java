package org.example.interfaces;

import org.example.models.Client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ClientInterface {

    public Client create(String firstName, String lastName, String email, BigDecimal salary, String address, String cin);
    public List<Client> getAll();
    public boolean update(int id , Map<String, Object> data);
    public boolean close(int id);
}
