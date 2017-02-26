package edu.bsu.cs222.FPBreetlison;

public class Battler {

    private String Name;
    private int Attack;
    private int Defense;
    private int EnAttack;
    private int EnDefense;
    private int Agility;

    public Battler(String name, int attack, int defense, int enAttack, int enDefense, int agility){
        Name = name;
        Attack = attack;
        Defense = defense;
        EnAttack = enAttack;
        EnDefense = enDefense;
        Agility = agility;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public int getAttack() {
        return Attack;
    }
    public void setAttack(int attack) {
        Attack = attack;
    }
    public int getDefense() {
        return Defense;
    }
    public void setDefense(int defense) {
        Defense = defense;
    }
    public int getEnAttack() {
        return EnAttack;
    }
    public void setEnAttack(int enAttack) {
        EnAttack = enAttack;
    }
    public int getEnDefense() {
        return EnDefense;
    }
    public void setEnDefense(int enDefense) {
        EnDefense = enDefense;
    }
    public int getAgility() {
        return Agility;
    }
    public void setAgility(int agility) {
        Agility = agility;
    }

}
