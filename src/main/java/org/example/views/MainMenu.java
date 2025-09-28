package org.example.views;

import org.example.controllers.AccountController;
import org.example.controllers.ClientController;
import org.example.controllers.FeeruleController;
import org.example.controllers.TransactionController;
import org.example.repositories.AccountRepository;
import org.example.repositories.ClientRepository;
import org.example.repositories.FeeruleRepository;
import org.example.repositories.TransactionRepository;
import org.example.services.AccountService;
import org.example.services.ClientService;
import org.example.services.FeeruleService;
import org.example.services.TransactionServices;
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

    private TransactionRepository transactionRepository = new  TransactionRepository();
    private TransactionServices transactionServices = new TransactionServices(transactionRepository , accountRepository);
    private TransactionController transactionController = new TransactionController(transactionServices , accountService);



    private FeeruleRepository  feeruleRepository = new FeeruleRepository();
    private FeeruleService feeruleService = new FeeruleService(feeruleRepository);
    private FeeruleController feeruleController = new FeeruleController(feeruleService);



    public void teller(){
        System.out.println("============menu============");
        System.out.println("1. add new client");
        System.out.println("2. update info client");
        System.out.println("3. view all clients");
        System.out.println("4. close client");
        System.out.println("5. Create account ");
        System.out.println("6. List all accounts");
        System.out.println("7. Close account");
        System.out.println("8. Deposit");
        System.out.println("9. Withdraw");
        System.out.println("10. Transfer");
        System.out.println("11. credit applications");
    }

    public void manager(){
        System.out.println("12. validte close account ");
        System.out.println("13. validate credit application ");
        System.out.println("14. validate transaction extern");
    }


    public void menuTeller(){
        System.out.println("============= Welcome Teller =================");
        this.teller();
        System.out.println(". exit");


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
                    case 6:
                        accountController.listAllAccount();
                        break;
                    case 7:
                        accountController.closeAccount();
                        break;
                    case 8:
                         transactionController.deposit();
                    default:
                        System.out.println("Invalid choice ");
                        isValid = false;
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid input, please enter a number!");
                sc.nextLine();
            }
        }while(!isValid);



    }

    public void menuManager(){
        System.out.println("============= Welcome manager =================");
        this.teller();
        this.manager();

    }

    public void menuAdmin(){
        System.out.println("============= Welcome admin =================");
        this.teller();
        this.manager();

        System.out.println("15. Create fee rules.");
        System.out.println("16. List fee rules.");
        System.out.println("17. Update fee rules.");
        System.out.println("18. Activate fee rules.");
        System.out.println("19. Deactivate fee rules.");

        int choice = 0;
        boolean isValid = false;
        do{
            choice = InputValidator.getInt("write what number would you like to do?");
            isValid = true;
            switch (choice) {
                case 15:
                    this.feeruleController.addFeeRule();
                    break;
                case 16:
                    this.feeruleController.listFeerules();
                    break;
                case

                default:
                    System.out.println("Invalid choice ");
                    isValid = false;
                    break;
            }
        }while (!isValid);






    }
}