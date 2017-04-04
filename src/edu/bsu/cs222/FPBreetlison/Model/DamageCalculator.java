package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Model.Objects.Fighter;

public class DamageCalculator {

    private Fighter attacker;

    public DamageCalculator(Fighter attacker){

        this.attacker = attacker;

    }

    public int calculateDamage(){

        return attacker.getAttack();
    }


}
