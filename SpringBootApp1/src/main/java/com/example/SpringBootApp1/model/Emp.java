package com.example.SpringBootApp1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;
import javax.validation.constraints.*;

@PropertySource(value = "classpath:error.properties", ignoreResourceNotFound = true)
@Data

//Mark class as an Entity
@Entity

// Defining class name as Table name
// Specify the name attribute with table name as the class name and table name both are different.
/*
    Pay extra attention to the query name and the convention we follow:
    @{EntityName}.{queryName}
 */
@Table(name="EMPLOYEES")
@NamedQuery(name = "Emp.fetchByName",
        query = "SELECT e FROM Emp e WHERE fname = :fname and lname = :lname"
)
// @JsonIgnoreProperties({"name", "phone"})  to ignore the field for name and phone in JSON.
public class Emp
{
    @JsonIgnore //JsonIgnore indicates that the annotated method or field is to be ignored

    @Id   // Primary Key
    @GeneratedValue(strategy = GenerationType.AUTO)  /* will be use used for Hibernate Sequence*/
    @Column(name = "id")
    int empId;

    @Column(name = "first_name", nullable = false)
    @NotNull
    @NotEmpty
    @Size(min=2, max=10 ,message = "First Name should be minimum 2 characters amd ma 10 character")
    String fname;

    @Column(name = "last_name", nullable = false)
    @Size(min=2, max=10 ,message = "Last Name should be minimum 2 characters amd max 10 character")
    String lname;

    @Column
    @Min(value = 100, message = "Salary should be greater than 100.")
    Double salary;

    @Column(name = "EMAIL", nullable = false)
    @NotBlank
    @Email(message = "Invalid email id.")
    String emailId;

}
