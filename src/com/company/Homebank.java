package com.company;

import java.util.*;


/**
 * Created by HV on 04-Mar-19.
 */
public class Homebank {
    private static Homebank instance = null;
    private HashMap<Integer,String> credentials;
    private HashMap<Customer,List<Account>> customerAccounts;
    private ArrayList<Account> accounts;
    private ArrayList<Customer> customers;


    public static Homebank getInstance() {
        if(instance == null){
            instance = new Homebank();
            return instance;
        }
        else{
            return instance;
        }
    }

    public HashMap<Integer, String> getCredentials() {
        return credentials;
    }

    public Homebank(){
        this.credentials = new HashMap<>();
        this.customerAccounts = new HashMap<>();
        this.accounts = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public Customer login(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please Log In\n");
        while(true) {
            System.out.println("Enter your user ID: ");
            int userID = scanner.nextInt();
            if (credentials.containsKey(userID)) {
                System.out.println("Enter your password: ");
                String password = scanner.next();
                if (credentials.get(userID).equals(password)) {
                    System.out.println("Login succesfully.");
                    for(Customer customer : customers){
                        if(customer.getId() == userID){
                            return customer;
                        }
                    }
                } else {
                    System.out.println("Wrong password.");
                }
            } else {
                System.out.println("User ID does not exists.");
            }
        }
    }


    public boolean transfer(Account account1, Account account2,double amount){
        if(accounts.contains(account2)){
            if(account1.getBalance() < amount){
                System.out.println("Insufficent founds");
            }else if((account1.getCurrency() == Currency.RON) && (account2.getCurrency() == Currency.RON) ||
                     (account1.getCurrency() == Currency.EUR) && (account2.getCurrency() == Currency.EUR) ||
                     (account1.getCurrency() == Currency.USD) && (account2.getCurrency() == Currency.USD)){

                double acc1newBalance = account1.getBalance() - amount;
                double acc2newBalance = account2.getBalance() + amount;
                account1.setBalance(acc1newBalance);
                account2.setBalance(acc2newBalance);
                double transferedAmmount = 0 - amount;

                account1.addTransactions(account2,transferedAmmount,account1.getCurrency());
                account2.addTransactions(account1,amount,account2.getCurrency());

                return true;
            } else if((account1.getCurrency() == Currency.RON) && (account2.getCurrency() == Currency.EUR)){
                double rate = 4.7473;

                double acc1newBalance = account1.getBalance() - amount;
                double acc2newBalance = account2.getBalance() + (amount/rate);
                account1.setBalance(acc1newBalance);
                account2.setBalance(acc2newBalance);
                double transferedAmmount = 0 - amount;

                account1.addTransactions(account2,transferedAmmount,account1.getCurrency());
                account2.addTransactions(account1,amount,account2.getCurrency());
                return true;
            } else if((account1.getCurrency() == Currency.RON) && (account2.getCurrency() == Currency.USD)){
                double rate = 4.2010;
                double conversionRate = amount/rate;
                double acc1newBalance = account1.getBalance() - amount;
                double acc2newBalance = account2.getBalance() + conversionRate;
                account1.setBalance(acc1newBalance);
                account2.setBalance(acc2newBalance);
                double transferedAmmount = 0 - amount;

                account1.addTransactions(account2,transferedAmmount,account1.getCurrency());
                account2.addTransactions(account1,amount,account2.getCurrency());
                return true;
            } else if((account1.getCurrency() == Currency.EUR) && (account2.getCurrency() == Currency.RON)){
                double rate = 4.7473;

                double acc1newBalance = account1.getBalance() - amount;
                double acc2newBalance = account2.getBalance() + (amount*rate);
                account1.setBalance(acc1newBalance);
                account2.setBalance(acc2newBalance);
                double transferedAmmount = 0 - amount;

                account1.addTransactions(account2,transferedAmmount,account1.getCurrency());
                account2.addTransactions(account1,amount,account2.getCurrency());
                return true;
            } else if((account1.getCurrency() == Currency.EUR) && (account2.getCurrency() == Currency.USD)){
                double rate = 1.13;

                double acc1newBalance = account1.getBalance() - amount;
                double acc2newBalance = account2.getBalance() + (amount*rate);
                account1.setBalance(acc1newBalance);
                account2.setBalance(acc2newBalance);
                double transferedAmmount = 0 - amount;

                account1.addTransactions(account2,transferedAmmount,account1.getCurrency());
                account2.addTransactions(account1,amount,account2.getCurrency());
                return true;
            } else if((account1.getCurrency() == Currency.USD) && (account2.getCurrency() == Currency.EUR)){
                double rate = 1.13;

                double acc1newBalance = account1.getBalance() - amount;
                double acc2newBalance = account2.getBalance() + (amount/rate);
                account1.setBalance(acc1newBalance);
                account2.setBalance(acc2newBalance);
                double transferedAmmount = 0 - amount;

                account1.addTransactions(account2,transferedAmmount,account1.getCurrency());
                account2.addTransactions(account1,amount,account2.getCurrency());
                return true;
            } else if((account1.getCurrency() == Currency.USD) && (account2.getCurrency() == Currency.RON)){
                double rate = 4.2010;

                double acc1newBalance = account1.getBalance() - amount;
                double acc2newBalance = account2.getBalance() + (amount*rate);
                account1.setBalance(acc1newBalance);
                account2.setBalance(acc2newBalance);
                double transferedAmmount = 0 - amount;

                account1.addTransactions(account2,transferedAmmount,account1.getCurrency());
                account2.addTransactions(account1,amount,account2.getCurrency());
                return true;
            }
        }else{
            System.out.println(account2.getId() + " does not exists. ");
        }

        return false;
    }

    public void addAccount(Customer customer, Currency currency){
        Account newAccount = new Account(Account.counter,currency,50.00);
        customer.addAccount(newAccount);
        if(!customerAccounts.containsKey(customer)){
            customerAccounts.put(customer, new ArrayList<>());
    }
        customerAccounts.get(customer).add(newAccount);
        accounts.add(newAccount);
    }

    public void addCustomer(Customer customerToAdd){
        if(customers.contains(customerToAdd)){
            System.out.println("Customer already exists.");
        } else{
            customers.add(customerToAdd);
        }
    }

    public Account getAccount(int accountId){
        try{
            for(Account account : accounts){
                if(accountId == account.getId()){
                    return account;
                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
        return null;
    }



    public boolean removeAccount(int idAccount , Customer customer){
        for(Account account : accounts){
            if(account.getId() == idAccount)
            {
                customer.removeAccount(account);
                accounts.remove(account);
                customerAccounts.remove(account);

                return true;
            }

        }
        return false;
    }

    public void listTransactions(int idAccount){
        for(Account account : accounts){
            if(account.getId() == idAccount) {
                account.printTransactions();
                break;
            }

        }
    }

    public HashMap<Customer, List<Account>> getCustomerAccounts() {
        return customerAccounts;
    }

    public Customer getCustomer(Account account) {
        for (Map.Entry entry : customerAccounts.entrySet()) {

            if (((List<Account>)entry.getValue()).contains(account)){
                return (Customer) entry.getKey();
            }

        }
        return null;
    }
}
