package edu.bsu.cs222.FPBreetlison;

public class DamageCalculator {

    private Fighter Attacker;
    private Fighter Defender;


    public DamageCalculator(Fighter attacker, Fighter defender){

        Attacker = attacker;
        Defender = defender;

    }

    public int calculateDamage(){
        //return (int)calculateByStats();
        return 2;
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

    private float calculateByStats(Fighter target, Fighter attacker){
        //float defensePercent = 0.0;
        //Defense
        //if Attacker.selectedMove.type = "PhyAtk"
        float defensePercent = target.getDefense()/300.0f;
        return attacker.getAttack()*defensePercent;
        //Figuring this out--------------------------------------
        // else if attacker.selectedMove.type = "EnAtk"
        //      defensePercent = defender.enDefense/100
        //      return attacker.enAttack*defensePercent ??

    }







}
