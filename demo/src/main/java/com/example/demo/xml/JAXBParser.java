package com.example.demo.xml;

import com.example.demo.utils.LambdaUtil;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBContextFactory;
import jakarta.xml.bind.Marshaller;
import lombok.Data;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;

@Data
public class JAXBParser
{
    public JAXBParser()
    {
        LambdaUtil.PRINTCONSUMER.accept("\nJAXB Parser Demo...\n\n");
        convertObjectsToXML();
        convertXMLToObjects();

    }

    void convertObjectsToXML()
    {
        try
        {
            // EclipseLink MOXy needs jaxb.properties at the same package with Company.class or Staff.class
            // Alternative, I prefer define this via eclipse JAXBContextFactory manually.
            JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Write into file
            jaxbMarshaller.marshal(createCompanyObject(), new File("company.xml"));
            // Write into console.
            jaxbMarshaller.marshal(createCompanyObject(), System.out);
        }
        catch (Exception ext)
        {
            ext.printStackTrace();
        }
    }

    void convertXMLToObjects()
    {

    }


    private static Company createCompanyObject()
    {
        Company comp = new Company();
        comp.setName("ABCDEFG Enterprise");

        //Staff 1
        Staff o1 = new Staff();
        o1.setId(1);
        o1.setName("ACM1");
        o1.setSalary(8000);
        o1.setBioData("support");
        o1.setJoinDate(LocalDate.now().minusMonths(12));

        // Staff 2
        Staff o2 = new Staff();
        o2.setId(2);
        o2.setName("ACM2");
        o2.setSalary(9000);
        o2.setBioData("developer");
        o2.setJoinDate(LocalDate.now().minusMonths(6));

        comp.setStaffList(Arrays.asList(o1, o2));

        return comp;
    }

}
