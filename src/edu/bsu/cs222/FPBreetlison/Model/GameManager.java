package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Controller.BattleView;
import edu.bsu.cs222.FPBreetlison.Controller.OverworldView;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Room;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GameManager {

    private GameData gameData;
    private BattleManager battleLogic;
    private BattleView battleControl;
    private Stage currentStage;
    private Room currentRoom;


    //region Initialization
    public void init(GameData gameData){
        currentRoom = gameData.getAllRooms().get("FirstSteps");
        this.gameData = gameData;
        currentStage = gameData.getStage();
        currentStage.setResizable(true);
        setUpOverworld();
    }

    void setUpOverworld() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Parent root = fxmlLoader.load(getClass().getResource("/edu/bsu/cs222/FPBreetlison/View/OverworldUI.fxml").openStream());
            setOverworldAsStage(root);
            updateStageTitle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OverworldView overworld = fxmlLoader.getController();
        overworld.initialize(this);
    }
    private void setOverworldAsStage(Parent root){
        currentStage.getIcons().add(new Image("/images/battleGraphics/itemGraphics/item_undefined.png"));
        currentStage.setScene(new Scene(root, 900, 600));
        currentStage.show();

    }

    public void updateStageTitle(){
        currentStage.setTitle("Overworld: "+getCurrentRoom().getName());
    }
    //endregion

    public void play(){
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

    BattleView getBattleControl() {
        return battleControl;
    }
    public BattleManager getBattleLogic() {
        return battleLogic;
    }


    public HashMap<Integer, Boolean> checkAvalibleDirections() {

        HashMap<Integer, Boolean> availableDirections = new HashMap<>();
        ArrayList<String> allDirections = new ArrayList<>();

        allDirections.add(currentRoom.getNorth());
        allDirections.add(currentRoom.getSouth());
        allDirections.add(currentRoom.getEast());
        allDirections.add(currentRoom.getWest());

        for (int i = 0; i<allDirections.size();i++){
            String roomDirection = allDirections.get(i);
            if (roomDirection.equals("null")) {
                availableDirections.put(i,false);
            }
            else{
                availableDirections.put(i,true);
            }
        }

        return  availableDirections;

    }

    public void travelNorth() {
        if(Objects.equals(currentRoom.getNorth(),"null")){
            return;
        }

        currentRoom = gameData.getAllRooms().get(currentRoom.getNorth());

    }

    public void travelSouth() {
        if(currentRoom.getSouth().equals("null")){
            return;

        }
        currentRoom = gameData.getAllRooms().get(currentRoom.getSouth());
    }

    public void travelEast() {
        if(currentRoom.getEast().equals("null")) {
            return;
        }
        currentRoom = gameData.getAllRooms().get(currentRoom.getEast());

    }

    public void travelWest() {
        if(currentRoom.getWest().equals("null")) {
            return;
        }
        currentRoom = gameData.getAllRooms().get(currentRoom.getWest());
    }
}
