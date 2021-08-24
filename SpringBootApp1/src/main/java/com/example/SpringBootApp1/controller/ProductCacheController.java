package com.example.SpringBootApp1.controller;

import com.example.SpringBootApp1.model.Product;
import com.example.SpringBootApp1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductCacheController
{
    @Autowired
    ProductService productService;

    @RequestMapping("/productList")
    List<Product> getProducts()
    {
        return productService.getProductList();
    }

    @RequestMapping("/product/{prodid}")
    Product getProductByProdId(@PathVariable int prodid)
    {
        return productService.getProductById(prodid);
    }

    @RequestMapping("/product_delete/{prodid}")
    String deleteProductByProdId(@PathVariable int prodid)
    {
        productService.deleteByProductId(prodid);
        return "Deletion done for " + prodid;
    }
}
