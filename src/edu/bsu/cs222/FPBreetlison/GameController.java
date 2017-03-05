package edu.bsu.cs222.FPBreetlison;

import GUI.BattleController;
import GUI.OverworldController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {

    private GameData gameData;
    private BattleManager battleLogic;
    private OverworldController overworld;
    private BattleController battleControl;
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
        battleControl = fxmlLoader.getController();
        battleControl.initialize(this);
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
        battleLogic = new BattleManager();
        setUpBattle();
        battleLogic.getGameInfo(this);
        battleLogic.start();
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
