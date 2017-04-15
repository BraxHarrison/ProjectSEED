package edu.bsu.cs222.FPBreetlison.Model.Objects;

import edu.bsu.cs222.FPBreetlison.Model.DamageCalculator;

import java.util.*;

public class Fighter implements java.io.Serializable {

    private String name;
    private int hp;
    private int maxHP;
    private int attack;
    private int defense;
    private int tpCost;

    private double expModifier;
    private int lvl;
    private int experience;
    private int expToNextLevel;
    private boolean isLeveledUp;

    private ArrayList<String> battleStrings;
    private ArrayList<Skill> skillList;
    private HashMap<String,Integer> currStats;
    private HashMap<String,Integer> baseStats;
    private String skillAnim;
    private String battlerGraphicPath;
    private String miniGraphicPath;
    private Skill queuedSkill;
    private int sizeX;
    private int sizeY;
    private boolean KOState;
    private int KOLevel;
    private int lastDamage;

    public Fighter(String info){

        List<String> characterInfo = stringParser(info);
        expToNextLevel=150;
        currStats = new HashMap<>();
        skillList =  new ArrayList<Skill>();
        ArrayList<Object> attributes = new ArrayList<>();
        KOLevel = 0;
        lvl = 1;
        loadInfo(characterInfo);
        associateStats();
    }

    private void loadInfo(List<String> characterInfo) {
        this.name = characterInfo.get(0);
        this.maxHP = Integer.parseInt(characterInfo.get(1));
        this.hp = maxHP;
        this.attack = Integer.parseInt(characterInfo.get(2));
        this.defense = Integer.parseInt(characterInfo.get(3));
        //this.enAttack = Integer.parseInt(characterInfo.get(4));
        //this.enDefense = Integer.parseInt(characterInfo.get(5));
        //this.agility = Integer.parseInt(characterInfo.get(6));
        this.tpCost = Integer.parseInt(characterInfo.get(7));
        this.expModifier = Double.parseDouble(characterInfo.get(8));
        this.battlerGraphicPath = characterInfo.get(9);
        this.miniGraphicPath = characterInfo.get(10);
        this.sizeX = Integer.parseInt(characterInfo.get(11));
        this.sizeY = Integer.parseInt(characterInfo.get(12));
    }

    public double calcHPPercentage(){
        return (double)currStats.get(("hp"))/(double)hp;
    }

    private void associateStats(){
        currStats.put("hp",hp);
        currStats.put("attack",attack);
        currStats.put("defense",defense);
        currStats.put("tpCost",tpCost);
    }

    public void revertStats(){
        currStats.replace("attack",currStats.get("attack"),attack);
        currStats.replace("defense",currStats.get("defense"),defense);
        currStats.replace("tpCost",currStats.get("tpCost"),tpCost);
    }

    //region In-Battle Functionality

    public void doBasicAttack(Fighter target){
        double damage = this.getCurrStats().get("attack")*2/target.getCurrStats().get("defense");
        int finalDamage = (int)Math.round(damage);
        lastDamage = finalDamage;
        target.takeDamage(finalDamage);
        chooseActionString();
    }

    public void useSkill(int index){
        Skill usedSkill = skillList.get(index);
        usedSkill.use(this,this );
    }

    public void addSkill(Skill skill){
        skillList.add(skill);
    }

    public void getExp(int amount){
        experience += amount;
    }


    public void checkLevel(){
        if(experience >= expToNextLevel){
            levelUp();
        }

    }

    private void levelUp(){
        lvl +=1;
        hp+=5;
        attack+=2;
        defense+=2;
        int levelDifference = experience-expToNextLevel;
        double nextLevelRaw = expToNextLevel + expToNextLevel*expModifier;
        expToNextLevel = (int)Math.round(nextLevelRaw);
        experience = levelDifference;
        revertStats();
        isLeveledUp=true;

    }

    //endregion

    //region Reaction Functionality

    public void takeDamage(int damage){
        int oldHP = currStats.get("hp");
        currStats.replace("hp",oldHP,oldHP-damage);
        if(currStats.get("hp") < 0){
            oldHP = currStats.get("hp");
            currStats.replace("hp",oldHP,0);
        }
    }

    void recoverHealth(int heal){
        int hp = currStats.get("hp");
        currStats.replace("hp",currStats.get("hp"),hp+=heal);
        if(currStats.get("hp") > maxHP){
            currStats.replace("hp",currStats.get("hp"),maxHP);
        }
    }

    void strengthenStat(String stat, int factor){
        int oldValue = currStats.get(stat);
        int newValue = factor + oldValue;
        currStats.replace(stat,oldValue,newValue);
    }

    void weakenStat(String stat, int factor){
        int oldValue = currStats.get(stat);
        int newValue = oldValue - factor;
        currStats.replace(stat,oldValue,newValue);
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
        if(currStats.get("hp")<=0 && KOLevel == 1){
            KOState = true;
            KOLevel = 2;
        }
        else if(currStats.get("hp")==0 && KOLevel ==0){
            KOState = true;
            KOLevel = 1;
        }
        return KOLevel;

    }
    public boolean isKO(){
        return KOState;
    }

    //region Setters and Getters


    public ArrayList<Skill> getSkillList() {
        return skillList;
    }

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
    public int getDefense() {
        return defense;
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
    public Skill getQueuedSkill() {
        return queuedSkill;
    }
    public void setQueuedSkill(Skill queuedSkill) {
        this.queuedSkill = queuedSkill;
    }
    public HashMap<String, Integer> getCurrStats() {
        return currStats;
    }
    public boolean isLeveledUp() {
        return isLeveledUp;
    }
    public void setLeveledUp(boolean leveledUp) {
        isLeveledUp = leveledUp;
    }
    public int getLvl() {
        return lvl;
    }
    public String getSkillAnim() {
        return skillAnim;
    }
    public void setSkillAnim(String skillAnim) {
        this.skillAnim = skillAnim;
    }
    public int getLastDamage() {
        return lastDamage;
    }
    //endregion

}
