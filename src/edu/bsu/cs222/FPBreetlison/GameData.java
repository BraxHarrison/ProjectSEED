package edu.bsu.cs222.FPBreetlison;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameData {

    public static GameData gameData;

    private ArrayList<Fighter> team;
    private ArrayList<Fighter> enemyTeam;
    private Fighter target;
    private Stage stage;
    private String noodles = "Nooooodles?";

    private GameController game;

    public static GameData getData(){
        if(gameData == null){
            gameData = new GameData();
            return gameData;
        }
        return gameData;

    }

    public GameData(){
        init();

    }
    public String getNoodles(){
        return noodles;
    }
    public void setNoodles(){
        noodles = "Noodles.";
    }

    private void init(){
        team = new ArrayList<Fighter>();
        enemyTeam = new ArrayList<Fighter>();
        addHeroes();
        addEnemies();
        System.out.println("Initializing...");

    }

    private void addHeroes(){
        team.add(new Fighter("Prota",30,7,5,2,5,7));
        team.add(new Fighter("Roxy", 20, 5,7,6,9,12));
        team.add(new Fighter("Smitty", 45, 10,12,1,4,5));
    }

    private void addEnemies(){
        enemyTeam.add(new Fighter("Jag", 25,10,3,3,3,7));
        enemyTeam.add(new Fighter("Blisterbulb", 30,6,6,7,7,3));
        enemyTeam.add(new Fighter("Harshmallow", 20,3,4,9,11,4));
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
    public GameController getGame() {
        return game;
    }
    public void setGame(GameController game) {
        this.game = game;
    }
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
