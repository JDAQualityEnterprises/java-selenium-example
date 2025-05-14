package com.jda.core;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


/* =====================================================================================================================
OBJECTIVE
    To provide an auto generated value at runtime and reuse during an entire Cucumber run
    This means, that preferable to have a Run file for each set of related feature files

===================================================================================================================== */
public class XmlHelper {


    // default constructor
    public XmlHelper() {
    }


    /* =================================================================================================================
    OBJECTIVE
         Returns list of nodes matching xpath expression

    OPERATIONAL NOTES
        NEEDS TO BE GENERALISED

    MODIFICATIONS (Descending order)
        DATE        AUTHOR      DESCRIPTION
        2017/04/10  Allan       Creation
    ================================================================================================================= */
    /*
    public static NodeList readTrainUnitCardFile(String xpathExpression) throws IOException {
        String contents = "<FileNotFound />";
        String sourceFile = Driver.getDataPath().concat("\\TrainUnitCard.txt");

        if (Files.exists(Paths.get(sourceFile))) {
            contents = FileHelper.readFromFile(sourceFile);

            // Fix for the badly formed XML
            contents = contents.replace("&nbsp;", "");
            contents = contents.replace("<hr class=\"vd-card-hr\">", "");
            contents = contents.replace("<img style=\"position: absolute; left: -2px; top: -336px; width: 59px; height: 492px; -moz-user-select: none; border: 0px none; padding: 0px; margin: 0px; max-width: none;\" src=\"https://maps.gstatic.com/mapfiles/transparent.png\" draggable=\"false\">", "");
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(contents)));

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xPath = xPathfactory.newXPath();

            //read an xml node using xpath
            NodeList nodeList = (NodeList) xPath.compile(xpathExpression).evaluate(document, XPathConstants.NODESET);

            return nodeList;

        } catch (Exception ex) {
            return null;
        }
    }
*/


    public static Document getXmlDocumentByFile(String sourceFile) throws IOException {
        String contents = "";

        if (Files.exists(Paths.get(sourceFile))) {
            contents = FileHelper.readFromFile(sourceFile);
        } else {
            return null;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(contents)));

        } catch (Exception ex) {
            return null;
        }
    }


    public static Document getXmlDocumentByString(String contents) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(contents)));

        } catch (Exception ex) {
            return null;
        }
    }


    public static NodeList getNodeListByXpath(Document document , String xpathExpression) throws IOException {
        try {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xPath = xPathfactory.newXPath();

            //read an xml node using xpath
            NodeList nodeList = (NodeList) xPath.compile(xpathExpression).evaluate(document, XPathConstants.NODESET);

            return nodeList;

        } catch (Exception ex) {
            return null;
        }
    }


}
