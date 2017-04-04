package Model.Tests;


import edu.bsu.cs222.FPBreetlison.Model.BattleXMLParser;
import edu.bsu.cs222.FPBreetlison.Model.DamageCalculator;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Fighter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;


public class BattleTests {

    private DamageCalculator damageCalc;
    private BattleXMLParser parser;
    private Document characterInfo;
    private Fighter attacker;
    private Fighter defender;


    //region Test Initialization
    @Before
    public void setUp() throws ParserConfigurationException, IOException, SAXException {
        initFighters();
        setUpCharacterInfo();

    }

    private void initFighters(){
        attacker = new Fighter("Attacker,2,2,2,2,2,2,2");
        defender = new Fighter("Attacker,2,2,2,2,2,2,2");
        damageCalc = new DamageCalculator(attacker);
        parser = new BattleXMLParser();
    }

    private void setUpCharacterInfo() throws ParserConfigurationException, IOException, SAXException {

    }

    //endregion
    //region XML Parser Tests

    //These need to be redone when we're finished adding information to characters

    @Test
    public void TestCharacterStringParser(){
        Fighter fighter = new Fighter("Elmira,10,2,2,2,2,2,2");
        Assert.assertEquals("Elmira",fighter.getName());
        Assert.assertEquals(2,fighter.getAttack());
    }

    @Test
    public void TestFighterXMLParse() throws IOException, SAXException, ParserConfigurationException {
        ArrayList<Fighter> fighters = parser.parseFighterInfo();
        Fighter fighter = fighters.get(0);
        Assert.assertEquals("Prota",fighter.getName());

    }

    @Test
    public void TestMoveDescriptionXMLParse() throws IOException, SAXException, ParserConfigurationException {
        ArrayList<Fighter> fighters = parser.parseFighterInfo();
        ArrayList<String> battleDesc = fighters.get(0).getBattleStrings();
        Assert.assertEquals("You fire a quick shot at the enemy. It grazes them.",battleDesc.get(0));
    }

    @Test
    public void TestItemXMLParse(){

    }

    @Test
    public void TestRoomXMLParse(){

    }

    @Test
    public void TestEntityXMLParse(){

    }

    @Test
    public void TestEventXMLParse(){

    }

    //endregion
    //region Overworld Tests
    @Test
    public void TestBasicRoomTraversal(){

    }

    //endregion
    //region Battle System Tests

    @Test
    public void TestKODetection(){

    }

    @Test
    public void TestEnemyTargetFinder(){

    }

    @Test
    public void TestStateMachineEndEnemyTurn(){

    }

    @Test
    public void TestStateMachineEnemyWin(){

    }
    @Test
    public void TestStateMachineHeroWin(){

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
    public void TestHealingItem(){

    }

    @Test
    public void TestBuffItem(){

    }

    @Test
    public void TestAttackSkill(){

    }

    @Test
    public void TestBuffSkill(){

    }

    @Test
    public void TestHealSkill(){

    }

    @Test
    public void TestFleeChecker(){

    }

    //endregion
}
