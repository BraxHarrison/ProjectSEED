package edu.bsu.cs222.FPBreetlison;

import java.util.ArrayList;

public class Battler {

    private String Name;
    private int HP;
    private int MaxHP;
    private int Attack;
    private int Defense;
    private int EnAttack;
    private int EnDefense;
    private int Agility;

    private ArrayList<Skill> Skills;

    public Battler(String name,int hp, int attack, int defense, int enAttack, int enDefense, int agility){

        Skills = new ArrayList<Skill>();

        Name = name;
        HP = hp;
        MaxHP = hp;
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
    public void doBasicAttack(Battler target){
       DamageCalculator damageCalculator = new DamageCalculator();
       target.takeDamage(damageCalculator.calculateDamage(this, target));

    }
    public void takeDamage(int damage){
        HP-=damage;
        if(HP < 0){
            HP = 0;
        }
    }
    public void recoverHealth(int heal){
        HP+=heal;
        if(HP > MaxHP){
            HP = MaxHP;
        }
    }
    public void sufferEffect(){

    }

    public String generateAttackDescription(){
        //Expand this to include randomized descriptions
        return Name + " struck the enemy!";
    }

    public void learnSkill(Skill skill){
        Skills.add(skill);
    }

    //region Setters and Getters
    public void setHP(int hp) {
        HP = hp;
    }
    public int getHP() {
        return HP;
    }
    public int getMaxHP() {
        return MaxHP;
    }
    public void setMaxHP(int maxHP) {
        MaxHP = maxHP;
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
    public ArrayList<Skill> getSkills(){
        return Skills;
    }
    //endregion

}
