package com.example.demo.utils;

import java.time.LocalDateTime;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.lang.System.out;

public class LambdaUtil
{
    final public static Consumer PRINTCONSUMER = s -> out.print(s);
    final public static BiConsumer PRINTBICONSUMER = (s1, s2) -> out.println(LocalDateTime.now().toString() + s1 + " " + s2);
}
