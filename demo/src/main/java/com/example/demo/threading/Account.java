package com.example.demo.threading;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Account
{
    private int accountId;
    private String custName;

    // Using volatile keyword with variables to make every thread read the data from memory, not read from thread cache.
    volatile private double accountBalance;

    private Object mutex = new Object();

    public Account(int accountId,String custName,double accountBalance)
    {
        this.accountId = accountId;
        this.custName = custName;
        this.accountBalance = accountBalance;
    }

    public boolean makeTransaction(String transactionType, double transactionAmount)
    {
        boolean result = false;
        Objects.requireNonNull(transactionType);

        /*
        Here, we can pass as this to synchronized; but that will block all the fields of this object. So, using mutex.
         */
        synchronized (mutex)
        {
            result =  transactionType.equals("D")?this.debit(transactionAmount):this .credit(transactionAmount);
        }
        return result;
    }

    private boolean debit(double amount)
    {
        if(accountBalance> 0 && accountBalance > amount)
        {
            accountBalance = accountBalance - amount;
            return true;
        }
        else
        {
            return  false;
        }
    }

    private boolean credit(double amount)
    {
        if(amount > 0)
        {
            accountBalance = accountBalance + amount;
            return true;
        }
        else
        {
            return  false;
        }
    }
    @Override
    public String toString()
    {
        return String.format("Account Id: %s Name: %s Account Balance: %s", accountId, custName, accountBalance);
    }
}
