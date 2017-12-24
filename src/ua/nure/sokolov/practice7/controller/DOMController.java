package ua.nure.sokolov.practice7.controller;

import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.*;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import ua.nure.sokolov.practice7.XML;
import ua.nure.sokolov.practice7.entity.Author;
import ua.nure.sokolov.practice7.entity.OldCard;
import ua.nure.sokolov.practice7.entity.OldCards;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class DOMController {
    private String xmlFileName;

    private OldCards oldCards;

    public static final String VALID_XML_FILE = "input.xml";
    public static final String INVALID_XML_FILE = "input-invalid.xml";
    public static final String XSD_FILE = "input.xsd";

    public static final String FEATURE_TURN_VALIDATION_ON = "http://xml.org/sax/features/validation";
    public static final String FEATURE_TURN_SCHEMA_VALIDATION_ON = "http://apache.org/xml/features/validation/schema";



    public DOMController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Document document = null;
    public static void main(String[] args){
//        DOMController domController = new DOMController(VALID_XML_FILE);
//        domController.parseXml(true);
//        try {
//            saveToXML(domController.document, "ouptut1.xml");
//        } catch (TransformerException e) {
//            e.printStackTrace();
//        }

        // try to parse NOT valid XML document with validation on (failed)
        DOMController domContr = new DOMController(VALID_XML_FILE);
        // try to parse NOT valid XML document with validation off (success)
        try {
            domContr.parse(true);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }

        // we have Test object at this point:
        System.out.println("====================================");
        System.out.print("Here is the xml: \n" + domContr.getOldCards());
        System.out.println("====================================");

        // save test in XML file
        OldCards test = domContr.getOldCards();
        try {
            DOMController.saveToXML(test, VALID_XML_FILE + ".dom-result.xml");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public OldCards getOldCards(){
        return oldCards;
    }

    public void parseXml(boolean validate){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilderFactory.setNamespaceAware(true);
            // make parser validating
            if (validate) {
                // turn validation on
                documentBuilderFactory.setFeature(FEATURE_TURN_VALIDATION_ON, true);

                // turn on xsd validation
                documentBuilderFactory.setFeature(FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
            }
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(xmlFileName);
            document.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("Корневой элемент: " + document.getDocumentElement().getNodeName());
        NodeList nodeList = document.getElementsByTagName(document.getDocumentElement().getChildNodes().item(1).getNodeName());
        System.out.println("--------------------");
        for(int tmp = 0; tmp < nodeList.getLength(); tmp++)
        {
            Node node = nodeList.item(tmp);
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element)node;
//                System.out.print("OldCard: " + "отправлена - ");
//                if((element.getAttribute("sent")).equals("true")){
//                    System.out.println("да");
//                }else {
//                    System.out.println("нет");
//                }
//                System.out.println("Thema: " + element.getElementsByTagName("Thema").item(0).getChildNodes().item(0).getNodeValue());
//                System.out.println("Type: " + element.getElementsByTagName("Type").item(0).getChildNodes().item(0).getNodeValue());
//                System.out.println("Year: " + element.getElementsByTagName("Year").item(0).getChildNodes().item(0).getNodeValue());
//                System.out.println("Country: " + element.getElementsByTagName("Country").item(0).getChildNodes().item(0).getNodeValue());
//                System.out.println("Author: " + element.getElementsByTagName("Author").item(0).getChildNodes().item(0).getNodeValue());
//                System.out.println("Valuable: " + element.getElementsByTagName("Valuable").item(0).getChildNodes().item(0).getNodeValue());
//                System.out.println("~~~~~~~~~~~~~~~");
            }
        }
    }

//    public void saveToXml(Document document, String xmlFileNameOutput){
//        try
//        {
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            DOMSource domSource = new DOMSource(document);
//            StreamResult streamResult = new StreamResult(new File(xmlFileNameOutput));
//
//            transformer.transform(domSource, streamResult);
//            System.out.println("File was saved");
//        } catch (TransformerException te)
//        {
//            System.out.println(te.getLocalizedMessage());
//            te.printStackTrace();
//        }
//    }

    public void parse(boolean validate)
            throws ParserConfigurationException, SAXException, IOException, org.xml.sax.SAXException {

        // obtain DOM parser
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // set properties for Factory

        // XML document contains namespaces
        dbf.setNamespaceAware(true);

        // make parser validating
        if (validate) {
            // turn validation on
            dbf.setFeature(FEATURE_TURN_VALIDATION_ON, true);

            // turn on xsd validation
            dbf.setFeature(FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
        }

        DocumentBuilder db = dbf.newDocumentBuilder();

        // set error handler
        db.setErrorHandler(new DefaultHandler() {
            @Override
            public void error(SAXParseException e) throws SAXParseException {
                // throw exception if XML document is NOT valid
                throw e;
            }
        });

        // parse XML document
        Document document = db.parse(xmlFileName);

        // get root element
        Element root = document.getDocumentElement();

        // create container
        oldCards = new OldCards();

        // obtain questions nodes
        NodeList questionNodes = root
                .getElementsByTagName(XML.OLDCARD.value());

        // process questions nodes
        for (int j = 0; j < questionNodes.getLength(); j++) {
            OldCard question = getOldCards(questionNodes.item(j));
            // add question to container
            oldCards.getOldCard().add(question);
        }
    }

    private OldCard getOldCards(Node qNode) {
        OldCard question = new OldCard();
        Element qElement = (Element) qNode;

        // process question text
        Node qtNode = qElement.getElementsByTagName(XML.THEMA.value())
                .item(0);
        // set question text
        question.setThema(qtNode.getTextContent());

        Node qtNode1 = qElement.getElementsByTagName(XML.TYPE.value())
                .item(0);
        // set question text
        question.setType(qtNode1.getTextContent());

        Node qtNode2 = qElement.getElementsByTagName(XML.COUNTRY.value())
                .item(0);
        // set question text
        question.setCountry(qtNode2.getTextContent());
        Node qtNode3 = qElement.getElementsByTagName(XML.YEAR.value())
                .item(0);
        // set question text
        question.setYear(qtNode3.getTextContent());

        // process answers
        NodeList aNodeList = qElement.getElementsByTagName(XML.AUTHOR.value());
        for (int j = 0; j < aNodeList.getLength(); j++) {
            Author answer = getAuthor(aNodeList.item(j));

            // add answer
            question.getAuthors().add(answer);
        }

        Node qtNode4 = qElement.getElementsByTagName(XML.VALUABLE.value())
                .item(0);
        // set question text
        question.setValuable(qtNode4.getTextContent());

        return question;
    }

    private Author getAuthor(Node aNode) {
        Author author = new Author();
        Element aElement = (Element) aNode;

        // process correct
        String correct = aElement.getAttribute(XML.EXIST.value());
        author.setExist(Boolean.valueOf(correct));

        // process content
        String content = aElement.getTextContent();
        author.setName(content);

        return author;
    }

    public static Document getDocument(OldCards oldCards) throws ParserConfigurationException {

        // obtain DOM parser
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setNamespaceAware(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();

        Element tElement = document.createElement(XML.OLDCARDS.value());

        // add root element
        document.appendChild(tElement);

        // add questions elements
        for (OldCard oldCard : oldCards.getOldCard()) {

            // add oldCard
            Element qElement = document.createElement(XML.OLDCARD.value());
            tElement.appendChild(qElement);
//            for (OldCard oldCards1 : oldCards.getOldCard()) {
//                Element aElement = document.createElement(XML.OLDCARD.value());
//                aElement.setTextContent(String.valueOf(oldCards1.getSent()));
//
//                // set attribute
//                if (oldCards1.isSent()) {
//                    aElement.setAttribute(XML.SENT.value(), "true");
//                }
//                qElement.appendChild(aElement);
//            }


            // add oldCard text
            Element qtElement =
                    document.createElement(XML.THEMA.value());
            qtElement.setTextContent(oldCard.getThema());
            qElement.appendChild(qtElement);

            Element qtElement1 =
                    document.createElement(XML.TYPE.value());
            qtElement1.setTextContent(oldCard.getType());
            qElement.appendChild(qtElement1);
            Element qtElement2 =
                    document.createElement(XML.COUNTRY.value());
            qtElement2.setTextContent(oldCard.getCountry());
            qElement.appendChild(qtElement2);

            Element qtElement3 =
                    document.createElement(XML.YEAR.value());
            qtElement3.setTextContent(oldCard.getYear());
            qElement.appendChild(qtElement3);

            // add answers
            for (Author author : oldCard.getAuthors()) {
                Element aElement = document.createElement(XML.AUTHOR.value());
                aElement.setTextContent(author.getName());

                // set attribute
                if (author.isExist()) {
                    aElement.setAttribute(XML.EXIST.value(), "true");
                }
                qElement.appendChild(aElement);
            }

            Element qtElement4 =
                    document.createElement(XML.VALUABLE.value());
            qtElement4.setTextContent(oldCard.getValuable());
            qElement.appendChild(qtElement4);
        }

        return document;
    }

    public static void saveToXML(OldCards oldCards, String xmlFileName)
            throws ParserConfigurationException, TransformerException {
        // Test -> DOM -> XML
        saveToXML(getDocument(oldCards), xmlFileName);
    }

    public static void saveToXML(Document document, String xmlFileName)
            throws TransformerException {

        StreamResult result = new StreamResult(new File(xmlFileName));

        // set up transformation
        TransformerFactory tf = TransformerFactory.newInstance();
        javax.xml.transform.Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");

        // run transformation
        t.transform(new DOMSource(document), result);
    }

}
