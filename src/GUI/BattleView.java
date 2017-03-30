package GUI;

import edu.bsu.cs222.FPBreetlison.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
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
import java.util.Collections;

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
    public Group itemInfoDisplay;

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
        int dur = 0;
        for(int i = 0; i<messages.size();i++){
            String message = messages.get(i);
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(dur),
                    ae -> pushMessage(message)));
            dur += 1000;
        }
        uiLocked = true;
        timeline.play();
    }

    public void queueBarUpdates(ArrayList<DamageState> targets){
        Timeline timeline = new Timeline();
        timeline.setOnFinished(e -> clearBarInfo(targets));
        int dur = 0;
        for(int i = 0; i<targets.size();i++) {
            DamageState heroSnapshot = targets.get(i);
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(dur),
                    ae -> updateHeroBars(heroSnapshot)));
            dur += 1000;
        }
        timeline.play();
    }

    private void clearMessages(ArrayList<String> messages){
        messages.clear();
        uiLocked = false;
    }
    private void clearBarInfo(ArrayList<DamageState> barInfos){
        barInfos.clear();
    }

    public void pushMessage(String message) {
        historyDisplay.setText(mainDisplay.getText() + "\n\n" + historyDisplay.getText());
        mainDisplay.setText(message);

    }


    public void updateHeroBars(DamageState heroSnapshot){
        ObservableList<Node> hSelectors = heroSelectorArea.getChildren();
        System.out.println(team.get(heroSnapshot.getIndex()).getName() + "/" + heroSnapshot.getHpPercent() + "/" + heroSnapshot.getKOState());
        StackPane selector = (StackPane)hSelectors.get(heroSnapshot.getIndex());
        ProgressBar hbar = (ProgressBar)selector.getChildren().get(1);
        hbar.setProgress(heroSnapshot.getHpPercent());
        accountForRounding(hbar,heroSnapshot);
        if(heroSnapshot.getKOState()){
            removeHero(heroSnapshot.getIndex());
        }

    }

    private void startLoader(){
        loaderScreen.toFront();
        loaderScreen.setStyle("-fx-background-color: black;");
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
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
            StackPane hero =  new StackPane();
            hero.setId(Integer.toString(i));
            populateHero(hero);
            formatHeroButton((Label)hero.getChildren().get(0));
            formatHeroBar((ProgressBar)hero.getChildren().get(1));
            hero.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent event) {
                    selectHero(hero);
                }
            });
            heroSelectorArea.getChildren().add(hero);
        }
    }

    private void populateHero(StackPane hero) {
        int index = Integer.parseInt(hero.getId());
        Label hLabel = new Label(team.get(index).getName());
        ProgressBar hbar = new ProgressBar();
        hero.getChildren().add(hLabel);
        hero.getChildren().add(hbar);

    }

    private void formatHeroButton(Label hero) {
        hero.setScaleX(3);
        hero.setScaleY(3);
        hero.setTranslateX(-30);
        hero.setMaxWidth(40);
        hero.setMinWidth(40);
        hero.setTextFill(Color.web("0xfffff1"));
        hero.setFont(darwinFont);
    }

    private void formatHeroBar(ProgressBar hBar) {
        hBar.setRotate(270);
        hBar.setScaleX(.30);
        hBar.setScaleY(.80);
        hBar.setTranslateX(26);
//        hBar.setTranslateY(2);
        hBar.getStyleClass().add("healthBar");
        hBar.getStyleClass().add("green-bar");
        hBar.setProgress(1);

    }

    private void accountForRounding(ProgressBar hbar, DamageState heroSnapshot) {
        if(heroSnapshot.getHpPercent() < .07 && heroSnapshot.getHpPercent() > 0){
            hbar.setProgress(0.1);
        }
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
            Label item = new Label(inventory.get(i).getName());
            item.setId(Integer.toString(i));
            formatItemSelector(item);
            setupMouseEventsForItem(item);
            itemSelectorArea.getChildren().add(item);
        }
    }

    private void setupMouseEventsForItem(Label item) {
        item.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>(){
            @Override
            public void handle(javafx.scene.input.MouseEvent event){selectItem(item);}

        });
        item.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {hoverItem(item);}
        });
        item.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){exitItem();}
        });
    }

    private void exitItem() {
        itemInfoDisplay.setVisible(false);
    }

    private void hoverItem(Label item) {
        selectedItem = Integer.parseInt(item.getId());
        itemInfoDisplay.setVisible(true);
        loadItemImage();
        loadItemDescription();
    }

    private void loadItemImage() {
        ImageView infoGraphic = (ImageView) itemInfoDisplay.getChildren().get(0);
        infoGraphic.setImage(new Image(inventory.get(selectedItem).getImagePath()));
        infoGraphic.setFitWidth(50);
        infoGraphic.setFitHeight(50);
    }

    private void formatItemSelector(Label item) {
        item.setScaleX(2);
        item.setScaleY(2);
        item.setMaxWidth(60);
        item.setMinWidth(40);
        item.setTextFill(Color.web("0xffffff"));
        item.setFont(darwinFont);
    }

    private void loadItemDescription() {
        Label quickSummary = (Label) itemInfoDisplay.getChildren().get(1);
        Label infoText = (Label) itemInfoDisplay.getChildren().get(2);
        quickSummary.setText(inventory.get(selectedItem).getQuickSummary());
        infoText.setText(inventory.get(selectedItem).getDescription());

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
        updateInventoryUI();
        battleLogic.useItem(selectedItem);

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
            fighters.get(i).checkKOLevel();
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

    private void selectHero(StackPane hero) {
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

    public void removeHero(int index){
        StackPane hero = (StackPane) heroSelectorArea.getChildren().get(index);
        Label heroLabel = (Label)hero.getChildren().get(0);
        heroLabel.setTextFill(Color.web("0x333c47"));
        hero.setOnMousePressed(null);
        battleLogic.getMessageQueue().add(team.get(index).getName() + " is down!");
    }

    public void goBack(ActionEvent event) {
        selectorMenu.setVvalue(0);
        if(actionMenu.isVisible()){
            heroSelectorArea.setVisible(true);
            actionMenu.setVisible(false);
            enemySelectorArea.setVisible(false);
            backButton.setVisible(false);

        }
        else if(itemSelectorArea.isVisible()){
            actionMenu.setVisible(true);
            itemSelectorArea.setVisible(false);

        }

    }

    //endregion


}
