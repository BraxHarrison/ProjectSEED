package edu.bsu.cs222.FPBreetlison;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class XMLParser {

    //I want this to read through all of the character info and return a list of fighter

    Document document;
    NodeList nodeList;


    public ArrayList<Fighter> parseFighterInfo() throws ParserConfigurationException, IOException, SAXException {
        createDoc();
        findElement();
        return createFighterList();

    }

    private void createDoc() throws ParserConfigurationException, IOException, SAXException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("GameInfo.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(inputStream);
    }

    private void findElement(){
        nodeList = this.document.getElementsByTagName("chara");

    }

    private ArrayList<Fighter> createFighterList(){
        ArrayList<Fighter> fighters = new ArrayList<Fighter>();
        for(int i = 0; i< nodeList.getLength();i++){
            Node battleInfo = nodeList.item(i);
            Element battler = (Element)battleInfo;
            Fighter fighter = new Fighter(createFighterString(battler));
            fighters.add(fighter);
        }
        return fighters;

    }

    private String createFighterString(Element battler) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(battler.getAttribute("name") + ",");
        strBuilder.append(battler.getAttribute("maxHP") + ",");
        strBuilder.append(battler.getAttribute("attack") + ",");
        strBuilder.append(battler.getAttribute("defense") + ",");
        strBuilder.append(battler.getAttribute("enAttack") + ",");
        strBuilder.append(battler.getAttribute("enDefense") + ",");
        strBuilder.append(battler.getAttribute("agility") + ",");
        strBuilder.append(battler.getAttribute("tpCost"));

        return strBuilder.toString();


    }

}
