package org.example.views;

import org.example.controllers.AccountController;
import org.example.controllers.ClientController;
import org.example.repositories.AccountRepository;
import org.example.repositories.ClientRepository;
import org.example.services.AccountService;
import org.example.services.ClientService;
import org.example.util.InputValidator;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {

    private Scanner sc = new Scanner(System.in);
    private ClientRepository clientRepository = new ClientRepository();
    private ClientService clientService = new ClientService(clientRepository);
    private ClientController clientController = new ClientController(clientService);
    private AccountRepository accountRepository = new  AccountRepository();
    private AccountService accountService = new AccountService(accountRepository);
    private AccountController accountController = new AccountController(accountService, clientService);

    public void menuTeller(String username){
        System.out.println("===========================");
        System.out.println("Welcome to Teller " + username);
        System.out.println("============================");
        System.out.println("============menu============");
        System.out.println("1. add new client");
        System.out.println("2. update info client");
        System.out.println("3. view all clients");
        System.out.println("4. close client");
        System.out.println("5. Create account ");
        System.out.println("6. List my accounts");
        System.out.println("7. Close account");
        System.out.println(". exit");

        System.out.println();
        int choice = 0;
        boolean isValid = false;

        do{
            try {
                choice = InputValidator.getInt("write what number would you like to do?");
                isValid = true;
                switch (choice) {
                    case 1:
                        clientController.addClient();
                        break;
                    case 2:
                        clientController.updateCleint();
                        break;
                    case 3:
                        clientController.viewAllClients();
                        break;
                    case 4:
                        clientController.closeClient();
                        break;
                    case 5:
                        accountController.createAccount();
                        break;
                    default:
                        System.out.println("Invalid choice , please choise just between 1 and 3");
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