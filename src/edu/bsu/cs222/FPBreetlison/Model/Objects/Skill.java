package edu.bsu.cs222.FPBreetlison.Model.Objects;

import edu.bsu.cs222.FPBreetlison.Model.GameData;

import java.util.Arrays;
import java.util.List;

public class Skill implements java.io.Serializable {
    private String name;
    private int affectAmt;
    private int tpCost;
    private String type;
    private String type2;
    private String type3;
    private String type4;
    private Double chance;
    private String quickInfo;
    private String extraMessage;
    private String animType;
    private String element;

    GameData gameData;


    public Skill(String info, GameData gameData){
        this.gameData = gameData;
        List<String> skillInfo = stringParser(info);
        this.name = skillInfo.get(0);
        this.affectAmt = Integer.parseInt(skillInfo.get(1));
        this.tpCost = Integer.parseInt(skillInfo.get(2));
        this.element = skillInfo.get(3);
        this.type = skillInfo.get(4);
        this.type2 = skillInfo.get(5);
        this.type3 = skillInfo.get(6);
        this.type4 = skillInfo.get(7);
        this.chance = Double.parseDouble(skillInfo.get(8));
        this.quickInfo = skillInfo.get(9);
        this.extraMessage =  skillInfo.get(10);
        this.animType = skillInfo.get(11);
    }

    public void use(Fighter user, Fighter target){
        switch (type) {
            case "heal":
                if(type2.equals("all")){
                    healAll();
                }
                else{
                    user.recoverHealth(affectAmt);
                }

                break;
            case "attack":
                if(type2.equals("all")){
                    attackAll(user);
                }
                else{
                    attackOne(user,target);
                }

                break;
            case "debuff":
                target.weakenStat(affectAmt);
                break;
            case "buff":
                user.strengthenStat("attack", affectAmt);
                break;
            case "selfDebuff":

                break;
        }
    }

    private void attackOne(Fighter user, Fighter target) {
        double damage = user.getCurrStats().get("attack") * affectAmt / target.getCurrStats().get("defense");
        int finalDamage = (int) Math.round(damage * 1.5 * elementModifier(target));
        if(finalDamage < 1){
            finalDamage = 1;
        }
        target.takeDamage(finalDamage);
    }

    private void attackAll(Fighter user) {
        for(int i = 0; i<gameData.getEnemyTeam().size();i++){
            Fighter target = gameData.getEnemyTeam().get(i);
            double damage = user.getCurrStats().get("attack") * affectAmt / target.getCurrStats().get("defense");
            int finalDamage = (int) Math.round(damage * 1.5 * elementModifier(target));
            if(finalDamage < 1){
                finalDamage = 1;
            }
            target.takeDamage(finalDamage);
        }
    }

    private void healAll() {
        for(int i = 0; i<gameData.getTeam().size();i++){
            gameData.getTeam().get(i).recoverHealth(affectAmt);
        }
    }

    private double elementModifier(Fighter target) {
        if(element.equals(target.getWeakness())){
            return 1.5;
        }
        else if(element.equals(target.getStrength())){
            return .5;
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

    public String getType2() {
        return type2;
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
