package edu.bsu.cs222.FPBreetlison;

import GUI.BattleController;
import GUI.OverworldController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static edu.bsu.cs222.FPBreetlison.GameData.gameData;

public class GameController {

    private GameData gameData;
    private BattleManager battleManager;
    private OverworldController overworld;
    private BattleController battle;
    Stage currentStage;

    public void init(GameData gameData){
        this.gameData = gameData;
        currentStage = gameData.getStage();
        currentStage.setResizable(false);
        setUpOverworld();

    }

    //region Stage Initialization
    private void setUpOverworld() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Parent root = fxmlLoader.load(getClass().getResource("/GUI/OverworldUI.fxml").openStream());
            setOverworldAsStage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        overworld = fxmlLoader.getController();
        overworld.initialize(this);
    }
    private void setOverworldAsStage(Parent root){
        currentStage.setTitle("Overworld---(Location to be set)");
        currentStage.setScene(new Scene(root, 900, 500));
        currentStage.show();
    }
    private void setUpBattle(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Parent root = fxmlLoader.load(getClass().getResource("/GUI/BattleUI.fxml").openStream());
            setBattleAsStage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        battle = fxmlLoader.getController();
        battle.initialize(this);
    }
    private void setBattleAsStage(Parent root){
        currentStage.setTitle("Battle!");
        currentStage.setScene(new Scene(root, 900,500));

    }
    //endregion

    public void play(){
        System.out.println("Game is running");
        this.gameData = gameData;
    }

    public void startBattle(){
        battleManager = new BattleManager();
        setUpBattle();
        battleManager.startBattle();
    }
    public void quit(){
        System.out.println("Game has been quit.");
    }

    public GameData getGameData(){
        return gameData;
    }



}
