package ua.nure.sokolov.practice7.controller;

import jdk.internal.org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ua.nure.sokolov.practice7.XML;
import ua.nure.sokolov.practice7.entity.Author;
import ua.nure.sokolov.practice7.entity.OldCard;
import ua.nure.sokolov.practice7.entity.OldCards;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

public class STAXController extends DefaultHandler {

    private String xmlFileName;

    // main container
    private OldCards oldCards;

    public static final String VALID_XML_FILE = "input.xml";

    public OldCards getTest() {
        return oldCards;
    }

    public STAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    /**
     * Parses XML document with StAX (based on event reader). There is no validation during the
     * parsing.
     */
    public void parse() throws ParserConfigurationException, SAXException,
            IOException, XMLStreamException {

        OldCard oldCard = null;
        Author author = null;

        // current element name holder
        String currentElement = null;

        XMLInputFactory factory = XMLInputFactory.newInstance();

        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);

        XMLEventReader reader = factory.createXMLEventReader(
                new StreamSource(xmlFileName));

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            // skip any empty content
            if (event.isCharacters() && event.asCharacters().isWhiteSpace()) {
                continue;
            }

            // handler for start tags
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                currentElement = startElement.getName().getLocalPart();

                if (XML.OLDCARDS.equalsTo(currentElement)) {
                    oldCards = new OldCards();
                    continue;
                }

                if (XML.OLDCARD.equalsTo(currentElement)) {
                    oldCard = new OldCard();
                    continue;
                }

                if (XML.AUTHOR.equalsTo(currentElement)) {
                    author = new Author();
                    Attribute attribute = startElement.getAttributeByName(
                            new QName(XML.EXIST.value()));
                    if (attribute != null) {
                        author.setExist(Boolean.parseBoolean(attribute.getValue()));
                    }
                }
            }

            // handler for contents
            if (event.isCharacters()) {
                Characters characters = event.asCharacters();

                if (XML.THEMA.equalsTo(currentElement)) {
                    oldCard.setThema(characters.getData());
                    continue;
                }

                if (XML.TYPE.equalsTo(currentElement)) {
                    oldCard.setType(characters.getData());
                    continue;
                }

                if (XML.COUNTRY.equalsTo(currentElement)) {
                    oldCard.setCountry(characters.getData());
                    continue;
                }

                if (XML.YEAR.equalsTo(currentElement)) {
                    oldCard.setYear(characters.getData());
                    continue;
                }

                if (XML.AUTHOR.equalsTo(currentElement)) {
                    author.setName(characters.getData());
                    continue;
                }

                if (XML.VALUABLE.equalsTo(currentElement)) {
                    oldCard.setValuable(characters.getData());
                    continue;
                }
            }

            // handler for end tags
            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                String localName = endElement.getName().getLocalPart();

                if (XML.OLDCARD.equalsTo(localName)) {
                    oldCards.getOldCard().add(oldCard);
                    continue;
                }

                if (XML.THEMA.equalsTo(localName)) {
                    oldCard.getThema();
                }
                if (XML.TYPE.equalsTo(localName)) {
                    oldCard.getType();
                }
                if (XML.COUNTRY.equalsTo(localName)) {
                    oldCard.getCountry();
                }
                if (XML.YEAR.equalsTo(localName)) {
                    oldCard.getYear();
                }

                if (XML.AUTHOR.equalsTo(localName)) {
                    oldCard.getAuthors().add(author);
                }
                if (XML.VALUABLE.equalsTo(localName)) {
                    oldCard.getValuable();
                }
            }
        }
        reader.close();
    }

    public static void main(String[] args) throws Exception {

        // try to parse (valid) XML file (success)
        STAXController staxContr = new STAXController(VALID_XML_FILE);
        staxContr.parse(); // <-- do parse (success)

        // obtain container
        OldCards oldCards = staxContr.getTest();

        // we have Test object at this point:
        System.out.println("====================================");
        System.out.print("Here is the test: \n" + oldCards);
        System.out.println("====================================");
    }
}
