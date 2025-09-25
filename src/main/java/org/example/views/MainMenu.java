package org.example.views;

import org.example.controllers.ClientController;
import org.example.repositories.ClientRepository;
import org.example.services.ClientService;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {

    private Scanner sc = new Scanner(System.in);
    private ClientRepository clientRepository = new ClientRepository();
    private ClientService clientService = new ClientService(clientRepository);
    private ClientController clientController = new ClientController(clientService);

    public void menuTeller(String username){
        System.out.println("===========================");
        System.out.println("Welcome to Teller " + username);
        System.out.println("============================");
        System.out.println("============menu============");
        System.out.println("1. add new client");
        System.out.println("2. update info client");
        System.out.println("3. view client");
        System.out.println("4. view all clients");
        System.out.println("5. exit");

        System.out.println("write what number would you like to do?");
        int choice = 0;
        boolean isValid = false;

        do{
            try {
                choice = sc.nextInt();
                isValid = true;
                switch (choice) {
                    case 1:
                        clientController.addClient();
                        break;
                    case 2:
                        clientController.updateCleint();
                        break;
                    case 3:
                        clientController.viewClient();
                        break;
                    case 4:
                        clientController.viewAllClients();
                        break;
                    default:
                        System.out.println("Invalid choice , please choise just between 1 and 4");
                        isValid = false;
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid input, please enter a number!");
                sc.nextLine();
            }
        }while(!isValid);



    }
}