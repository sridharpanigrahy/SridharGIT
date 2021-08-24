package com.example.SpringBootApp1.service;

import com.example.SpringBootApp1.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService
{
    List<Product> productList;

    @PostConstruct
    void init  ()
    {
        productList = new ArrayList<>();
        List<Product> productListTemp =
        Arrays.asList(
                new Product(1,"PROD1",1000),
                new Product(2,"PROD2",2000),
                new Product(3,"PROD3",3000),
                new Product(4,"PROD4",4000)
        ).stream().collect(Collectors.toList());

        productList.addAll(productListTemp);
        log.info(productList.toString());
    }

    @Cacheable(value = "PRODUCT")    /* Cache addition */
    public List<Product> getProductList()
    {
        log.info(getClass().getName() + " getProductList is called.");
        return productList;
    }

    @CachePut(value = "PRODUCT", key = "#prodId")   /* Cache Put/Update by executing the method ; key value should be same as argument variable. */
    public Product getProductById(int prodId)
    {
        log.info(getClass().getName() + " getProductById is called.");
        return productList.stream().filter(product -> { return product.getProdId()==prodId; } ).findFirst().orElse(null);
    }

    @CacheEvict(value = "PRODUCT", key = "#prodId")   /* Cache Delete by executing the method */
    public int deleteByProductId(int prodId)
    {
        log.info(getClass().getName() + " deleteByProductId is called.");
        productList.removeIf(item -> item.getProdId()==prodId);
        return prodId;
    }
}
