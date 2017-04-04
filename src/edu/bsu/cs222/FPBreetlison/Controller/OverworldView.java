package edu.bsu.cs222.FPBreetlison.Controller;

import edu.bsu.cs222.FPBreetlison.Model.GameData;
import edu.bsu.cs222.FPBreetlison.Model.GameManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Map;


public class OverworldView {


    GameManager game;
    GameData gameData;
    public Label roomDescription;
    public StackPane travelPane;
    public Button north;
    public Button south;
    public Button east;
    public Button west;


    public void initialize(GameManager game){
        this.game = game;
        this.gameData = game.getGameData();
        updateRoom();
    }

    private void updateRoom() {
        roomDescription.setText(game.getCurrentRoom().getDescription());
        setDirectionButtonsVisible();
        game.updateStageTitle();
    }

    public void setDirectionButtonsVisible() {
        ArrayList<Button> directionButtons = new ArrayList<>();
        directionButtons.add(north);
        directionButtons.add(south);
        directionButtons.add(east);
        directionButtons.add(west);
        Map<Integer, Boolean> directions = game.checkAvalibleDirections();
        for (int i = 0; i<directionButtons.size();i++){
            directionButtons.get(i).setVisible(directions.get(i));
        }

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
        updateRoom();
    }

    public void travelSouth(ActionEvent actionEvent) {
        game.travelSouth();
        updateRoom();
    }

    public void travelEast(ActionEvent actionEvent) {
        game.travelEast();
        updateRoom();
    }

    public void travelWest(ActionEvent actionEvent) {
        game.travelWest();
        updateRoom();
    }
}
