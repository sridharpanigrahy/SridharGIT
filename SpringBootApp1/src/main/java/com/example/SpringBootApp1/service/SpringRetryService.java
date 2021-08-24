package com.example.SpringBootApp1.service;

import com.example.SpringBootApp1.exception.RecordNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@EnableRetry
@Service
public class SpringRetryService
{
    static long var1 = 1;
    static long var2 = 1;

    @Retryable(maxAttempts = 6,                         // Maximum attempt to retry is 6
               value = RecordNotFoundException.class,   // Retry when the exception is RecordNotFound.class
               backoff = @Backoff(value = 3))           // Before next retry, wait for 3 milliseconds.
   public long getNumberDividedByTen1()
    {
        log.info("SpringRetryService::getNumberDividedByTen1 is invoked with var1 value as : " + var1);
        var1++;
        if(var1 % 10 != 0)
            throw new RecordNotFoundException("From getNumberDividedByTen1 - Not a number 10 with divisible with 10");
        return var1;
    }

    @Recover  /* This will be invoked after max attempt retry. */
    long getNumberDividedByTenRecover()
    {
        log.info("Returning the default number from getNumberDividedByTenRecover...");
        return 100L;
    }

    @Retryable(maxAttempts = 15,                         // Maximum attempt to retry is 6
               value = RecordNotFoundException.class,   // Retry when the exception is RecordNotFound.class
               backoff = @Backoff(value = 3))           // Before next retry, wait for 3 milliseconds.
    public long getNumberDividedByTen2()
    {
        log.info("SpringRetryService::getNumberDividedByTen2 is invoked with var2 value as : " + var2);
        var2++;
        if(var2 % 10 != 0)
            throw new RecordNotFoundException("From getNumberDividedByTen2 - Not a number 10 with divisible with 10");
        return var2;
    }
}
