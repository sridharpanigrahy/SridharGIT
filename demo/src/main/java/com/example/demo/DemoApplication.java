package com.example.demo;

import com.example.demo.basic.Basic;
import com.example.demo.basic.Emp;
import com.example.demo.basic.Person;
import com.example.demo.collections.CollectionsDemo;
import com.example.demo.generics.GenericsDemo;
import com.example.demo.java8.Java8Demo;
import com.example.demo.threading.TheadingDemo;
import com.example.demo.utils.LambdaUtil;
import com.example.demo.xml.XMLParserDemo;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@Log
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args)
	{
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String...args) throws Exception
	{
		LambdaUtil.PRINTCONSUMER.accept("\nStart of Program.");

		log.info("\nStart of Basic application...");
		Basic basic = new Basic();

		log.info("\nGenerics Demo...");
		GenericsDemo genericsDemo = new GenericsDemo();

		log.info("\nCollections Demo...");
		CollectionsDemo collectionsDemo = new CollectionsDemo();

		log.info("\nStart of Java 8 application...");
		Java8Demo java8Demo = new Java8Demo();

		log.info("\nThreading Demo");
		//TheadingDemo theadingDemo = new TheadingDemo();

		log.info("\nXML Parsing Demo");
		new XMLParserDemo();

		LambdaUtil.PRINTCONSUMER.accept("\n" + LocalDateTime.now() + " - End of Program.");

	}

}
