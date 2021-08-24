package com.example.demo.threading;

import com.example.demo.utils.LambdaUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Java8ThreadingDemo
{
    public Java8ThreadingDemo()
    {
        findSumUsingStream();
        findSumUsingParallelStream();
        findSumUsingParallelStreamAndThread();
    }

    private void findSumUsingStream()
    {
        long startTime = System.currentTimeMillis();
        long firstNum = 1;
        long lastNum = 1_000_000;

        List<Long> longList = LongStream.rangeClosed(firstNum, lastNum).boxed().collect(Collectors.toList());
        long result = longList.stream().reduce(0L,Long::sum).longValue();
        long endTime = System.currentTimeMillis();
        LambdaUtil.PRINTCONSUMER.accept("\nfindSumUsingStream - Sum using Stream is :" + result);

        LambdaUtil.PRINTCONSUMER.accept("\nfindSumUsingStream - Time Taken in Milli : " + (endTime-startTime));
    }

    /*
    The default processing that occurs in such ParallelStream uses the ForkJoinPool.commonPool(), a thread pool shared by the entire application.
    System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");
     */
    private void findSumUsingParallelStream()
    {
        long startTime = System.currentTimeMillis();
        long firstNum = 1;
        long lastNum = 1_000_000;

        List<Long> longList = LongStream.rangeClosed(firstNum, lastNum).boxed().collect(Collectors.toList());
        long result = longList.parallelStream().reduce(0L,Long::sum).longValue();
        long endTime = System.currentTimeMillis();
        LambdaUtil.PRINTCONSUMER.accept("\nfindSumUsingParallelStream - Sum using Parallel Stream is :" + result);

        LambdaUtil.PRINTCONSUMER.accept("\nfindSumUsingParallelStream - Time Taken in Milli : " + (endTime-startTime));
    }

    private void findSumUsingParallelStreamAndThread()
    {
        long startTime = System.currentTimeMillis();
        long firstNum = 1;
        long lastNum = 1_000_000;
        ForkJoinPool customThreadPool = null;
        try
        {
            List<Long> longList = LongStream.rangeClosed(firstNum, lastNum).boxed().collect(Collectors.toList());

            // Find the total no of CPU cores and Create Pool.
            customThreadPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            long result = customThreadPool.submit(() -> longList.parallelStream().reduce(0L,Long::sum).longValue()).get();

            long endTime = System.currentTimeMillis();
            LambdaUtil.PRINTCONSUMER.accept("\nfindSumUsingParallelStreamAndThread - Sum using Parallel Stream and ForkJoinPool is :" + result);
            LambdaUtil.PRINTCONSUMER.accept("\nfindSumUsingParallelStreamAndThread - Time Taken in Milli : " + (endTime-startTime));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally {
            customThreadPool.shutdown();
        }
    }
}
