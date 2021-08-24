package com.example.demo.threading;

import com.example.demo.basic.Basic;
import com.example.demo.utils.LambdaUtil;
import lombok.Data;

import java.util.stream.IntStream;


@Data
public class BasicThreading
{
    private Account account;
    BasicThreading()
    {
        account = new Account(1,"ABC",200);
        startTransactions();
    }

    private void startTransactions()
    {
        Runnable debitRunnable = () -> {
            IntStream.range(1,10).forEach(
                    i ->
                    {
                        account.makeTransaction("D",20);
                        LambdaUtil.PRINTCONSUMER.accept("\n" + Thread.currentThread().getName() + " - " + account.toString());
                        try {
                            Thread.sleep(1);
                        }
                        catch (Exception e) {}

                    }
            );
        };

        Runnable creditRunnable = () -> {
            IntStream.range(1,10).forEach(
                    i ->
                    {
                        account.makeTransaction("C",20);
                        LambdaUtil.PRINTCONSUMER.accept("\n" + Thread.currentThread().getName() + " - " + account.toString());
                        try {
                            Thread.sleep(1);
                        }
                        catch (Exception e) {}
                    }
            );
        };

        Thread debitThread = new Thread(debitRunnable);
        Thread creditThread = new Thread(creditRunnable);
        debitThread.setName("DebitThread");
        creditThread.setName("CreditThread");
        creditThread.setPriority(Thread.MAX_PRIORITY);
        debitThread.start();
        creditThread.start();

        try {
            debitThread.join();
            debitThread.join();
        } catch (Exception e) {
        }


    }



}
