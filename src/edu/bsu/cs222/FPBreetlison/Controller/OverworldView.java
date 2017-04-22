package edu.bsu.cs222.FPBreetlison.Controller;

import edu.bsu.cs222.FPBreetlison.Model.Animator;
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
    public ImageView partyButton;
    public ImageView sideBarGraphic;
    public ImageView travelButton;
    public ImageView saveLoadButton;
    public ImageView inspectButton;
    public Label roomDescription;
    public StackPane travelPane;
    public Button north;
    public Button south;
    public Button east;
    public Button west;
    public HBox backgroundImage;
    public StackPane loadSaveMenu;
    public StackPane inspectMenu;
    public StackPane navBanner;
    public Label walletDisplay;

    private GameManager game;
    private GameData gameData;
    private Animator animator;

    public void initialize(GameManager game){
        this.game = game;
        this.gameData = game.getGameData();
        this.animator = new Animator(game);
        setUpBarGraphic();
        updateRoom();
        initializePartyButton();
        initializeTravelButton();
        initializeSaveLoadButton();
        initializeInspectButton();
        initNavBanner();
    }



    private void initializePartyButton() {
        //partyButton.setOnMousePressed(e->openTravel());
        partyButton.setOnMouseEntered(e->optionHover(partyButton));
        partyButton.setOnMouseExited(e->optionUnhover(partyButton));
        partyButton.setImage(new Image("/images/system/system_undefined.png"));
    }

    private void initNavBanner() {
        ImageView bannerImage = (ImageView)navBanner.getChildren().get(0);
        Label bannerDisplay = (Label)navBanner.getChildren().get(1);
        bannerImage.setImage(new Image("/images/system/system_banner.png"));
        bannerDisplay.setText(gameData.getCurrentRoom().getName());
        animator.showBanner(navBanner);

    }

    private void updateBanner(){
        Label bannerDisplay = (Label)navBanner.getChildren().get(1);
        bannerDisplay.setText(gameData.getCurrentRoom().getName());
        animator.showBanner(navBanner);
    }


    private void initializeTravelButton(){
        travelButton.setOnMousePressed(e->openTravel());
        travelButton.setOnMouseEntered(e->optionHover(travelButton));
        travelButton.setOnMouseExited(e->optionUnhover(travelButton));
        travelButton.setImage(new Image("/images/system/system_sidebar_map.png"));
    }



    private void initializeSaveLoadButton(){
        saveLoadButton.setOnMousePressed(e->showSaveMenu());
        saveLoadButton.setOnMouseEntered(e->optionHover(saveLoadButton));
        saveLoadButton.setOnMouseExited(e->optionUnhover(saveLoadButton));
        saveLoadButton.setImage(new Image("/images/system/system_sidebar_save.png"));
    }

    private void initializeInspectButton() {
        inspectButton.setOnMousePressed(e->showInspectMenu());
        inspectButton.setOnMouseEntered(e->optionHover(inspectButton));
        inspectButton.setOnMouseExited(e->optionUnhover(inspectButton));
        inspectButton.setImage(new Image("/images/system/system_sidebar_inspect.png"));
    }

    private void showSaveMenu() {
        loadSaveMenu.setVisible(true);
    }

    private void showInspectMenu() {
        inspectMenu.setVisible(true);
    }

    private void optionHover(ImageView button) {

        button.setScaleX(1.5);
        button.setScaleY(1.5);
    }
    private void optionUnhover(ImageView button){
        button.setScaleX(1);
        button.setScaleY(1);
    }

    private void setUpBarGraphic() {
        sideBarGraphic.setImage(new Image("/images/system/system_sidebar.png"));
    }

    private void updateRoom() {
        roomDescription.setText(gameData.getCurrentRoom().getDescription());
        setDirectionButtonsVisible();
        setBackground();
        updateBanner();
        updateWallet();
        game.updateStageTitle();
    }

    private void updateWallet() {
        walletDisplay.setText(gameData.getWallet().getDisplayAmount());
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

    public void saveGame() throws IOException {
        game.saveGame();
    }

    public void loadGame() throws IOException, ClassNotFoundException {
        game.loadGame();
        initialize(this.game);
    }

    public void hideLoadSaveMenu(ActionEvent actionEvent) {
        loadSaveMenu.setVisible(false);
    }

    public void hideInspectMenu(ActionEvent actionEvent) {
        inspectMenu.setVisible(false);
    }
}
