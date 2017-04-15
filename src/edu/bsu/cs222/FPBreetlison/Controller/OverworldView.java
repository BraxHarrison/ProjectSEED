package edu.bsu.cs222.FPBreetlison.Controller;

import edu.bsu.cs222.FPBreetlison.Model.GameData;
import edu.bsu.cs222.FPBreetlison.Model.GameManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class OverworldView {


    public VBox sideBar;
    public ImageView sideBarGraphic;
    public ImageView travelButton;
    public Label roomDescription;
    public StackPane travelPane;
    public Button north;
    public Button south;
    public Button east;
    public Button west;
    public HBox backgroundImage;

    private GameManager game;
    private GameData gameData;

    public void initialize(GameManager game){
        this.game = game;
        this.gameData = game.getGameData();
        setUpGraphics();
        updateRoom();
        initializeTravel();
    }

    private void initializeTravel(){
        travelButton.setOnMousePressed(e->openTravel());
        travelButton.setImage(new Image("/images/system/system_travel.png"));
    }
    private void setUpGraphics() {
        setUpBarGraphic();
        ImageView partyButton = (ImageView)sideBar.getChildren().get(0);
        partyButton.setImage(new Image("/images/system/system_undefined.png"));
        partyButton.setFitHeight(50);
        partyButton.setFitWidth(50);
    }

    private void setUpBarGraphic() {
        sideBarGraphic.setImage(new Image("/images/system/system_sidebar.png"));
    }

    private void updateRoom() {
        roomDescription.setText(gameData.getCurrentRoom().getDescription());
        setDirectionButtonsVisible();
        setBackground();
        game.updateStageTitle();
    }

    private void setBackground() {
        String backgroundImageURL = gameData.getCurrentRoom().getImageURL();
        BackgroundImage roomBack = new BackgroundImage(new Image( backgroundImageURL,900,500,false,true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        backgroundImage.setBackground(new Background(roomBack));
        backgroundImage.toBack();

    }

    private void setDirectionButtonsVisible() {
        ArrayList<Button> directionButtons = new ArrayList<>();
        directionButtons.add(north);
        directionButtons.add(south);
        directionButtons.add(east);
        directionButtons.add(west);
        Map<Integer, Boolean> directions = game.checkAvailableDirections();
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

    public void saveGame(ActionEvent actionEvent) throws IOException {
        game.saveGame();
    }

    public void loadGame(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        game.loadGame();
        initialize(this.game);
    }
}
