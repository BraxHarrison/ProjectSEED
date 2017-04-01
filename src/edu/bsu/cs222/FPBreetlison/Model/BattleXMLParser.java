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

    Document document;
    NodeList nodeList;
    ArrayList<Fighter> fighters;

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
        ArrayList<Fighter> fighters = new ArrayList<Fighter>();
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
        for(int i = 0; i < fighters.size();i++){
            Fighter fighter = fighters.get(i);
            ArrayList<String> battleDescriptions = loadBattleDescriptions(fighter);
            fighter.setBattleStrings(battleDescriptions);
        }
    }

    private ArrayList<String> loadBattleDescriptions(Fighter fighter) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(fighter.getName());
        strBuilder.append("BattleString");
        String elementName = strBuilder.toString();
        ArrayList<String> strings = new ArrayList<String>();
        nodeList = this.document.getElementsByTagName(elementName);
        for(int i = 0; i<nodeList.getLength();i++){
            Element battleDesc = (Element)nodeList.item(i);
            strings.add(battleDesc.getAttribute("string"));

        }
        return strings;

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
        strBuilder.append(battler.getAttribute("tpCost") + ",");
        strBuilder.append(battler.getAttribute("battlerGraphicPath"));

        return strBuilder.toString();

    }

}
