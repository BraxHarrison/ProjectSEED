package edu.bsu.cs222.FPBreetlison;

import javafx.stage.Stage;

import java.util.ArrayList;

public class GameData {

    public static GameData gameData;

    private ArrayList<Fighter> team;
    private ArrayList<Fighter> enemyTeam;
    private Fighter target;
    private Stage stage;
    private int tp;
    private int maxTP;
    private int selectedUser;
    private int selectedTarget;

    private GameManager game;

    public GameData(){
        init();

    }

    private void init(){
        team = new ArrayList<Fighter>();
        enemyTeam = new ArrayList<Fighter>();
        addHeroes();
        addEnemies();
        System.out.println("Initializing...");
        maxTP = 10;
        tp = maxTP;

    }

    private void addHeroes(){
        team.add(new Fighter("Prota,30,7,5,2,5,7,3"));
        team.add(new Fighter("Roxy,20,5,7,6,9,12,2"));
        team.add(new Fighter("Smitty,45,10,12,1,4,5,5"));
    }

    private void addEnemies(){
        enemyTeam.add(new Fighter("Jag,25,10,3,3,3,7,2"));
        enemyTeam.add(new Fighter("Blisterbulb,30,6,6,7,7,3,6"));
        enemyTeam.add(new Fighter("Harshmallow,20,3,4,9,11,4,4"));
    }

    public void resetHeroTP(){
        tp = maxTP;
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
    public GameManager getGame() {
        return game;
    }
    public void setGame(GameManager game) {
        this.game = game;
    }
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public int getMaxTP() {
        return maxTP;
    }
    public int getCurrentTp() {
        return tp;
    }
    public int getSelectedUser() {
        return selectedUser;
    }
    public void setSelectedUser(int selectedUser) {
        this.selectedUser = selectedUser;
    }
    public int getSelectedTarget() {
        return selectedTarget;
    }
    public void setSelectedTarget(int selectedTarget) {
        this.selectedTarget = selectedTarget;
    }

    public void subtractTp(int cost) {
        tp -= cost;
        if(tp < 0){
            tp = 0;
        }
    }
}
