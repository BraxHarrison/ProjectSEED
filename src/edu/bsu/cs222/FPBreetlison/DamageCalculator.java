package edu.bsu.cs222.FPBreetlison;

public class DamageCalculator {

    private Fighter attacker;
    private Fighter defender;


    public DamageCalculator(Fighter attacker, Fighter defender){

        this.attacker = attacker;
        this.defender = defender;

    }

    public int calculateDamage(){
        return (int)calculateByStats();
        //return 2;
    }

    private float calculateElementalAffinity(){
        //if attacker.selectedMove.element == character.element;
        //      return 2.0;
        //else return 1;
        return 2.0f;
    }

    private float calculateElementalWeakness(){
        //if Attacker.selectedMove.element == Defender.element.weaknesses
        //      return 2.0;
        //else if Attack.selectedMove.element == Defender.element.resistances
        //      return .5;
        //return;
        return 2.0f;
    }

    private float calculateByStats(){
        //float defensePercent = 0.0;
        //Defense
        //if (attacker.selectedMove.type = "PhyAtk"){}
        int defenseVal= defender.getDefense();
        float defensePercent = (defenseVal*1.0f)/40;
        return attacker.getAttack()*(1-defensePercent);
        //Figuring this out--------------------------------------
        // else if attacker.selectedMove.type = "EnAtk"
        //      defensePercent = defender.enDefense/100
        //      return attacker.enAttack*defensePercent ??
    }
}
