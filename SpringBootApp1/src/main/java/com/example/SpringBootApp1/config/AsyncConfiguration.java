package com.example.SpringBootApp1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@EnableAsync  /* Enable the Asynchronous */
@Configuration
public class AsyncConfiguration
{
    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsynchThread-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "restTemplate1")
    public RestTemplate restTemplate1()
    {
        return new RestTemplate();
    }

    @Bean(name = "restTemplate2")
    RestTemplate restTemplate2()
    {
        RestTemplate restTemplate = new RestTemplate();
        /*
            RestTemplate uses MessageConverter, and we need to set this property in the RestTemplate bean.
            In our example we are using MappingJacksonHttpMessageConverter for fetching data from JSON format.
         */
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

}
