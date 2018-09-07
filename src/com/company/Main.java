package com.company;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        // write your code here
        SchemaFactory schemaFactory = SchemaFactory.newDefaultInstance();
        Schema schema = schemaFactory.newSchema(new StreamSource("boeken.xsd"));
        SAXParserFactory spf = SAXParserFactory.newDefaultInstance();
        spf.setSchema(schema);
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        List<Boek> boeken = new ArrayList<>();
        saxParser.parse("boeken.xml", new MyContenthandler(boeken));
        System.out.println("De boeken: ");
        boeken.forEach(System.out::println);
        }
    }
class Boek{
    private String titel;
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    @Override
    public String toString() {
        return "Boek{" +
                "titel='" + titel + '\'' +
                '}';
    }
}

class MyContenthandler extends DefaultHandler {
    private StringBuilder tekstBuilder = new StringBuilder();
    private List<Boek> boeken;
    private Boek boek;
    public MyContenthandler(List<Boek> boeken){
        this.boeken=boeken;
    }

    @Override
    public void startDocument() throws SAXException {
      //  System.out.println("Begin van het document");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equals("boek")){
            boek=new Boek();
        }
        System.out.printf("Begin element <%s>%n", localName);
    }

   @Override
    public void endDocument() throws SAXException {
     //   System.out.println("Einde van het document");
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tekstBuilder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("titel")){
            boek.setTitel(tekstBuilder.toString());

        } else if (localName.equals("boek"))
            boeken.add(boek);
        tekstBuilder.setLength(0); // terug leegmaken
    }
}