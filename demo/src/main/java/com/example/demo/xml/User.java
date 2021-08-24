package com.example.demo.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;

    @Override
    public String toString() {
        return "\nUser [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", gender=" +
                gender + "]";
    }
}
