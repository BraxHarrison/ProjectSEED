package GUI;

import edu.bsu.cs222.FPBreetlison.BattleManager;
import edu.bsu.cs222.FPBreetlison.Fighter;
import edu.bsu.cs222.FPBreetlison.GameManager;
import edu.bsu.cs222.FPBreetlison.GameData;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BattleView {


    //region Variables

    public VBox heroSelectorArea;
    public VBox actionMenu;
    public VBox allHeroVitals;
    public VBox allEnemyVitals;
    public ScrollPane objectsMenu;
    public HBox enemySelectorArea;
    public Label historyDisplay;
    public ProgressBar tpBar;
    public Label tpDisplay;
    public HBox backgroundImage;
    public ScrollPane selectorMenu;
    public Pane loaderScreen;
    public StackPane battleDisplay;

    GameData gameData;
    GameManager game;
    BattleManager battleLogic;

    Font darwinFont;


    public Label mainDisplay;
    //endregion
    //region Utility Functions

    public void queueMessages(ArrayList<String> messages){
        Timeline timeline = new Timeline();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearMessages(messages);
            }
        });
        int dur = 0;
        for(int i = 0; i<messages.size();i++){
            String message = messages.get(i);
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(dur),
                    ae -> pushMessage(message)));
            dur += 1500;
        }
        timeline.play();
    }

    private void clearMessages(ArrayList<String> messages){
        messages.clear();
    }

    public void pushMessage(String message) {
        historyDisplay.setText(mainDisplay.getText() + "\n\n" + historyDisplay.getText());
        mainDisplay.setText(message);
    }

    private void startLoader(){
        loaderScreen.toFront();
        loaderScreen.setStyle("-fx-background-color: black;");
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> endLoader()));
        timeline.play();

    }

    private void endLoader() {
        loaderScreen.setVisible(false);
        battleDisplay.setVisible(true);
    }
    //endregion
    //region Initialization Functions

    public void initialize(GameManager game){
        startLoader();
        loadFonts();
        transferBattleData(game);
        setBackground();
        formatSelectorArea();
        setupBattle();
    }

    private void transferBattleData(GameManager game) {
        this.game = game;
        this.gameData = game.getGameData();
        this.battleLogic = game.getBattleLogic();
        battleLogic.getGameInfo(game);

    }


    private void loadFonts() {
        darwinFont = Font.loadFont(getClass().getResource("/fonts/Darwin.ttf").toExternalForm(), 10);
    }

    private void formatSelectorArea() {

        mainDisplay.setTextFill(Color.web("0x000000"));
        mainDisplay.setFont(darwinFont);
        for(int i = 0; i < actionMenu.getChildren().size();i++){
            Label action = (Label)actionMenu.getChildren().get(i);
            action.setFont(darwinFont);
        }

    }

    private void setBackground() {
        BackgroundImage battleBack = new BackgroundImage(new Image("images/Plains_Day.png",900,500,false,true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        backgroundImage.setBackground(new Background(battleBack));
        backgroundImage.toBack();

    }

    private void setupBattle(){
        createHeroButtons();
        createEnemySelectors();
        pushMessage("An enemy group led by " + gameData.getEnemyTeam().get(0).getName()
                + " appears!");
        battleLogic.start();
    }

    private void createHeroButtons() {

        ArrayList<Fighter> team = gameData.getTeam();
        for(int i = 0; i<team.size();i++){
            Label hero = new Label(team.get(i).getName());
            hero.setText(team.get(i).getName());
            hero.setId(Integer.toString(i));
            formatHeroButton(hero);
            hero.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
                    selectHero(hero);
                }
            });
            heroSelectorArea.getChildren().add(hero);
        }
    }

    private void formatHeroButton(Label hero) {
        hero.setScaleX(3);
        hero.setScaleY(3);
        hero.setMaxWidth(40);
        hero.setMinWidth(40);
        hero.setTextFill(Color.web("0xfffff1"));
        hero.setFont(darwinFont);
    }

    private ImageView setImage(int id) {
        String heroPath = gameData.getTeam().get(id).getBattlerGraphicPath();
        Image image = new Image(getClass().getResourceAsStream(heroPath));
        ImageView imageView = new ImageView(image);
        return imageView;
    }

    private void selectHero(Label hero) {
        gameData.setSelectedUser(Integer.parseInt(hero.getId()));
        heroSelectorArea.setVisible(false);
        showActionMenu();
    }

    private void createHeroVitals(){
        ArrayList<Fighter> team = gameData.getTeam();
        for(int i = 0; i < team.size();i++){
            Label name = new Label(team.get(i).getName());
            Label hp = new Label("HP: "+team.get(i).getHp()+"/"+team.get(i).getMaxHP());

            VBox heroVitals = new VBox();
            heroVitals.getChildren().add(name);
            heroVitals.getChildren().add(hp);
            allHeroVitals.getChildren().add(heroVitals);
        }
    }

    private void createEnemyVitals(){
        ArrayList<Fighter> enemyTeam = gameData.getEnemyTeam();
        for(int i = 0; i < enemyTeam.size();i++){
            Label name = new Label(enemyTeam.get(i).getName());
            Label hp = new Label("HP: "+ enemyTeam.get(i).getHp()+"/"+enemyTeam.get(i).getMaxHP());

            VBox enemyVitals = new VBox();
            enemyVitals.getChildren().add(name);
            enemyVitals.getChildren().add(hp);
            allEnemyVitals.getChildren().add(enemyVitals);
        }
    }

    //endregion
    //region Button Logic

    public void selectAttack(javafx.scene.input.MouseEvent event) {
        pushMessage("Who will " + gameData.getTeam().get(gameData.getSelectedUser()).getName() + " attack?");
        showSelector(enemySelectorArea);
    }
    public void selectEndTurn(javafx.scene.input.MouseEvent event) {
        battleLogic.endPlayerTurn();
    }
    public void selectSkill(javafx.scene.input.MouseEvent event) {

    }

    public void selectItems(javafx.scene.input.MouseEvent event) {

    }

    public void selectFlee(javafx.scene.input.MouseEvent event) {

    }

    public void createEnemySelectors() {
        ArrayList<Fighter> enemyTeam = gameData.getEnemyTeam();
        for(int i = 0; i<enemyTeam.size();i++){
            Button enemy = new Button("Enemy");
            enemy.setId(Integer.toString(i));
            formatEnemySelector(enemy);
            enemy.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    selectEnemy(enemy);
                }
            });
            enemySelectorArea.setVisible(false);
            enemySelectorArea.getChildren().add(enemy);

        }
    }

    private void formatEnemySelector(Button enemy) {
        enemy.setScaleX(1.5);
        enemy.setScaleY(2);
        //enemy.setOpacity(0);
    }

    public void hideSelector(HBox selector){
        selector.setVisible(false);
    }

    public void showSelector(HBox selector){
        selector.setVisible(true);
    }


    public boolean detectEnemyKO() {
        ObservableList<Node> selectors = enemySelectorArea.getChildren();
        ArrayList<Fighter> fighters = gameData.getEnemyTeam();
        int KOamt = 0;
        for (int i = 0; i < selectors.size(); i++) {
            fighters.get(i).checkKO();
            if (fighters.get(i).getKOLvl() > 0) {
                KOamt++;
            }
            if(fighters.get(i).getKOLvl() == 1){
                selectors.get(i).setVisible(false);
                pushMessage(fighters.get(i).getName() + " is down!");
            }
        }
        if (KOamt == fighters.size()) {
            return true;
        }
        return false;
    }

    private void selectEnemy(Button selected) {
        gameData.setSelectedTarget(Integer.parseInt(selected.getId()));
        triggerAttack();
    }

    private void triggerAttack() {

        battleLogic.tryBasicAttack();
        //updateEnemyVitals();

    }

    private void updateEnemyVitals() {
        ArrayList<Fighter> enemyTeam = gameData.getEnemyTeam();
        for(int i = 0; i<allEnemyVitals.getChildren().size();i++){
            VBox enemyVital = (VBox) allEnemyVitals.getChildren().get(i);
            Label enemyHP = (Label)enemyVital.getChildren().get(1);
            enemyHP.setText("HP: " + enemyTeam.get(i).getHp()+"/"+enemyTeam.get(i).getMaxHP());
        }
    }

    public void updateHeroVitals(){
        ArrayList<Fighter> heroTeam = gameData.getTeam();
        for(int i = 0; i< allHeroVitals.getChildren().size(); i++){
            VBox heroVital = (VBox) allHeroVitals.getChildren().get(i);
            Label heroHP = (Label)heroVital.getChildren().get(1);
            heroHP.setText("HP: " + heroTeam.get(i).getHp()+"/"+heroTeam.get(i).getMaxHP());
        }
    }

    public void updateTP(double percentage){
        tpBar.setProgress(percentage);
        tpDisplay.setText("TP: " + gameData.getCurrentTp() + "/" + gameData.getMaxTP());
        battleLogic.checkPlayerTP();
    }

    private void showActionMenu() {
        actionMenu.setVisible(true);
        //pushMessage("What will " + gameData.getTeam().get(gameData.getSelectedUser()).getName() + " do?" );
    }


    //endregion


}
