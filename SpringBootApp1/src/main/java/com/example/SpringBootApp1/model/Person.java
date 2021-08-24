package com.example.SpringBootApp1.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Entity
// @Table(name = "Person")  If it is missing , it will create the table name as Class name.
public class Person
{
    @Id
    String id;
    String firstName;
    String lastName;
}
