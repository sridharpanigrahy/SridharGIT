package com.example.demo.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;

import java.time.LocalDate;

@Data
@XmlRootElement(name = "staff")
@XmlAccessorType(XmlAccessType.FIELD) // It tells JAXB only take Staff.id as the property; not to consider getXXX() as attributes.
public class Staff
{
    @XmlAttribute // id will work as attribute.
    int id;

    String name;

    int salary;

    String bioData;

    @XmlJavaTypeAdapter(DateAdapter.class)
    LocalDate joinDate;

}
