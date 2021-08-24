package com.example.demo.xml;

import com.example.demo.utils.LambdaUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/*
    DOM XML parser parses the entire XML document and loads it into memory; then models it in a “TREE” structure for easy traversal or manipulation.
    DOM Parser is slow and consumes a lot of memory when it loads an XML document which contains a lot of data.
    Please consider SAX parser as a solution for it, SAX is faster than DOM and use less memory.
 */
@Log4j2
public class DOMParserDemo
{

    public void domDemo()
    {
        parseXMLFileUsingDOM();
        createXMLFileUsingDOM();

    }

    private void parseXMLFileUsingDOM()
    {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Optional<File> optionalFile = Optional.ofNullable(ResourceUtils.getFile("classpath:User.xml"));
            if(optionalFile.isPresent())
            {
                Document document = documentBuilder.parse(optionalFile.get());
                document.getDocumentElement().normalize();
                LambdaUtil.PRINTCONSUMER.accept("\nRoot Element Name is :" + document.getDocumentElement().getNodeName());
                NodeList nodeList = document.getElementsByTagName("User");
                LambdaUtil.PRINTCONSUMER.accept("\nUser element count is :" + nodeList.getLength());
                LambdaUtil.PRINTCONSUMER.accept("\nUser Details for XML File are :");
                List<User> userList = new ArrayList<>();
                IntStream.range(0,nodeList.getLength()).forEach(
                        index ->
                        {
                            userList.add(getUser(nodeList.item(index)));
                        }
                );

                userList.forEach(LambdaUtil.PRINTCONSUMER);
            }
        }
        catch (Exception exception)
        {
            log.error(exception.getStackTrace());
        }
    }

    private User getUser(Node node)
    {
        User user = new User();
        if (node.getNodeType() == Node.ELEMENT_NODE)
        {
            Element element = (Element) node;
            user.setId(Integer.parseInt(getTagValue("id", element)));
            user.setFirstName(getTagValue("firstName", element));
            user.setLastName(getTagValue("lastName", element));
            user.setGender(getTagValue("gender", element));
            user.setAge(Integer.parseInt(getTagValue("age", element)));
        }
        return user;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    private void createXMLFileUsingDOM()
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            Document document = documentBuilderFactory.newDocumentBuilder().newDocument();

            // Creating the root element
            Element rootElement = document.createElement("Users");
            document.appendChild(rootElement);

            // Creating Child User Element for Users
            rootElement.appendChild(createUserElement(document,"1","ABC1","XYZ1",10,"M"));
            rootElement.appendChild(createUserElement(document,"2","ABC2","XYZ2",20,"M"));
            rootElement.appendChild(createUserElement(document,"3","ABC3","XYZ3",20,"M"));

            // For output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // For pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult();
            streamResult.setOutputStream(System.out);
            /*
                If you want to write into file.
                StreamResult fileStreamResult = new StreamResult(new File("create_users.xml"));
                transformer.transform(domSource,fileStreamResult);
             */
            transformer.transform(domSource,streamResult);

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private Node createUserElement(Document doc, String id, String firstName, String lastName, int age, String gender)
    {
        Element user = doc.createElement("User");

        // set id attribute
        user.setAttribute("id", id);

        // create firstName element
        user.appendChild(createUserChildElement(doc, user, "firstName", firstName));

        // create lastName element
        user.appendChild(createUserChildElement(doc, user, "lastName", lastName));

        // create age element
        user.appendChild(createUserChildElement(doc, user, "age", String.valueOf(age)));

        // create gender element
        user.appendChild(createUserChildElement(doc, user, "gender", gender));
        return user;
    }

    // utility method to create child node
    private static Node createUserChildElement(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
