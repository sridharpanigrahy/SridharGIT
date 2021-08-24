package com.example.demo.threading;

import java.util.concurrent.BlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.lang.System.out;

public class TheadingDemo
{
    public TheadingDemo()
    {
        BasicThreading basicThreading = new BasicThreading();
        BlockingQueueDemo blockingQueueDemo = new BlockingQueueDemo();
        AdvancedThreading advancedThreading = new AdvancedThreading();
        Java8ThreadingDemo java8ThreadingDemo = new Java8ThreadingDemo();
    }
}
