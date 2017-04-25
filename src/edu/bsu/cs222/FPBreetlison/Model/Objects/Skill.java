package edu.bsu.cs222.FPBreetlison.Model.Objects;

import java.util.Arrays;
import java.util.List;

public class Skill implements java.io.Serializable {
    String name;
    int affectAmt;
    int tpCost;
    String type;
    String type2;
    String quickInfo;
    String extraMessage;
    String animType;
    String element;


    public Skill(String info){
        List<String> skillInfo = stringParser(info);
        this.name = skillInfo.get(0);
        this.affectAmt = Integer.parseInt(skillInfo.get(1));
        this.tpCost = Integer.parseInt(skillInfo.get(2));
        this.element = skillInfo.get(3);
        this.type = skillInfo.get(4);
        this.type2 = skillInfo.get(5);
        this.quickInfo = skillInfo.get(6);
        this.extraMessage =  skillInfo.get(7);
        this.animType = skillInfo.get(8);
    }

    public void use(Fighter user, Fighter target){
        if(type.equals("heal")){
            user.recoverHealth(affectAmt);
        }
        else if(type.equals("attack")){
            double damage = user.getCurrStats().get("attack")*affectAmt/target.getCurrStats().get("defense");
            int finalDamage = (int)Math.round(damage*1.5*elementModifier(target));
            target.takeDamage(finalDamage);
        }
        else if(type.equals("debuff")){
            target.weakenStat("attack",affectAmt);
        }
        else if(type.equals("buff")){
            user.strengthenStat("attack",affectAmt);
        }
        else if(type.equals("selfDebuff")){

        }
    }

    private double elementModifier(Fighter target) {
        if(element.equals(target.getWeakness())){
            return 1.5;
        }
        return 1;
    }

    private List<String> stringParser(String info){
        return Arrays.asList(info.split(","));
    }

    public String getName() {
        return name;
    }
    public String getQuickInfo() {
        return quickInfo;
    }
    public String getExtraMessage() {
        return extraMessage;
    }
    public String getType() {
        return type;
    }
    public int getTpCost() {
        return tpCost;
    }
    public String getAnimType() {
        return animType;
    }
    public String getElement() {
        return element;
    }
}
