package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Controller.BattleView;
import edu.bsu.cs222.FPBreetlison.Controller.OverworldView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GameManager {

    private GameData gameData;
    private BattleManager battleLogic;
    private OverworldView overworld;
    private BattleView battleControl;
    Stage currentStage;
    private Room currentRoom;


    //region Initialization
    public void init(GameData gameData){
        currentRoom = gameData.getAllRooms().get("FirstSteps");
        this.gameData = gameData;
        currentStage = gameData.getStage();
        currentStage.setResizable(false);
        setUpOverworld();
    }

    private void setUpOverworld() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Parent root = fxmlLoader.load(getClass().getResource("/edu/bsu/cs222/FPBreetlison/View/OverworldUI.fxml").openStream());
            setOverworldAsStage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        overworld = fxmlLoader.getController();
        overworld.initialize(this);
    }
    private void setOverworldAsStage(Parent root){
        currentStage.setTitle("Overworld: "+gameData.getCurrentRoom().getName());
        currentStage.getIcons().add(new Image("/images/item_undefined.png"));
        currentStage.setScene(new Scene(root, 900, 600));
        currentStage.show();

    }
    //endregion

    public void play(){
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
            Parent root = fxmlLoader.load(getClass().getResource("/edu/bsu/cs222/FPBreetlison/View/BattleUI.fxml").openStream());
            setBattleAsStage(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        battleControl = fxmlLoader.getController();
        battleControl.initialize(this);
    }

    private void setBattleAsStage(Parent root){
        currentStage.setTitle("Battle!");
        currentStage.setScene(new Scene(root, 900,600));

    }
    public Room getCurrentRoom() {return currentRoom;}
    public GameData getGameData(){
        return gameData;
    }
    public OverworldView getOverworld() {
        return overworld;
    }
    public BattleView getBattleControl() {
        return battleControl;
    }
    public BattleManager getBattleLogic() {
        return battleLogic;
    }

    public void travelNorth() {
        if(Objects.equals(currentRoom.getNorth(), "null")) {
            return;
        }
        currentRoom = gameData.getAllRooms().get(currentRoom.getNorth());

    }

    public void travelSouth() {
        if(Objects.equals(currentRoom.getSouth(), "null")){
            return;
        }
        currentRoom = gameData.getAllRooms().get(currentRoom.getSouth());

    }

    public void travelEast() {
        if(Objects.equals(currentRoom.getEast(), "null")) {
            return;
        }
        currentRoom = gameData.getAllRooms().get(currentRoom.getEast());

    }

    public void travelWest() {

        if(Objects.equals(currentRoom.getWest(), "pop")) {
            return;
        }
        currentRoom = gameData.getAllRooms().get(currentRoom.getWest());

    }
}
