package edu.bsu.cs222.FPBreetlison.Tests;

import edu.bsu.cs222.FPBreetlison.DamageCalculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BattleLogicTests {

    private DamageCalculator damageCalc;

    @Before
    public void setUp(){
        damageCalc = new DamageCalculator();
    }

    @Test
    public void TestDamageCalcGeneral(){
        Assert.assertEquals(2.0,damageCalc.calculateByStats(),0.0f);
    }

    @Test
    public void TestAffinityGeneral(){
        Assert.assertEquals(2, damageCalc.calculateDamage());
    }

}
