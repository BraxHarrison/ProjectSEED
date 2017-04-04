package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Model.Objects.Fighter;
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

public class BattleXMLParser {

    private Document document;
    private NodeList nodeList;
    private ArrayList<Fighter> fighters;

    //region Initialization Functions

    private void createDoc() throws ParserConfigurationException, IOException, SAXException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("database/GameInfo.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(inputStream);
    }

    //endregion

    public ArrayList<Fighter> parseFighterInfo() throws ParserConfigurationException, IOException, SAXException {
        createDoc();
        fighters = createFighterList();
        setBattleDescriptions();
        return fighters;
    }

    private ArrayList<Fighter> createFighterList(){
        ArrayList<Fighter> fighters = new ArrayList<>();
        nodeList = this.document.getElementsByTagName("chara");
        for(int i = 0; i< nodeList.getLength();i++){

            Node battleInfo = nodeList.item(i);
            Element battler = (Element)battleInfo;
            Fighter fighter = new Fighter(createFighterString(battler));
            fighters.add(fighter);
        }
        return fighters;

    }

    private void setBattleDescriptions (){
        for (Fighter fighter : fighters) {
            ArrayList<String> battleDescriptions = loadBattleDescriptions(fighter);
            fighter.setBattleStrings(battleDescriptions);
        }
    }

    private ArrayList<String> loadBattleDescriptions(Fighter fighter) {
        String elementName = fighter.getName() +
                "BattleString";
        ArrayList<String> strings = new ArrayList<>();
        nodeList = this.document.getElementsByTagName(elementName);
        for(int i = 0; i<nodeList.getLength();i++){
            Element battleDesc = (Element)nodeList.item(i);
            strings.add(battleDesc.getAttribute("string"));

        }
        return strings;

    }



    private String createFighterString(Element battler) {

        return (battler.getAttribute("name") + ",") +
                battler.getAttribute("maxHP") + "," +
                battler.getAttribute("attack") + "," +
                battler.getAttribute("defense") + "," +
                battler.getAttribute("enAttack") + "," +
                battler.getAttribute("enDefense") + "," +
                battler.getAttribute("agility") + "," +
                battler.getAttribute("tpCost") + "," +
                battler.getAttribute("battlerGraphicPath");

    }

}
