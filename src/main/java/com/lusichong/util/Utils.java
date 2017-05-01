package com.lusichong.util;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Created by lusichong on 2017/4/29 20:45.
 */
public class Utils {
    private static final String TAG = "Utils";
    public static void setSimulationXMLParams(int moCount) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(Const.BEIJING_MAP_TRAJECTORY_CONFIG));

            Node root = doc.getFirstChild();

            Node agents = root.getChildNodes().item(3);
            NamedNodeMap agentsMap = agents.getAttributes();
            Attr count = (Attr) agentsMap.getNamedItem("count");
            count.setValue(moCount + "");

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            DOMSource domSource = new DOMSource(root);
            StreamResult result = new StreamResult(new File(Const.BEIJING_MAP_TRAJECTORY_CONFIG));
            transformer.transform(domSource, result);
            Log.i(TAG, "setSimulationXMLParams success.");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
