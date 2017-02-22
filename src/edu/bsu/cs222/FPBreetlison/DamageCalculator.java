package edu.bsu.cs222.FPBreetlison;

public class DamageCalculator {


    public int calculateDamage(){
        return 2;
    }

    private float calculateElementalAffinity(){
        //if attacker.selectedMove.element == attacker.element;
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
        return 1.0f;
    }

    public float calculateByStats(){
        //float defensePercent = 0.0;
        //if attacker.selectedMove.type = "PhyAtk"
              //float defensePercent = 40.0f/300.0f; (THIS WORKS)
              //System.out.print(50.0f*defensePercent);
        // else if attacker.selectedMove.type = "EnAtk"
        //      defensePercent = defender.enDefense/300
        //      return attacker.enAttack*defensePercent ??

        return 2.0f;
    }

    private float calculateArmor(){
        return 2.0f;

    }







}
