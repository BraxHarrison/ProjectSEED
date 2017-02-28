package edu.bsu.cs222.FPBreetlison;

import java.util.ArrayList;

public class GameData {

    public static GameData gameData;

    private ArrayList<Fighter> team;
    private ArrayList<Fighter> enemyTeam;
    private Fighter target;

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
        team = new ArrayList<Fighter>();
        enemyTeam = new ArrayList<Fighter>();
        addHeroes();
        addEnemies();
        System.out.print("Initializing...");
    }

    private void addHeroes(){
        team.add(new Fighter("Eve",20,3,2,2,2,2));
        team.add(new Fighter("Quinn", 30, 4,2,2,2,2));
        team.add(new Fighter("Jones", 35, 5,2,22,2,2));
    }

    private void addEnemies(){
        enemyTeam.add(new Fighter("Jag", 30,4,3,3,3,3));
        enemyTeam.add(new Fighter("Jag", 30,2,2,2,2,2));
    }

    public ArrayList<Fighter> getTeam() {
        return team;
    }
    public void setTeam(ArrayList<Fighter> team) {
        this.team = team;
    }
    public ArrayList<Fighter> getEnemyTeam() {
        return enemyTeam;
    }
    public void setEnemyTeam(ArrayList<Fighter> enemyTeam) {
        this.enemyTeam = enemyTeam;
    }
    public Fighter getTarget() {
        return target;
    }
    public void setTarget(Fighter target) {
        this.target = target;
    }
}
