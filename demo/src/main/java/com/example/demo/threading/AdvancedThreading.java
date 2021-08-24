package com.example.demo.threading;

import com.example.demo.utils.LambdaUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class AdvancedThreading
{
    public AdvancedThreading()
    {
        // Without Synchronized
        Set<Integer> seqSet = Collections.emptySet();
        try
        {
            seqSet = getUniqueSequences(new SequenceGenerator(),100000);
            LambdaUtil.PRINTCONSUMER.accept("\nUniq Sequence Set Size without Synchronized: " + seqSet.size());

            seqSet = getUniqueSequences(new SequenceGeneratorUsingSynchronizedBlock(),100000);
            LambdaUtil.PRINTCONSUMER.accept("\nUniq Sequence Set Size using SequenceGeneratorUsingSynchronizedBlock: " + seqSet.size());

            seqSet = getUniqueSequences(new SequenceGeneratorUsingSynchronizedMethod(),100000);
            LambdaUtil.PRINTCONSUMER.accept("\nUniq Sequence Set Size using SequenceGeneratorUsingSynchronizedMethod: " + seqSet.size());

            seqSet = getUniqueSequences(new SequenceGeneratorUsingReentrantLock(),100000);
            LambdaUtil.PRINTCONSUMER.accept("\nUniq Sequence Set Size using SequenceGeneratorUsingReentrantLock: " + seqSet.size());

            seqSet = getUniqueSequences(new SequenceGeneratorUsingSemaphore(),100000);
            LambdaUtil.PRINTCONSUMER.accept("\nUniq Sequence Set Size using SequenceGeneratorUsingSemaphore: " + seqSet.size());

            seqSet = getUniqueSequencesUsingSingleThread(new SequenceGeneratorUsingSemaphore(),100000);
            LambdaUtil.PRINTCONSUMER.accept("\nUniq Sequence Set Size using SequenceGeneratorUsingSemaphore and Using Single Thread: " + seqSet.size());

            seqSet = getUniqueSequencesUsingCacheThreadPool(new SequenceGeneratorUsingSemaphore(),1000);
            LambdaUtil.PRINTCONSUMER.accept("\nUniq Sequence Set Size using SequenceGeneratorUsingSemaphore and Using Single Thread: " + seqSet.size());

            // Displaying Time using Scheduled Thread.
            Runnable runnable = () -> {
                LambdaUtil.PRINTCONSUMER.accept("\nCurrent Date and Time is :" + LocalDateTime.now());
            };
            getCurrentDateTimeUsingScheduleThreadPool(runnable,100);

        }
        catch (Exception e)
        {

        }
        finally {

        }
    }

    // Using Cache Thread Pool.
    private void getCurrentDateTimeUsingScheduleThreadPool(Runnable runnable, int count) throws Exception
    {
        // Creating a thread pool using Schedule Thread pool.
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        List<Runnable> listOfTasks = new ArrayList<>();
        IntStream.range(1,100).forEach((i) -> listOfTasks.add(runnable));

        executor.schedule(runnable,1,TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(runnable,1,2,TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(runnable,1,5,TimeUnit.SECONDS);

        executor.awaitTermination(1, TimeUnit.SECONDS);  // wait till Threads end.
        executor.shutdown();  // Initiate shutdown for this service.

    }

    // Using Cache Thread Pool.
    private Set<Integer> getUniqueSequencesUsingCacheThreadPool(ISequenceGenerator generator, int count) throws Exception
    {
        // Creating a thread pool using Cache Thread pool.
        ExecutorService executor = Executors.newCachedThreadPool();
        Set<Integer> uniqueSequences = new LinkedHashSet<>();
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            // submit method takes implementation of Callable interface and returns Future object which will have value.
            futures.add(executor.submit(generator::getNextSequence));
        }

        for (Future<Integer> future : futures)
        {
            // Once we call get, the thread will wait for returned result.
            uniqueSequences.add(future.get());
        }

        executor.awaitTermination(1, TimeUnit.SECONDS);  // wait till Threads end.
        executor.shutdown();  // Initiate shutdown for this service.
        return uniqueSequences;
    }

    // Using Single Thread
    private Set<Integer> getUniqueSequencesUsingSingleThread(ISequenceGenerator generator, int count) throws Exception
    {
        // Creating a thread pool using Fixed Thread pool.
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Set<Integer> uniqueSequences = new LinkedHashSet<>();
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            // submit method takes implementation of Callable interface and returns Future object which will have value.
            futures.add(executor.submit(generator::getNextSequence));
        }

        for (Future<Integer> future : futures)
        {
            // Once we call get, the thread will wait for returned result.
            uniqueSequences.add(future.get());
        }

        executor.awaitTermination(1, TimeUnit.SECONDS);  // wait till Threads end.
        executor.shutdown();  // Initiate shutdown for this service.
        return uniqueSequences;
    }
    
    private Set<Integer> getUniqueSequences(ISequenceGenerator generator, int count) throws Exception
    {
        // Creating a thread pool using Fixed Thread pool.
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Set<Integer> uniqueSequences = new LinkedHashSet<>();
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            // submit method takes implementation of Callable interface and returns Future object which will have value.
            futures.add(executor.submit(generator::getNextSequence));
        }

        for (Future<Integer> future : futures)
        {
            // Once we call get, the thread will wait for returned result.
            uniqueSequences.add(future.get());
        }

        executor.awaitTermination(1, TimeUnit.SECONDS);  // wait till Threads end.
        executor.shutdown();  // Initiate shutdown for this service.
        return uniqueSequences;
    }

    /* Normal Sequence Generator Interface */
    interface ISequenceGenerator
    {
        public int getNextSequence();
    }
    /* Normal Sequence Generator Class */
    class SequenceGenerator implements ISequenceGenerator
    {
        private int currentValue = 0;
        public int getNextSequence()
        {
            currentValue = currentValue + 1;
            return currentValue;
        }
    }

    /*
    In a multi-threaded environment, a race condition occurs when two or more threads attempt to update mutable shared data at the same time.
    Java offers a mechanism to avoid race conditions by synchronizing thread access to shared data.
    Every object in Java has an intrinsic lock associated with it.
    The synchronized method and the synchronized block use this intrinsic lock to restrict the access of the critical section to only one thread at a time.
    Therefore, when a thread invokes a synchronized method or enters a synchronized block, it automatically acquires the lock. The lock releases when the method or block completes or an exception is thrown from them.

    synchronized (this)

    Static methods are synchronized on the Class object associated with the class and since only one Class object exists per JVM per class,
    only one thread can execute inside a static synchronized method per class, irrespective of the number of instances it has.
    synchronized (SynchronisedBlocks.class)
     */
    class SequenceGeneratorUsingSynchronizedMethod extends SequenceGenerator
    {
        @Override
        public synchronized int getNextSequence()
        {
            return super.getNextSequence();
        }
    }

    class SequenceGeneratorUsingSynchronizedBlock extends SequenceGenerator {

        private Object mutex = new Object();

        @Override
        public int getNextSequence()
        {
            synchronized (mutex)
            {
                return super.getNextSequence();
            }
        }
    }

    /*
        The ReentrantLock class was introduced in Java 1.5. It provides more flexibility and control than the synchronized keyword approach.
        Lock interface defined inside the java.util.concurrent.lock package and it provides extensive operations for locking.

        Lock API
        void lock() – acquire the lock if it's available; if the lock isn't available a thread gets blocked until the lock is released
        void lockInterruptibly() – this is similar to the lock(), but it allows the blocked thread to be interrupted and resume the execution through a thrown java.lang.InterruptedException
        boolean tryLock() – this is a non-blocking version of lock() method; it attempts to acquire the lock immediately, return true if locking succeeds
        boolean tryLock(long timeout, TimeUnit timeUnit) – this is similar to tryLock(), except it waits up the given timeout before giving up trying to acquire the Lock
        void unlock() – unlocks the Lock instance

        In addition to the Lock interface, we have a ReadWriteLock interface which maintains a pair of locks, one for read-only operations, and one for the write operation. The read lock may be simultaneously held by multiple threads as long as there is no write.

        ReadWriteLock declares methods to acquire read or write locks:
        Lock readLock() – returns the lock that's used for reading
        Lock writeLock() – returns the lock that's used for writing
     */
    class SequenceGeneratorUsingReentrantLock extends SequenceGenerator
    {
        private Lock lock = new ReentrantLock();

        @Override
        public int getNextSequence()
        {
            try
            {
                lock.lock();
                return super.getNextSequence();
            }
            finally
            {
                lock.unlock();
            }
        }
    }
    /*
        Like ReentrantLock, the Semaphore class was also introduced in Java 1.5.
        While in case of a mutex only one thread can access a critical section, Semaphore allows a fixed number of threads to access a critical section.
        Therefore, we can also implement a mutex by setting the number of allowed threads in a Semaphore to one.
     */
    class SequenceGeneratorUsingSemaphore extends SequenceGenerator {

        private Semaphore mutex = new Semaphore(1);

        @Override
        public int getNextSequence()
        {
            try {
                mutex.acquire();
                return super.getNextSequence();
            }
            catch (InterruptedException e)
            {
                // exception handling code
            }
            finally {
                mutex.release();
            }
            return 0;
        }
    }
}


