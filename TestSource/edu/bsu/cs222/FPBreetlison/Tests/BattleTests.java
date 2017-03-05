package edu.bsu.cs222.FPBreetlison.Tests;

import edu.bsu.cs222.FPBreetlison.Fighter;
import edu.bsu.cs222.FPBreetlison.DamageCalculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BattleTests {

    private DamageCalculator damageCalc;
    private Fighter attacker;
    private Fighter defender;


    @Before
    public void setUp(){
        attacker = new Fighter("Attacker,2,2,2,2,2,2,2");
        defender = new Fighter("Attacker,2,2,2,2,2,2,2");
        damageCalc = new DamageCalculator(attacker, defender);

    }

    @Test
    public void TestDamageCalcGeneral(){

        Assert.assertEquals(2,damageCalc.calculateDamage());
    }

    @Test
    public void TestAffinityGeneral(){
        Assert.assertEquals(2, damageCalc.calculateDamage());
    }

    @Test
    public void TestCharacterStringParser(){
        Fighter fighter = new Fighter("Elmira,10,2,2,2,2,2,2");
        Assert.assertEquals("Elmira",fighter.getName());
        Assert.assertEquals(2,fighter.getAttack());
    }

}
