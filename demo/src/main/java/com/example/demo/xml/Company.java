package com.example.demo.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import java.nio.file.Paths;
import java.util.List;


@Data
@XmlRootElement(name = "company")
@XmlType(propOrder = {"name", "staffList"})
@XmlAccessorType(XmlAccessType.FIELD) // To fix the below issue, we have added this statement.
/*
org.glassfish.jaxb.runtime.v2.runtime.IllegalAnnotationsException: 2 counts of IllegalAnnotationExceptions
There are two properties named "staffList"
	this problem is related to the following location:
		at public java.util.List com.example.demo.xml.Company.getStaffList()
		at com.example.demo.xml.Company

 */
public class Company
{
    @XmlElement(name = "staff")
    List<Staff> staffList;

    String name;

}
