package org.example.controllers;

import org.example.models.Client;
import org.example.models.User;
import org.example.services.ClientService;
import org.example.util.InputValidator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientController {

    private ClientService clientService ;
    private Scanner scanner = new Scanner(System.in);

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }
    public ClientController(){
    }

    public void addClient() {
        System.out.println("====here ! your create new client");

        String firstName = InputValidator.getString("Enter first name:");
        String lastName  = InputValidator.getString("Enter last name:");
        String email     = InputValidator.getEmail("Enter email:");
        BigDecimal salary = InputValidator.getBigDecimal("Enter salary:");
        String address   = InputValidator.getString("Enter address:");
        String cin       = InputValidator.getString("Enter CIN:");

        Client client =  this.clientService.createClient(firstName,lastName,email,salary,address,cin);

        if(client != null){
            System.out.println("Client created successfully");
        }else{
            System.out.println("Client not created");
        }

    }

    public  void updateCleint() {
        this.viewAllClients();
        int id = InputValidator.getInt("Enter ID to update");
        Map<String,Object> map = new HashMap<>();

        boolean continu = true;
        do {
            System.out.println("1. first_name");
            System.out.println("2. last_name");
            System.out.println("3. email");
            System.out.println("4. address");
            System.out.println("5. salary");
            System.out.println("6. Exit");

            int choice = InputValidator.getInt("Enter choice to update");

            switch (choice) {
                case 1:
                    map.put("first_name",InputValidator.getString("Enter first name"));
                    break;
                case 2:
                    map.put("last_name",InputValidator.getString("Enter last name"));
                    break;
                case 3:
                    map.put("email",InputValidator.getString("Enter email"));
                    break;
                case 4:
                    map.put("address",InputValidator.getString("Enter address"));
                    break;
                case 5:
                    map.put("salary",InputValidator.getBigDecimal("Enter salary"));
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
                continu = InputValidator.getBoolean("would you like update authr info? \ntrue. yes \nfalse. no") ;
        }while (continu);

        this.clientService.updateClient(id,map);


    }

    public void viewAllClients() {
      List<Client> clients =  this.clientService.getAllClients();
      clients.stream().forEach(System.out::println);
    }

    public void closeClient() {
        this.viewAllClients();
        int id = InputValidator.getInt("Enter ID to colse");
        boolean isClose =  this.clientService.closeCline(id);
        if(isClose){
            System.out.println("Client closed successfully");
        }else{
            System.out.println("Client not closed");
        }
    }
}
