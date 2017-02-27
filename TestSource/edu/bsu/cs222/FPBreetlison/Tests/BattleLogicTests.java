package edu.bsu.cs222.FPBreetlison.Tests;

import edu.bsu.cs222.FPBreetlison.Battler;
import edu.bsu.cs222.FPBreetlison.DamageCalculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BattleLogicTests {

    private DamageCalculator damageCalc;
    private Battler attacker;
    private Battler defender;


    @Before
    public void setUp(){
        damageCalc = new DamageCalculator();
        attacker = new Battler("Attacker",2,2,2,2,2,2);
        defender = new Battler("Attacker",2,2,2,2,2,2);
    }

    @Test
    public void TestDamageCalcGeneral(){

        Assert.assertEquals(6.6666669845581055,damageCalc.calculateByStats(defender,attacker),0.0f);
    }

    @Test
    public void TestAffinityGeneral(){
        Assert.assertEquals(2, damageCalc.calculateDamage(defender,attacker));
    }

}
