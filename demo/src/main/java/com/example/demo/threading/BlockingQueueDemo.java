package com.example.demo.threading;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/*
    Before Java 5, the producer-consumer problem can be solved using wait() and
    notify() methods but the introduction of BlockingQueue has made it very easy.

    java.util.concurrent.BlockingQueue is a java Queue that support operations that wait for the queue to become non-empty
    when retrieving and removing an element, and wait for space to become available in the queue when adding an element.

    Java BlockingQueue
    Java BlockingQueue doesn’t accept null values and throw NullPointerException if you try to store null value in the queue.
    Java BlockingQueue implementations are thread-safe. All queuing methods are atomic in nature and use internal locks or other forms of concurrency control.
    Java BlockingQueue interface is part of java collections framework and it’s primarily used for implementing producer consumer problem.
    We don’t need to worry about waiting for the space to be available for producer or object to be available for consumer in BlockingQueue
    because it’s handled by implementation classes of BlockingQueue.
    Java provides several BlockingQueue implementations such as ArrayBlockingQueue, LinkedBlockingQueue, PriorityBlockingQueue, SynchronousQueue etc.
    While implementing producer consumer problem in BlockingQueue, we will use ArrayBlockingQueue implementation.

    put(E e): This method is used to insert elements to the queue. If the queue is full, it waits for the space to be available.
    E take(): This method retrieves and remove the element from the head of the queue. If queue is empty it waits for the element to be available.
 */
public class BlockingQueueDemo
{
    public BlockingQueueDemo()
    {
        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(10);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        //starting producer to produce messages in queue
        Thread t1 = new Thread(producer);
        t1.start();
        //starting consumer to consume messages from queue
        Thread t2 = new Thread(consumer);
        t2.start();
        try
        {
            t1.join();  // Wait till the thread completes
            t2.join();
        }
        catch (Exception exception)
        {

        }

        System.out.println("\nProducer and Consumer has been started");
    }

      class Message
      {
          private String msg;

          public Message(String str)
          {
              this.msg = str;
          }

          public String getMsg()
          {
              return msg;
          }
      }

     class Producer implements Runnable
     {
         private BlockingQueue<Message> queue;
         public Producer(BlockingQueue<Message> q)
         {
             this.queue = q;
         }

         @Override
         public void run()
         {
             //produce messages
             for (int i = 0; i < 100; i++) {
                 Message msg = new Message("" + i);
                 try {
                     //Thread.sleep(1);
                     TimeUnit.MILLISECONDS.sleep(1);
                     queue.put(msg);
                     System.out.println( LocalDateTime.now() + " Produced " + msg.getMsg());
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
             //adding exit message
             Message msg = new Message("exit");
             try {
                 queue.put(msg);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }

    public class Consumer implements Runnable
    {
        private BlockingQueue<Message> queue;

        public Consumer(BlockingQueue<Message> q)
        {
            this.queue = q;
        }

        @Override
        public void run()
        {
            try {
                Message msg;
                //consuming messages until exit message is received
                while ((msg = queue.take()).getMsg() != "exit") {
                    Thread.sleep(10);
                    System.out.println(LocalDateTime.now() + " Consumed " + msg.getMsg());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
