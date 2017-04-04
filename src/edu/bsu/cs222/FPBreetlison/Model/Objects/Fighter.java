package edu.bsu.cs222.FPBreetlison.Model.Objects;

import edu.bsu.cs222.FPBreetlison.Model.DamageCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fighter {

    private String name;
    private int hp;
    private int maxHP;
    private int attack;
    private int defense;
    private int tpCost;
    //Following three character types are not being factored into calculation yet.
    //Leaving in place for easier understanding of needed calculations in future development.
    //private int enAttack;
    //private int enDefense;
    //private int agility;

    private ArrayList<String> battleStrings;
    private String battlerGraphicPath;
    private String miniGraphicPath;
    private int sizeX;
    private int sizeY;
    private boolean KOState;
    private int KOLevel;

    public Fighter(String info){

        List<String> characterInfo = stringParser(info);
        KOLevel = 0;

        this.name = characterInfo.get(0);
        this.maxHP = Integer.parseInt(characterInfo.get(1));
        this.hp = maxHP;
        this.attack = Integer.parseInt(characterInfo.get(2));
        this.defense = Integer.parseInt(characterInfo.get(3));
        //this.enAttack = Integer.parseInt(characterInfo.get(4));
        //this.enDefense = Integer.parseInt(characterInfo.get(5));
        //this.agility = Integer.parseInt(characterInfo.get(6));
        this.tpCost = Integer.parseInt(characterInfo.get(7));
        this.battlerGraphicPath = characterInfo.get(8);
        this.miniGraphicPath = characterInfo.get(9);
        this.sizeX = Integer.parseInt(characterInfo.get(10));
        this.sizeY = Integer.parseInt(characterInfo.get(11));
    }

    //region In-Battle Functionality

    public void doBasicAttack(Fighter target){
        DamageCalculator damageCalculator = new DamageCalculator(this);
        target.takeDamage(damageCalculator.calculateDamage());
        chooseActionString();
    }

    //endregion

    //region Reaction Functionality

    private void takeDamage(int damage){
        hp -=damage;
        if(hp < 0){
            hp = 0;
        }
    }

    void recoverHealth(int heal){
        hp +=heal;
        if(hp > maxHP){
            hp = maxHP;
        }
    }

    void buffer(String type, int amt){
        if(type.equals("speed")){
            tpCost-=amt;
        }
        else if(type.equals("attack")){
            attack+=amt;
        }
    }

    //endregion

    //region Text-Related Functionality

    private void chooseActionString(){

    }

    private List<String> stringParser(String info){

        return Arrays.asList(info.split(","));
    }

    //endregion

    public int checkKOLevel(){
        if(hp<=0 && KOLevel == 1){
            KOState = true;
            KOLevel = 2;
        }
        else if(hp<=0 && KOLevel ==0){
            KOState = true;
            KOLevel = 1;
        }
        return KOLevel;

    }
    public boolean isKO(){
        return KOState;
    }

    //region Setters and Getters

    //Will be cleaned up, and hopefully most will be gone by iteration 2.
    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }
    public int getMaxHP() {
        return maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public int getTpCost() {
        return tpCost;
    }

    public ArrayList<String> getBattleStrings() {
        return battleStrings;
    }
    public void setBattleStrings(ArrayList<String> battleStrings) {
        this.battleStrings = battleStrings;
    }
    public int getKOLvl() {
        return KOLevel;
    }
    public String getBattlerGraphicPath() {
        return battlerGraphicPath;
    }
    public String getMiniGraphicPath(){return miniGraphicPath;}
    public int getSizeX(){return sizeX;}
    public int getSizeY(){return sizeY;}
    //endregion

}
