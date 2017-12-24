package ua.nure.sokolov.practice7.controller;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.nure.sokolov.practice7.XML;
import ua.nure.sokolov.practice7.entity.Author;
import ua.nure.sokolov.practice7.entity.OldCard;
import ua.nure.sokolov.practice7.entity.OldCards;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

public class SAXController extends DefaultHandler{

    private String xmlFileName;

    private String currentElement;


    private Author author;

    private OldCards oldCards;

    private OldCard oldCard;

    public static final String FEATURE_TURN_VALIDATION_ON = "http://xml.org/sax/features/validation";
    public static final String FEATURE_TURN_SCHEMA_VALIDATION_ON = "http://apache.org/xml/features/validation/schema";


    public static void main(String[] args) {
        SAXController saxController = new SAXController("input.xml");
        try {
            saxController.parse(true);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public SAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public OldCards getOldCards(){
        return oldCards;
    }

    @Override
    public void error(org.xml.sax.SAXParseException e) throws SAXException {
        // if XML document not valid just throw exception
        throw e;
    };

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        currentElement = localName;

        if (XML.OLDCARDS.equalsTo(currentElement)) {
            oldCards = new OldCards();
            return;
        }

        if (XML.OLDCARD.equalsTo(currentElement)) {
            oldCard = new OldCard();

            return;
        }

        if (XML.AUTHOR.equalsTo(currentElement)) {
            author = new Author();
            if (attributes.getLength() > 0) {
                author.setExist(Boolean.parseBoolean(attributes.getValue(uri,
                        XML.EXIST.value())));
            }
        }

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        String elementText = new String(ch, start, length).trim();

        if (elementText.isEmpty()) {
            return;
        }

        if (XML.THEMA.equalsTo(currentElement)) {
            oldCard.setThema(elementText);
            return;
        }
        if (XML.TYPE.equalsTo(currentElement)) {
            oldCard.setType(elementText);
            return;
        }
        if (XML.COUNTRY.equalsTo(currentElement)) {
            oldCard.setCountry(elementText);
            return;
        }
        if (XML.YEAR.equalsTo(currentElement)) {
            oldCard.setYear(elementText);
            return;
        }

        if (XML.AUTHOR.equalsTo(currentElement)) {
            author.setName(elementText);
            return;
        }

        if (XML.VALUABLE.equalsTo(currentElement)) {
            oldCard.setValuable(elementText);
            return;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (XML.OLDCARD.equalsTo(localName)) {
            // just add question to container
            oldCards.getOldCard().add(oldCard);
            return;
        }
        if (XML.AUTHOR.equalsTo(localName)) {
            // just add answer to container
            oldCard.getAuthors().add(author);
            return;
        }
    }



    public void parse(boolean validate)
            throws ParserConfigurationException, SAXException, IOException {

        // obtain sax parser factory
        SAXParserFactory factory = SAXParserFactory.newInstance();

        // XML document contains namespaces
        factory.setNamespaceAware(true);

        // set validation
        if (validate) {
            factory.setFeature(FEATURE_TURN_VALIDATION_ON, true);
            factory.setFeature(FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
        }

        SAXParser parser = factory.newSAXParser();
        parser.parse(xmlFileName, this);
    }
}
