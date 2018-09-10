package com.company;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilmMain {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newDefaultInstance();
        spf.setNamespaceAware(true);
        SAXParser saxparser = spf.newSAXParser();
        List<Film> films = new ArrayList<>();
        saxparser.parse("Film.xml", new MyContentHandler(films));
        films.forEach(System.out::println);

    }

}
enum Genre {Sci_Fi, Weird };
class Film{
    private String titel;
    private int jaar;
    private Genre genre;
    public Film() {
        this.titel = titel;
        this.jaar = jaar;
        this.genre = genre;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }



    public String getTitel() {
        return titel;
    }

    public int getJaar() {
        return jaar;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Film{" +
                "titel='" + titel + '\'' +
                ", jaar=" + jaar +
                ", genre='" + genre + '\'' +
                '}';
    }

    public void setGenre (Genre genre) {
        this.genre = genre;
    }
}

class MyContentHandler extends DefaultHandler {
    private StringBuilder tekstBuilder = new StringBuilder();
    private List<Film> films;
    private Film film;

    public MyContentHandler(List<Film> films) {
        this.films = films;

    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tekstBuilder.append(ch, start, length);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equals("film")) {
            film = new Film();
            String genreString = attributes.getValue("genre");
            switch (genreString) {
                case "Sci-Fi":
                    film.setGenre(Genre.Sci_Fi);
                    break;
                case "weird":
                    film.setGenre(Genre.Weird);
                    break;
            }
        }
        tekstBuilder.setLength(0);
    }

            @Override
            public void endElement (String uri, String localName, String qName) throws SAXException {
                if (localName.equals("film")){
                    films.add(film);
                if (localName.equals("titel")) {
                    film.setTitel(tekstBuilder.toString().trim());
                } else if (localName.equals("jaar")) {
                    film.setJaar(Integer.parseInt(tekstBuilder.toString().trim()));
                } //else if (localName.equals("genre")) {
                    //film.setGenre(tekstBuilder.toString().trim());

                //}

                }
                tekstBuilder.setLength(0);
            }

            @Override
            public void error (SAXParseException e) throws SAXException {
                throw e;
            }


        }

