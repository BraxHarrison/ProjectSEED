package GUI;

import edu.bsu.cs222.FPBreetlison.*;
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

import java.util.ArrayList;

public class BattleView {

    //region Variables

    private ArrayList<Fighter> team;
    private ArrayList<Fighter> enemyTeam;
    private ArrayList<Item> inventory;

    public VBox heroSelectorArea;
    public VBox actionMenu;
    public VBox allHeroVitals;
    public VBox allEnemyVitals;
    public HBox enemySelectorArea;
    public VBox itemSelectorArea;
    public Label historyDisplay;
    public ProgressBar tpBar;
    public Label tpDisplay;
    public HBox backgroundImage;
    public ScrollPane selectorMenu;
    public Pane loaderScreen;
    public StackPane battleDisplay;
    public Button backButton;

    public int selectedUser;
    private int selectedTarget;
    private int selectedItem;
    private boolean uiLocked;

    GameData gameData;
    GameManager game;
    BattleManager battleLogic;

    Font darwinFont;

    public Label mainDisplay;
    //endregion
    //region Utility Functions

    public void queueMessages(ArrayList<String> messages){
        Timeline timeline = new Timeline();
        timeline.setOnFinished(e -> clearMessages(messages));
        ArrayList<String> queuedMessages = messages;
        int dur = 0;
        for(int i = 0; i<queuedMessages.size();i++){
            String message = queuedMessages.get(i);
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(dur),
                    ae -> pushMessage(message)));
            dur += 1500;
        }
        uiLocked = true;
        timeline.play();
    }

    private void clearMessages(ArrayList<String> messages){
        messages.clear();
        uiLocked = false;
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
        transferBattleData(game);
        readData();
        startLoader();
        loadFonts();
        setBackground();
        formatDisplayArea();
        setupBattle();
    }

    private void readData() {
        team = gameData.getTeam();
        enemyTeam = gameData.getEnemyTeam();
        inventory = gameData.getInventory();

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

    private void formatDisplayArea() {

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
        createItemsButtons();
        pushMessage("An enemy group led by " + enemyTeam.get(0).getName()
                + " appears!");
        battleLogic.start();
    }

    private void createHeroButtons() {

        for(int i = 0; i<team.size();i++){
            Label hero = new Label(team.get(i).getName());
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
        String heroPath = team.get(id).getBattlerGraphicPath();
        Image image = new Image(getClass().getResourceAsStream(heroPath));
        ImageView imageView = new ImageView(image);
        return imageView;
    }

    public void createEnemySelectors() {
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

    private void createItemsButtons(){
        ArrayList<Item> inventory = gameData.getInventory();
        for(int i = 0; i<inventory.size();i++){
            System.out.println(inventory.get(i).getName());
            Label item = new Label(inventory.get(i).getName());
            item.setId(Integer.toString(i));
            System.out.println("Creating item id : " + item.getId());
            formatItemSelector(item);
            item.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>(){
                @Override
                public void handle(javafx.scene.input.MouseEvent event){selectItem(item);}

            });
            itemSelectorArea.getChildren().add(item);
        }
    }

    private void formatItemSelector(Label item) {
        item.setScaleX(2);
        item.setScaleY(2);
        item.setMaxWidth(60);
        item.setMinWidth(40);
        System.out.println("This is happening too");
        item.setTextFill(Color.web("0xffffff"));
        item.setFont(darwinFont);
    }

    private void createHeroVitals(){
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
        if(!uiLocked){
            pushMessage("Who will " + team.get(selectedUser).getName() + " attack?");
            enemySelectorArea.setVisible(true);
        }

    }

    public void selectEndTurn(javafx.scene.input.MouseEvent event) {
        if(!uiLocked){
            battleLogic.endPlayerTurn();
        }

    }

    public void selectSkill(javafx.scene.input.MouseEvent event) {

    }

    public void selectBag(javafx.scene.input.MouseEvent event) {
        if(!uiLocked){
            pushMessage("Which item will " + team.get(selectedUser).getName() +
                    " use?");
            itemSelectorArea.setVisible(true);
            actionMenu.setVisible(false);
        }

    }

    private void selectItem(Label item){
        selectedItem = itemSelectorArea.getChildren().lastIndexOf(item);
        battleLogic.useItem(selectedItem);
        updateInventoryUI();

    }

    private void updateInventoryUI(){
        pushMessage(team.get(selectedUser).getName() +  " used the " + inventory.get(selectedItem).getName() + " !");
        itemSelectorArea.getChildren().remove(selectedItem);
        itemSelectorArea.setVisible(false);
        actionMenu.setVisible(true);

    }

    public void selectFlee(javafx.scene.input.MouseEvent event) {

    }

    public boolean detectEnemyKO() {
        //This really needs to be moved to BattleManager
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

    private void selectHero(Label hero) {
        selectedUser = Integer.parseInt(hero.getId());
        heroSelectorArea.setVisible(false);
        showActionMenu();
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

        for(int i = 0; i<allEnemyVitals.getChildren().size();i++){
            VBox enemyVital = (VBox) allEnemyVitals.getChildren().get(i);
            Label enemyHP = (Label)enemyVital.getChildren().get(1);
            enemyHP.setText("HP: " + enemyTeam.get(i).getHp()+"/"+enemyTeam.get(i).getMaxHP());
        }
    }

    public void updateHeroVitals(){
        for(int i = 0; i< allHeroVitals.getChildren().size(); i++){
            VBox heroVital = (VBox) allHeroVitals.getChildren().get(i);
            Label heroHP = (Label)heroVital.getChildren().get(1);
            heroHP.setText("HP: " + team.get(i).getHp()+"/"+ team.get(i).getMaxHP());
        }
    }

    public void updateTP(double percentage){
        tpBar.setProgress(percentage);
        tpDisplay.setText("TP: " + gameData.getCurrentTp() + "/" + gameData.getMaxTP());
        battleLogic.checkPlayerTP();
    }

    private void showActionMenu() {
        actionMenu.setVisible(true);
        backButton.setVisible(true);
        pushMessage("What will " + team.get(selectedUser).getName() + " do?" );
    }

    public void goBack(ActionEvent event) {
        selectorMenu.setVvalue(0);
        if(actionMenu.isVisible()){
            System.out.println("Does this happen?");
            heroSelectorArea.setVisible(true);
            actionMenu.setVisible(false);
            enemySelectorArea.setVisible(false);

        }
        else if(itemSelectorArea.isVisible()){
            actionMenu.setVisible(true);
            itemSelectorArea.setVisible(false);

        }

    }

    //endregion


}
