package edu.bsu.cs222.FPBreetlison;

public class DamageCalculator {

    public int calculateDamage(Battler target, Battler attacker){
        //return (int)calculateByStats(target,attacker);
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

    public float calculateByStats(Battler target, Battler attacker){
        //float defensePercent = 0.0;
        //Defense
        //if attacker.selectedMove.type = "PhyAtk"
        float defensePercent = target.getDefense()/300.0f;
        return attacker.getAttack()*defensePercent;
        //Figuring this out--------------------------------------
        // else if attacker.selectedMove.type = "EnAtk"
        //      defensePercent = defender.enDefense/100
        //      return attacker.enAttack*defensePercent ??

    }

    private float calculateArmor(){
        return 2.0f;

    }







}
