package ua.nure.sokolov.practice7;

import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import ua.nure.sokolov.practice7.controller.DOMController;
import ua.nure.sokolov.practice7.controller.SAXController;
import ua.nure.sokolov.practice7.controller.STAXController;
import ua.nure.sokolov.practice7.entity.OldCards;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Main {
    public static void usage() {
        System.out.println("Usage:\njava -jar ST3ExampleSimple.jar xmlFileName");
        System.out.println("java ua.nure.your_last_name.SummaryTask3.Main xmlFileName");
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            usage();
            return;
        }

        String xmlFileName = args[0];
        System.out.println("Input ==> " + xmlFileName);

        ////////////////////////////////////////////////////////
        // DOM
        ////////////////////////////////////////////////////////

        // get
        DOMController domController = new DOMController(xmlFileName);
        try {
            domController.parse(true);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
        OldCards test = domController.getOldCards();

        // sort (case 1)
        Sorter.sortQuestionsByQuestionText(test);

        // save
        String outputXmlFile = "output.dom.xml";
        try {
            DOMController.saveToXML(test, outputXmlFile);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        System.out.println("Output ==> " + outputXmlFile);

        ////////////////////////////////////////////////////////
        // SAX
        ////////////////////////////////////////////////////////

        // get
        SAXController saxController = new SAXController(xmlFileName);
        try {
            saxController.parse(true);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        test = saxController.getOldCards();

        // sort  (case 2)
        Sorter.sortQuestionsByAnswersNumber(test);

        // save
        outputXmlFile = "output.sax.xml";

        // other way:
        try {
            DOMController.saveToXML(test, outputXmlFile);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        System.out.println("Output ==> " + outputXmlFile);

//        ////////////////////////////////////////////////////////
//        // StAX
//        ////////////////////////////////////////////////////////
//
//        // get
        STAXController staxController = new STAXController(xmlFileName);
        try {
            staxController.parse();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        test = staxController.getTest();

        // sort  (case 3)
        Sorter.sortAnswersByContent(test);

        // save
        outputXmlFile = "output.stax.xml";
        try {
            DOMController.saveToXML(test, outputXmlFile);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        System.out.println("Output ==> " + outputXmlFile);

    }
}
