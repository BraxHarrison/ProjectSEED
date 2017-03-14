package edu.bsu.cs222.FPBreetlison;

import java.util.ArrayList;

public class Fighter {

    private String name;
    private int hp;
    private int maxHP;
    private int attack;
    private int defense;
    private int enAttack;
    private int enDefense;
    private int agility;

    private ArrayList<Skill> Skills;

    //Try to get rid of all of these parameter
    public Fighter(String name, int maxHP, int attack, int defense, int enAttack, int enDefense, int agility){

        Skills = new ArrayList<Skill>();

        this.name = name;
        this.hp = maxHP;
        this.maxHP = maxHP;
        this.attack = attack;
        this.defense = defense;
        this.enAttack = enAttack;
        this.enDefense = enDefense;
        this.agility = agility;
    }


    public void doBasicAttack(Fighter target){
       DamageCalculator damageCalculator = new DamageCalculator(this, target);
       target.takeDamage(damageCalculator.calculateDamage());
    }

    public void takeDamage(int damage){
        hp -=damage;
        if(hp < 0){
            hp = 0;
        }
    }



    public void recoverHealth(int heal){
        hp +=heal;
        if(hp > maxHP){
            hp = maxHP;
        }
    }


    public void sufferEffect(){

    }

    public String generateAttackDescription(){
        //Expand this to include randomized descriptions
        return name + " struck the enemy!";
    }

    public void learnSkill(Skill skill){
        Skills.add(skill);
    }

    //region Setters and Getters

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getHp() {
        return hp;
    }
    public int getMaxHP() {
        return maxHP;
    }
    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }
    public int getAttack() {
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public int getDefense() {
        return defense;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public int getEnAttack() {
        return enAttack;
    }
    public void setEnAttack(int enAttack) {
        this.enAttack = enAttack;
    }
    public int getEnDefense() {
        return enDefense;
    }
    public void setEnDefense(int enDefense) {
        this.enDefense = enDefense;
    }
    public int getAgility() {
        return agility;
    }
    public void setAgility(int agility) {
        this.agility = agility;
    }
    public ArrayList<Skill> getSkills(){
        return Skills;
    }
    //endregion

}
