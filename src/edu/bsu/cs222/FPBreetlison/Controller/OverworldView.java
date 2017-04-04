package edu.bsu.cs222.FPBreetlison.Controller;

import edu.bsu.cs222.FPBreetlison.Model.GameManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Map;


public class OverworldView {


    private GameManager game;
    public Label roomDescription;
    public StackPane travelPane;
    public Button north;
    public Button south;
    public Button east;
    public Button west;


    public void initialize(GameManager game){
        this.game = game;
        updateRoom();
    }

    private void updateRoom() {
        roomDescription.setText(game.getCurrentRoom().getDescription());
        setDirectionButtonsVisible();
        game.updateStageTitle();
    }

    private void setDirectionButtonsVisible() {
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

    public void openTravel() {
        travelPane.setVisible(true);
    }

    public void closeTravel() {
        travelPane.setVisible(false);
    }


    public void travelNorth() {
        game.travelNorth();
        updateRoom();
    }

    public void travelSouth() {
        game.travelSouth();
        updateRoom();
    }

    public void travelEast() {
        game.travelEast();
        updateRoom();
    }

    public void travelWest() {
        game.travelWest();
        updateRoom();
    }
}
