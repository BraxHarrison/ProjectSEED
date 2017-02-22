package edu.bsu.cs222.FPBreetlison.Tests;

import edu.bsu.cs222.FPBreetlison.DamageCalculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//Test
public class BattleLogicTests {

    private DamageCalculator damageCalc;

    @Before
    public void setUp(){
        damageCalc = new DamageCalculator();
    }

    @Test
    public void TestDamageCalcGeneral(){
        //DamageCalculator damageCalculator = new DamageCalculator();
        Assert.assertEquals(2,damageCalc.calculateDamage());
    }

    @Test
    public void TestAffinityGeneral(){

    }

}
