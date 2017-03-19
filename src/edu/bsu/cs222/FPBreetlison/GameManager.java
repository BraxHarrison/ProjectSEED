package edu.bsu.cs222.FPBreetlison;

import GUI.BattleController;
import GUI.OverworldController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GameManager {

    private GameData gameData;
    private BattleManager battleLogic;
    private OverworldController overworld;
    private BattleController battleControl;
    Stage currentStage;

    //region Initialization
    public void init(GameData gameData){
        this.gameData = gameData;
        currentStage = gameData.getStage();
        currentStage.setResizable(false);
        setUpOverworld();

    }
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
        currentStage.setScene(new Scene(root, 900, 800));
        currentStage.show();

    }
    //endregion

    public void play(){
        System.out.println("Game is running");
        this.gameData = gameData;
    }

    public void createBattle(){
        createBattleLogic();
        //battleLogic.start();
        createBattleController();

    }

    private void createBattleLogic(){
        battleLogic = new BattleManager();
    }

    private void createBattleController(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Parent root = fxmlLoader.load(getClass().getResource("/GUI/BattleUI.fxml").openStream());
            setBattleAsStage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        battleControl = fxmlLoader.getController();
        battleControl.initialize(this);
    }

    private void setBattleAsStage(Parent root){
        currentStage.setTitle("Battle!");
        currentStage.setScene(new Scene(root, 900,800));

    }

    public GameData getGameData(){
        return gameData;
    }
    public OverworldController getOverworld() {
        return overworld;
    }
    public BattleController getBattleControl() {
        return battleControl;
    }
    public BattleManager getBattleLogic() {
        return battleLogic;
    }
}
