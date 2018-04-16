package Bank.application;

import Bank.model.Accounts;
import Bank.model.User;
import Bank.service.UserService;

import java.util.Scanner;
import java.util.*;

/**
 *
 * Below are the core ones (everyone MUST accomplish them)
 -account creation (admin and user role)
 --admins must approve users before they can be used
 -transactions, must support withdrawals and deposits
 -must use logging
 -everything must NOT be in the main method.
 -data must be persisted
 -collect data inputs from console
 */

public class Application {

    public static void main(String[] args){

        menu();

    }

    public static void menu(){

        int choice;

        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Register admin");
            System.out.println("4. Admin Login");
            System.out.println("5. Quit");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    registerAdmin();
                    break;
                case 4:
                    adminLogin();
                    break;
                case 5:
                    System.exit(0);
            }
        }



    }
    public static void registerAdmin(){
        String username,password;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a username.");
        username = sc.next();
        System.out.println("Enter a password.");
        password = sc.next();
        //test if username is taken already
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAdmin(true);

        if(UserService.insertUser(user)){
            System.out.println("Account was successfully created.");
        }
        else{
            System.out.println("Account was not created.");
        }
    }
    public static void adminLogin(){
        String username,password;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a Username");
        username = sc.next();
        System.out.println("Enter a Password");
        password = sc.next();

        User user = UserService.getUser(username, password);
        if(user == null){
            System.out.println("Could not find admin user.");
            menu();
        }
        if(user.getAdmin()){}

        int choice;

        while(true) {
            System.out.println("Welcome, " + user.getUserName() + ".");
            System.out.println("\n1. View users.");
            System.out.println("2. Approve user.");
            System.out.println("3. Delete user");
            System.out.println("6. Logout");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    createAccount(user);
                    break;
                case 2:
                    deleteAccount();
                    break;
                case 3:
                    withdraw(user);
                    break;
                case 4:
                    deposit(user);
                    break;
                case 5:
                    viewAccounts(user);
                    break;
                case 6:
                    //user = null;
                    //username = null;
                    //password = null;
                    System.out.println("Logged out.");
                    menu();

            }
        }

    }
    public static void login(){
        String username,password;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a Username");
        username = sc.next();
        System.out.println("Enter a Password");
        password = sc.next();

        User user = UserService.getUser(username, password);
        if(user == null){
            System.out.println("Could not find user.");
            menu();
        }

        int choice;

        while(true) {
            System.out.println("Welcome, " + user.getUserName() + ".");
            System.out.println("\n1. Create account");
            System.out.println("2. Delete account");
            System.out.println("3. Withdraw");
            System.out.println("4. Deposit");
            System.out.println("5. View accounts");
            System.out.println("6. Logout");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    createAccount(user);
                    break;
                case 2:
                    deleteAccount();
                    break;
                case 3:
                    withdraw(user);
                    break;
                case 4:
                    deposit(user);
                    break;
                case 5:
                    viewAccounts(user);
                    break;
                case 6:
                    //user = null;
                    //username = null;
                    //password = null;
                    System.out.println("Logged out.");
                    menu();

            }
        }

    }
    public static void viewAccounts(User user){

        List<Accounts> accounts = UserService.getAllAccounts(user.getUserID());
        if(accounts == null){
            System.out.println("Could not get accounts.");

        }
        else {
            System.out.println("\nAccount ID             Total Amount");
            System.out.println("-----------------------------------");
            for (Accounts account : accounts) {
                System.out.println(account.getAccountID() + "                          $" + account.getMoneyAmount() + "\n");
            }
        }

    }
    public static void createAccount(User user){
        if(UserService.insertAccount(user.getUserID())){
            System.out.println("New account created for " + user.getUserName());
        }
        else{
            System.out.println("Account not created");
        }

    }
    public static void deleteAccount(){}
    public static void register(){
        String username,password;
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a username.");
        username = sc.next();
        System.out.println("Enter a password.");
        password = sc.next();
        //test if username is taken already
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        if(UserService.insertUser(user)){
            System.out.println("Account was successfully created.");
        }
        else{
            System.out.println("Account was not created.");
        }

    }

    public static void withdraw(User user){
        double withdrawAmount;
        double newAmount;
        int accountNum;
        List<Accounts> accounts = UserService.getAllAccounts(user.getUserID());
        Accounts accountSelected = new Accounts();
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter the ID of the account you want to withdraw from: ");
        accountNum = sc.nextInt();

        for(Accounts account : accounts){
            if(account.getAccountID() == accountNum){
                accountSelected = account;
            }
        }
        if(accountSelected == null){
            System.out.println("Account not found");
            withdraw(user);
        }

        System.out.print("\nHow much do you want to withdraw: ");
        withdrawAmount = sc.nextDouble();
        newAmount = accountSelected.getMoneyAmount() - withdrawAmount;
        if(newAmount >= 0 && withdrawAmount >= 0){
            if(UserService.updateAccount(accountNum,newAmount)){
                System.out.println("Withdraw successful.");
            }
            else{
                System.out.println("Withdraw not successful.");

            }
        }

    }

    public static void deposit(User user){
        double depositAmount;
        double newAmount;
        int accountNum;
        List<Accounts> accounts = UserService.getAllAccounts(user.getUserID());
        Accounts accountSelected = new Accounts();
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter the ID of the account you want to deposit to: ");
        accountNum = sc.nextInt();

        for(Accounts account : accounts){
            if(account.getAccountID() == accountNum){
                accountSelected = account;
            }
        }
        if(accountSelected == null){
            System.out.println("Account not found");
            withdraw(user);
        }

        System.out.print("\nHow much do you want to deposit: ");
        depositAmount = sc.nextDouble();
        if(depositAmount >= 0){
        newAmount = accountSelected.getMoneyAmount() + depositAmount;
            if(UserService.updateAccount(accountNum,newAmount)){
                System.out.println("Deposit successful.");
            }
            else{
                System.out.println("Deposit not successful.");

            }
        }

    }
}
