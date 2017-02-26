package edu.bsu.cs222.FPBreetlison;

public class DamageCalculator {

    //This class needs to die. Its contents can be moved to Battler.

    public int calculateDamage(){
        return 2;
    }

    private float calculateElementalAffinity(){
        //if attacker.selectedMove.element == character.element;
        //      return 2.0;
        //else return 1;
        return 2.0f;
    }

    private float calculateElementalWeakness(){
        //if attacker.selectedMove.element == defender.element.weaknesses
        //      return 2.0;
        //else if attack.selectedMove.element == defender.element.resistances
        //      return .5;
        //return;
        return 2.0f;
    }

    public float calculateByStats(){
        //float defensePercent = 0.0;
        //Defense
        //if attacker.selectedMove.type = "PhyAtk"
        float defensePercent = 40.0f/300.0f;
        System.out.print(50.0f*defensePercent);
        return 50.0f*defensePercent;
        //Figuring this out--------------------------------------
        // else if attacker.selectedMove.type = "EnAtk"
        //      defensePercent = defender.enDefense/100
        //      return attacker.enAttack*defensePercent ??

    }

    private float calculateArmor(){
        return 2.0f;

    }







}
