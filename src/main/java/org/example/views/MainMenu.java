package org.example.views;

import org.example.controllers.*;
import org.example.repositories.*;
import org.example.services.*;
import org.example.util.InputValidator;
import org.example.util.Session;

import java.awt.*;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainMenu {

    private Scanner sc = new Scanner(System.in);
    private ClientRepository clientRepository = new ClientRepository();
    private ClientService clientService = new ClientService(clientRepository);
    private ClientController clientController = new ClientController(clientService);


    private AccountRepository accountRepository = new  AccountRepository();
    private AccountService accountService = new AccountService(accountRepository);
    private AccountController accountController = new AccountController(accountService, clientService);



    private FeeruleRepository  feeruleRepository = new FeeruleRepository();
    private FeeruleService feeruleService = new FeeruleService(feeruleRepository);
    private FeeruleController feeruleController = new FeeruleController(feeruleService);


    private TransactionRepository transactionRepository = new  TransactionRepository();
    private TransactionServices transactionServices = new TransactionServices(transactionRepository , accountRepository);
    private TransactionController transactionController = new TransactionController(transactionServices , accountService , clientService ,feeruleService);

    private CreditRepository creditRepository = new  CreditRepository();
    private CreditService creditService = new CreditService(creditRepository,accountRepository,transactionRepository);
    private CreditController creditController = new CreditController(creditService, accountService,clientService);







    public void teller(){

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            try {
                System.out.println("➡ Running credit deduction job...");
                creditService.deductionMonthly();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        //scheduler.scheduleAtFixedRate(task, 0, 60, TimeUnit.SECONDS);

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
                         break;
                    case 9:
                        transactionController.withdraw();
                        break;
                    case 10:
                        int Transfer = InputValidator.getInt("would you like to transfer.\n1. Intern. \n2. external");
                        if(Transfer == 1) this.transactionController.transferIntern();
                        else if(Transfer == 2) this.transactionController.transferExternal();
                        else System.out.println("Invalid choice , try again");
                        break;
                    case 11:
                        creditController.requestCredit();
                        break;

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

//        12. validte close account
//        13. validate credit application
//        14. validate transaction extern

    public void menuManager(){
        System.out.println("============= Welcome manager ===============");
        this.teller();
        this.manager();

        int choice = 0;
        boolean isValid = false;
        do{
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
                    break;
                case 9:
                    transactionController.withdraw();
                    break;
                case 10:
                    int Transfer = InputValidator.getInt("would you like to transfer.\n1. Intern. \n2. external");
                    if(Transfer == 1) this.transactionController.transferIntern();
                    else if(Transfer == 2) this.transactionController.transferExternal();
                    else System.out.println("Invalid choice , try again");
                    break;
                case 11:
                    creditController.requestCredit();
                    break;
            case 12:
                accountController.validateCloseAccount();
                break;
            case 13:
                creditController.validateCredit();
                break;
            }
        }
        while(!isValid);


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
                    break;
                case 9:
                    transactionController.withdraw();
                    break;
                case 10:
                    int Transfer = InputValidator.getInt("would you like to transfer.\n1. Intern. \n2. external");
                    if(Transfer == 1) this.transactionController.transferIntern();
                    else if(Transfer == 2) this.transactionController.transferExternal();
                    else System.out.println("Invalid choice , try again");
                    break;
                case 11:
                    creditController.requestCredit();
                    break;
                case 15:
                    this.feeruleController.addFeeRule();
                    break;
                case 16:
                    this.feeruleController.listFeerules();
                    break;
                case 17:
                    this.feeruleController.updateFeeRule();
                    break;
                case 18:
                    this.feeruleController.activeFeeRule();
                    break;
                case 19:
                    this.feeruleController.deactivateFeeRule();
                    break;
                default:
                    System.out.println("Invalid choice ");
                    isValid = false;
                    break;
            }
        }while (!isValid);






    }
}