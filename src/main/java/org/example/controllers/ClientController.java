package org.example.controllers;

import org.example.models.Client;
import org.example.models.User;
import org.example.services.ClientService;
import org.example.util.InputValidator;
import org.example.util.Session;
import org.example.views.MainMenu;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientController {

    private ClientService clientService ;
    private Scanner scanner = new Scanner(System.in);
    private Session session = Session.getInstance();
    private Integer roleId = session.getSession("role_id", Integer.class);

    private void redirectByRole() {
        Session session = Session.getInstance();
        Integer roleId = session.getSession("role_id", Integer.class);

        if (roleId == null) {
            System.out.println("⚠ No role_id found in session. Redirect to login.");
            return;
        }

        switch (roleId) {
            case 1 -> new MainMenu().menuAdmin();
            case 2 -> new MainMenu().menuTeller();
            case 3 -> System.out.println("AUDITOR");
            case 4 -> new MainMenu().menuManager();
            default -> System.out.println("Unknown role, please login again.");
        }
    }


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
            redirectByRole();
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
                continu = InputValidator.getBoolean("would you like update author info? \ntrue. yes \nfalse. no") ;
        }while (continu);

       boolean isUpdate = this.clientService.updateClient(id,map);
       if(isUpdate){
           System.out.println("Client updated successfully");
           redirectByRole();
       }

    }

    public void viewAllClients() {
      List<Client> clients =  this.clientService.getAllClients();
      clients.stream().forEach(System.out::println);
    }

    public void closeClient() {
        this.viewAllClients();
        int id = InputValidator.getInt("Enter ID to colse");
        String  isClose =  this.clientService.closeCline(id);
        System.out.println(isClose);
        redirectByRole();
    }
}
