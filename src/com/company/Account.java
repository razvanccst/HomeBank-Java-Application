package com.company;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Account {
   private int id;
   private Currency currency;
   private double balance;
   private ArrayList<String> transactions;
   public static int counter = 1;


    public Account(int id, Currency currency,double initialAmount) {
        this.id = id;
        this.currency = currency;
        this.balance = initialAmount;
        this.transactions = new ArrayList<>();
        counter++;
    }

    public boolean deposit(double amount){
        if(amount < 0){
            return false;
        } else{
            this.balance+= amount;
            String mesage = "Amount " + amount + " deposited in your account.";
            this.transactions.add(mesage);
        }
    return true;
    }

    public boolean withdraw(double amount){
        if(amount > this.balance) {
            return false;
        }else if(amount < 0){
            return false;
        }else{
            this.balance = this.balance - amount;
            String mesage = "Amount " + amount + " withdrawed from your account.";
            this.transactions.add(mesage);
            return true;
        }
    }

    public void addTransactions(Account account,double amount,Currency currecy){
        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourmilliseconds);

        DecimalFormat numberFormat = new DecimalFormat("#.00");

        if(amount <0 ){

            String message = sdf.format(resultdate)+" : "+ "Amount of " + numberFormat.format(-amount) + " " + currecy + " transfered to IBAN[" + account.getId()+"]";
            this.transactions.add(message);
        }else{
            String message = sdf.format(resultdate)+" : "+  "Amount of " + numberFormat.format(amount) + " " + currecy + " received from IBAN[" + account.getId() +"]";
            this.transactions.add(message);
        }

    }

    public void printTransactions(){
        int count = 0;
        try {
            for (String transaction : transactions) {
                count++;
                System.out.println(count+". " +transaction);
            }

            if(transactions.isEmpty()){
                System.out.println("You have no transactions for this account ");
            }

        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }



    public int getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }


    public double getBalance() {
        return balance;
    }

    public String printBalance() {
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        return numberFormat.format(balance);
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
