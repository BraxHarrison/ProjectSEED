package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Model.Objects.Fighter;

public class DamageCalculator {

    private Fighter attacker;
    private Fighter defender;

    public DamageCalculator(Fighter attacker, Fighter defender){

        this.attacker = attacker;
        this.defender = defender;

    }

    public int calculateDamage(){

        return attacker.getAttack();
    }

    private float calculateElementalAffinity(){

        return 2.0f;
    }

    private float calculateElementalWeakness(){
        return 2.0f;
    }

    private float calculateByStats(){

        float roughDamage =  attacker.getAttack()/defender.getDefense();

        return roughDamage;

    }







}
