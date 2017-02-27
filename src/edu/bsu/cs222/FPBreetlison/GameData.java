package edu.bsu.cs222.FPBreetlison;

import java.util.ArrayList;

public class GameData {

    public static GameData gameData;

    private ArrayList<Battler> team;
    private ArrayList<Battler> enemyTeam;
    private Battler target;

    public static GameData getData(){
        if(gameData == null){
            gameData = new GameData();
        }
        return gameData;

    }

    public GameData(){
        init();
    }

    private void init(){
        team = new ArrayList<Battler>();
        enemyTeam = new ArrayList<Battler>();
        addHeroes();
        addEnemies();
        System.out.print("Initializing...");
    }

    private void addHeroes(){
        team.add(new Battler("Eve",20,2,2,2,2,2));
        team.add(new Battler("Quinn", 30, 2,2,2,2,2));
        team.add(new Battler("Jones", 35, 2,2,2,2,2));
    }

    private void addEnemies(){
        enemyTeam.add(new Battler("Jag", 30,2,2,2,2,2));
        enemyTeam.add(new Battler("Jag", 30,2,2,2,2,2));
    }

    public ArrayList<Battler> getTeam() {
        return team;
    }
    public void setTeam(ArrayList<Battler> team) {
        this.team = team;
    }
    public ArrayList<Battler> getEnemyTeam() {
        return enemyTeam;
    }
    public void setEnemyTeam(ArrayList<Battler> enemyTeam) {
        this.enemyTeam = enemyTeam;
    }
    public Battler getTarget() {
        return target;
    }
    public void setTarget(Battler target) {
        this.target = target;
    }
}
