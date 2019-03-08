package com.company;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static boolean loggedIn = false;
    public static Customer currentCustomer = null;

    public static void main(String[] args) {

        //Users to use for testing

        Customer user1 = new Customer(1,"password");
        Customer user2 = new Customer(2,"password");


        Homebank homebank = new Homebank();
        homebank.addCustomer(user1);
        homebank.addCustomer(user2);
        homebank.getCredentials().put(user1.getId(),user1.getPassword());
        homebank.getCredentials().put(user2.getId(),user2.getPassword());

        boolean quit = false;

        while(!quit) {
            if (!loggedIn) {
                System.out.println("Welcome!\n");
                currentCustomer = homebank.login();
                loggedIn = true;
            } else {
                printOptions();
                int input = scanner.nextInt();

                switch (input) {
                    case 1: // open accounts
                        boolean correctCurrency = false;
                        System.out.println("Creating account...\n");
                        while (!correctCurrency) {
                            System.out.println("Choose currency:\n ");

                            int value = 1;

                            for (Currency enume : Currency.values()) {
                                System.out.println(value + ". " + enume);
                                value++;
                            }
                            int currencyInput = scanner.nextInt();
                            if (currencyInput == 1) {
                                homebank.addAccount(currentCustomer, Currency.RON);
                                System.out.println("Account succesfuly created.");
                                correctCurrency = true;
                            } else if (currencyInput == 2) {
                                homebank.addAccount(currentCustomer, Currency.EUR);
                                System.out.println("Account succesfuly created.");
                                correctCurrency = true;
                            } else if (currencyInput == 3) {
                                homebank.addAccount(currentCustomer, Currency.USD);
                                System.out.println("Account succesfuly created.");
                                correctCurrency = true;
                            } else {
                                System.out.println("Choase only available options");
                            }
                        }
                        break;

                    case 2: // deposit
                        if (currentCustomer.getAccounts().isEmpty()) {
                            System.out.println("In order to make a deposit, you must create an account first.");
                        } else {
                            boolean checkDeposit = false;

                            while (!checkDeposit) {
                                System.out.println("Available accounts :");
                                System.out.println();
                                for (Account account : currentCustomer.getAccounts()) {
                                    System.out.println(account.getCurrency() + " account. IBAN = " +
                                            "[" + account.getId() + "] Balance: " + account.printBalance() + " " + account.getCurrency());
                                }
                                System.out.println();
                                System.out.println("Enter Account IBAN: ");
                                int accountID = scanner.nextInt();
                                System.out.println("Enter amount to deposit: ");
                                double amount = scanner.nextDouble();
                                for (Account account : currentCustomer.getAccounts()) {
                                    if (accountID == account.getId()) {
                                        if (account.deposit(amount)) {
                                            System.out.println("Amount succesfully added to account " + account.getId() + ". New balance is " + account.getBalance() + " " + account.getCurrency());
                                        } else {
                                            System.out.println("Deposit not processed. Amount should be positive.");
                                        }

                                        System.out.println();
                                        checkDeposit = true;
                                    }
                                }
                                if (checkDeposit == false) {
                                    System.out.println("Please enter the correct IBAN");
                                }
                            }
                        }
                        break;


                    case 3: //withdraw
                        if (currentCustomer.getAccounts().isEmpty()) {
                            System.out.println("In order to withdraw, you must create an account first.");
                        } else {
                            boolean checkWithdraw = false;
                            while (!checkWithdraw) {
                                System.out.println("Available accounts :");
                                System.out.println();
                                for (Account account : currentCustomer.getAccounts()) {
                                    System.out.println(account.getCurrency() + " account. IBAN = " +
                                            "[" + account.getId() + "] Balance: " + account.printBalance() + " " + account.getCurrency());

                                }
                                System.out.println();
                                System.out.println("Enter Account IBAN: ");
                                int accountID = scanner.nextInt();
                                System.out.println("Enter amount to withdraw: ");
                                double amount = scanner.nextDouble();
                                for (Account account : currentCustomer.getAccounts()) {
                                    if (accountID == account.getId()) {
                                        if (account.withdraw(amount)) {
                                            System.out.println("Amount succesfully withdrawed from account " + account.getId() + ". New balance is " + account.getBalance() + " " + account.getCurrency());
                                        } else {
                                            System.out.println("Insuficent founds. Your tried to withdraw " + amount +
                                                    " " + account.getCurrency() + " but your available balance is "
                                                    + account.getBalance() + " " + account.getCurrency() + ".");
                                        }
                                        System.out.println();
                                        checkWithdraw = true;
                                    }
                                }
                                if (checkWithdraw == false) {
                                    System.out.println("Please enter the correct IBAN");
                                }
                            }
                        }
                        break;

                    case 4: // transfer
                        if (currentCustomer.getAccounts().isEmpty()) {
                            System.out.println("Your account list is empty, please add an account first.");
                        } else {
                            boolean correctTransfer = false;
                            while (!correctTransfer) {
                                System.out.println("Your Accounts:\n");
                                for (Account account : currentCustomer.getAccounts()) {
                                    System.out.println(account.getCurrency() + " account. IBAN = " +
                                            "[" + account.getId() + "] Balance: " + account.printBalance() + " " + account.getCurrency());
                                }
                                System.out.println();
                                System.out.println("From which account do you want to transfer? Enter IBAN:");

                                int firstAccountID = scanner.nextInt();

                                System.out.println("To which account? Enter IBAN:");
                                int secondAccountID = scanner.nextInt();

                                System.out.println("Enter the amount you want to transfer:");

                                double amountToTransfer = scanner.nextDouble();


                                    Customer senderCustomer = homebank.getCustomer(homebank.getAccount(firstAccountID));
                                    Account senderAccount = senderCustomer.getAccount(firstAccountID);

                                   if(currentCustomer.getAccounts().contains(senderAccount)) {

                                    if (senderAccount == null) {
                                        System.out.println("Account does not exist.");
                                    }

                                    Customer receiverCustomer = homebank.getCustomer(homebank.getAccount(secondAccountID));
                                    Account receiverAccount = receiverCustomer.getAccount(secondAccountID);
                                    if (receiverAccount == null) {
                                        System.out.println("Accont does not exist.");
                                    }
                                    if (homebank.transfer(senderAccount, receiverAccount, amountToTransfer)) {
                                        correctTransfer = true;
                                    }
                                }
                                   else{
                                       System.out.println("Account IBAN [" + firstAccountID + "] invalid. Enter a valid account:\n");
                                   }



                            }
                        }
                        break;

                    case 5: //print transactions

                        boolean correctAccount = false;

                        while (!correctAccount) {
                            for (Account account : currentCustomer.getAccounts()) {
                                System.out.println(account.getCurrency() + " account. IBAN = " +
                                        "[" + account.getId() + "] Balance: " + account.printBalance() + " " + account.getCurrency());

                            }
                            System.out.println("Enter Account IBAN: ");
                            int accountID = scanner.nextInt();

                            for (Account account : currentCustomer.getAccounts()) {
                                if (accountID == account.getId()) {
                                    homebank.listTransactions(accountID);
                                    correctAccount = true;
                                }
                            }
                            if (correctAccount == false) {
                                System.out.println("Choase only available options");
                            }
                        }
                        break;

                    case 6: // remove account
                        if (currentCustomer.getAccounts().isEmpty()) {
                            System.out.println("Your account list is empty, please add an account first.");
                        } else {
                            boolean correctRemove = false;
                            while (!correctRemove) {
                                for (Account account : currentCustomer.getAccounts()) {
                                    System.out.println(account.getCurrency() + " account. IBAN = " +
                                            "[" + account.getId() + "] Balance: " + account.printBalance() + " " + account.getCurrency());
                                }
                                System.out.println("Enter Account IBAN: ");
                                int accountID = scanner.nextInt();

                                for (Account account : currentCustomer.getAccounts()) {
                                    if (accountID == account.getId()) {
                                        homebank.removeAccount(accountID, currentCustomer);
                                        System.out.println("Account succesfully deleted.");
                                        correctRemove = true;
                                    }
                                }
                                if (correctRemove == false) {
                                    System.out.println("Choase only available options");
                                }
                            }
                            break;
                        }
                    case 7: //logout
                        System.out.println("Do you want to logout? Y/N");
                        String message = scanner.next();
                        System.out.println();

                        if(message.equalsIgnoreCase("y")){
                            System.out.println("Logged out.");
                            System.out.println();
                            loggedIn = false;

                        } else if(message.equalsIgnoreCase("n")) {
                            printOptions();
                        }
                        break;

                    default:
                        System.out.println("Not an available option");
                        System.out.println(" ");
                        break;

                }
            }
        }
    }

    public static void printOptions(){

        System.out.println();
        System.out.println("***********************************");
        System.out.println("Available Actions");
        System.out.println("1. Open a new Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Print Transactions");
        System.out.println("6. Remove Account");
        System.out.println("7. Log out");
        System.out.println("***********************************");
        System.out.println();
        System.out.println("Select your option:");

    }


}
