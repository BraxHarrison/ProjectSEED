package edu.bsu.cs222.FPBreetlison.Controller;

import edu.bsu.cs222.FPBreetlison.Model.GameData;
import edu.bsu.cs222.FPBreetlison.Model.GameManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;


public class OverworldView {


    GameManager game;
    GameData gameData;
    public Label roomDescription;
    public ScrollPane travelPane;


    public void initialize(GameManager game){
        this.game = game;
        this.gameData = game.getGameData();
        updateRoomDescription();
    }

    private void updateRoomDescription() {
        roomDescription.setText(game.getCurrentRoom().getDescription());
    }

    public void startBattle(){
        game.createBattle();
    }

    public void openTravel(ActionEvent actionEvent) {
        travelPane.setVisible(true);
    }

    public void closeTravel(ActionEvent actionEvent) {
        travelPane.setVisible(false);
    }


    public void travelNorth(ActionEvent actionEvent) {
        game.travelNorth();
        updateRoomDescription();
    }

    public void travelSouth(ActionEvent actionEvent) {
        game.travelSouth();
        updateRoomDescription();
    }

    public void travelEast(ActionEvent actionEvent) {
        game.travelEast();
        updateRoomDescription();
    }

    public void travelWest(ActionEvent actionEvent) {
        game.travelWest();
        updateRoomDescription();
    }
}
