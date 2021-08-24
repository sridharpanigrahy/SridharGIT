package com.example.SpringBootApp1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product
{
    int prodId;
    String prodName;
    double price;
}
