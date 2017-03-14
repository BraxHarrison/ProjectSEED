package edu.bsu.cs222.FPBreetlison.Tests;

import edu.bsu.cs222.FPBreetlison.Fighter;
import edu.bsu.cs222.FPBreetlison.DamageCalculator;

import edu.bsu.cs222.FPBreetlison.XMLParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class BattleTests {

    private DamageCalculator damageCalc;
    private XMLParser parser;
    private Document characterInfo;
    private Fighter attacker;
    private Fighter defender;


    @Before
    public void setUp() throws ParserConfigurationException, IOException, SAXException {
        initFighters();
        setUpCharacterInfo();

    }

    private void initFighters(){
        attacker = new Fighter("Attacker,2,2,2,2,2,2,2");
        defender = new Fighter("Attacker,2,2,2,2,2,2,2");
        damageCalc = new DamageCalculator(attacker, defender);
        parser = new XMLParser();
    }

    private void setUpCharacterInfo() throws ParserConfigurationException, IOException, SAXException {

    }

    @Test
    public void TestDamageCalcGeneral(){

        Assert.assertEquals(attacker.getAttack(),damageCalc.calculateDamage());
    }

    @Test
    public void TestAffinityGeneral(){
        Assert.assertEquals(attacker.getAttack(), damageCalc.calculateDamage());
    }

    @Test
    public void CheckFighterKO(){
        Assert.assertEquals(0,attacker.checkKO());
    }

    @Test
    public void CheckDeadFighterKO(){
        Fighter deadAttakcer = new Fighter("Attacker,0,2,2,2,2,2,2");
        Assert.assertEquals(1,deadAttakcer.checkKO());
    }

    @Test
    public void TestCharacterStringParser(){
        Fighter fighter = new Fighter("Elmira,10,2,2,2,2,2,2");
        Assert.assertEquals("Elmira",fighter.getName());
        Assert.assertEquals(2,fighter.getAttack());
    }

    @Test
    public void TestXMLCharacterInfo() throws IOException, SAXException, ParserConfigurationException {
        ArrayList<Fighter> fighters = parser.parseFighterInfo();
        Fighter fighter = fighters.get(0);
        Assert.assertEquals("Prota",fighter.getName());

    }

    @Test
    public void TestCharacterBattleDescriptions() throws IOException, SAXException, ParserConfigurationException {
        ArrayList<Fighter> fighters = parser.parseFighterInfo();
        ArrayList<String> battleDesc = fighters.get(0).getBattleStrings();
        Assert.assertEquals("You fire a quick shot at the enemy. It grazes them.",battleDesc.get(0));
    }

}
