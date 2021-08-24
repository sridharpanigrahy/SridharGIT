package com.example.demo.xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdapter extends XmlAdapter<String, LocalDate>
{
    @Override
    public LocalDate unmarshal(String s) throws Exception
    {
        return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception
    {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
