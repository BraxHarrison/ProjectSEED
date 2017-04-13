package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Model.Objects.Fighter;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Skill;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BattleXMLParser {

    private Document document;
    private String keyword;
    private HashMap<String,Fighter> heroes;
    private HashMap<String,Fighter> enemies;
    private HashMap<String,Skill> skills;

    //region Initialization Functions

    private void createDoc() throws ParserConfigurationException, IOException, SAXException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("database/GameInfo.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(inputStream);
    }

    //endregion

    public void parseBattleData() throws ParserConfigurationException, IOException, SAXException {
        createDoc();
        skills = loadSkills();
        heroes = loadFighters("hero");
        enemies = loadFighters("enemy");
    }

    public HashMap<String,Skill> loadSkills() {
        HashMap<String,Skill> skills = new HashMap<>();
        NodeList nodeList = this.document.getElementsByTagName("Skill");
        for(int i = 0; i< nodeList.getLength();i++){

            Node battleInfo = nodeList.item(i);
            Element skillSource = (Element)battleInfo;
            Skill skill = new Skill(createSkillString(skillSource));
            skills.put(skill.getName(),skill);
        }
        return skills;
    }


    private HashMap<String,Fighter> loadFighters(String key){
        this.keyword = key;
        HashMap<String,Fighter> fighters = new HashMap<>();
        NodeList nodeList = this.document.getElementsByTagName(keyword);
        for(int i = 0; i< nodeList.getLength();i++){

            Node battleInfo = nodeList.item(i);
            Element battler = (Element)battleInfo;
            Fighter fighter = new Fighter(createFighterString(battler));
            fighters.put(fighter.getName(),fighter);
            setBattleDescriptions(fighter);
            setStarterSkills(fighter,i);

        }
        return fighters;

    }

    private void setBattleDescriptions (Fighter fighter){
        ArrayList<String> battleDescriptions = loadBattleDescriptions(fighter);
        fighter.setBattleStrings(battleDescriptions);

    }

    private ArrayList<String> loadBattleDescriptions(Fighter fighter) {
        String elementName = fighter.getName() +
                "BattleString";
        ArrayList<String> strings = new ArrayList<>();
        NodeList nodeList = this.document.getElementsByTagName(elementName);
        for(int i = 0; i<nodeList.getLength();i++){
            Element battleDesc = (Element)nodeList.item(i);
            strings.add(battleDesc.getAttribute("string"));

        }
        return strings;
    }

    private void setStarterSkills(Fighter fighter, int index){
        List<String> starterSkillNames = loadStarterSkills(index);
        ArrayList<Skill> skills = fetchSkills(starterSkillNames);
        for(int i = 0; i<skills.size();i++){
            fighter.addSkill(skills.get(i));

        }
    }

    private List<String> loadStarterSkills(int index){
        NodeList nodeList = this.document.getElementsByTagName(keyword+"Skill");
        Element skillStringSource = (Element)nodeList.item(index);
        String skillString = skillStringSource.getAttribute("starter");
        return Arrays.asList(skillString.split(","));

    }

    private ArrayList<Skill> fetchSkills(List<String> skillStrings){
        ArrayList<Skill> fullSkills = new ArrayList<>();
        for(int i = 0; i<skillStrings.size();i++){
            String skillName = skillStrings.get(i);
            fullSkills.add(skills.get(skillName));
        }
        return fullSkills;
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
                battler.getAttribute("expModifier") + "," +
                battler.getAttribute("battlerGraphicPath") + "," +
                battler.getAttribute("miniGraphicPath") + "," +
                battler.getAttribute("sizeX") + "," +
                battler.getAttribute("sizeY");



    }

    private String createSkillString(Element skill){

        return (skill.getAttribute("name") + ",") +
                skill.getAttribute("affectAmt") + "," +
                skill.getAttribute("tpCost") + "," +
                skill.getAttribute("type") + "," +
                skill.getAttribute("type2") + "," +
                skill.getAttribute("quickInfo") + "," +
                skill.getAttribute("extraMessage") + "," +
                skill.getAttribute("animType");
    }

    public HashMap<String,Fighter> getHeroes() {
        return heroes;
    }
    public HashMap<String,Fighter> getEnemies() {
        return enemies;
    }

    public HashMap<String, Skill> getSkills() {
        return skills;
    }
}
