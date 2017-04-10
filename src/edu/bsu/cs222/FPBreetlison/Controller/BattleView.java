package edu.bsu.cs222.FPBreetlison.Controller;

import edu.bsu.cs222.FPBreetlison.Model.BattleManager;
import edu.bsu.cs222.FPBreetlison.Model.GameData;
import edu.bsu.cs222.FPBreetlison.Model.GameManager;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Fighter;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Item;

import edu.bsu.cs222.FPBreetlison.Model.Objects.Skill;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Snapshot;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Shear;
import javafx.util.Duration;

import java.util.ArrayList;

public class BattleView {
    public VBox skillSelectorArea;
    public Group skillInfoDisplay;

    //region Variables

    private ArrayList<Fighter> team;
    private ArrayList<Fighter> enemyTeam;
    private ArrayList<Item> inventory;

    public VBox heroSelectorArea;
    public HBox heroGraphicsArea;
    public VBox actionMenu;
    public HBox enemySelectorArea;
    public VBox itemSelectorArea;
    public Label mainDisplay;
    public Label historyDisplay;
    public ProgressBar tpBar;
    public Label tpDisplay;
    public HBox backgroundImage;
    public ScrollPane selectorMenu;
    public Pane loaderScreen;
    public StackPane battleDisplay;
    public Button backButton;
    public Group itemInfoDisplay;
    public Group battlerInfoDisplay;


    public int selectedUser;
    private int selectedItem;
    public boolean uiLocked;
    private boolean finishedLoading;

    private GameData gameData;
    private BattleManager battleLogic;

    private Font darwinFont;
    private boolean usingSkill;


    //endregion
    //region UI Animation

    public void queueMessages(ArrayList<String> messages){
        Timeline timeline = new Timeline();
        timeline.setOnFinished(e -> clearMessages(messages));
        int dur = 40;
        for (String message : messages) {
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(dur),
                    ae -> pushMessage(message)));
            dur += 1000;
        }
        uiLocked = true;
        timeline.play();
    }

    public void queueBarUpdates(ArrayList<Snapshot> targets){
        Timeline timeline = new Timeline();
        timeline.setOnFinished(e -> clearBarInfo(targets));
        int dur = 40;
        for (Snapshot heroSnapshot : targets) {
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
    private void clearBarInfo(ArrayList<Snapshot> barInfos){
        barInfos.clear();
    }

    public void pushMessage(String message) {
        historyDisplay.setText(mainDisplay.getText() + "\n\n" + historyDisplay.getText());
        mainDisplay.setText(message);

    }

    private void updateHeroBars(Snapshot heroSnapshot){
        StackPane selector = (StackPane)heroSelectorArea.getChildren().get(heroSnapshot.getIndex());
        ProgressBar hBar = (ProgressBar)selector.getChildren().get(1);
        hBar.setProgress(heroSnapshot.getHpPercent());
        roundHPPercent(hBar,heroSnapshot);
        updateHeroQuickInfo(heroSnapshot);
        updateColor(hBar,heroSnapshot.getHpPercent());
        if(heroSnapshot.getKOState()){
             removeHero(heroSnapshot.getIndex());
        }
    }

    private void updateHeroQuickInfo(Snapshot heroSnapshot){
        ImageView heroImage = (ImageView)heroGraphicsArea.getChildren().get(heroSnapshot.getIndex());
        if(heroImage.isHover()){
            Label hpInfo = (Label)battlerInfoDisplay.getChildren().get(1);
            hpInfo.setText(heroSnapshot.getHpString());
        }
    }

    private void updateColor(ProgressBar hbar, Double hpPercent) {
        if(hpPercent < .60 && hpPercent >= .30){
            hbar.getStyleClass().remove(2);
            hbar.getStyleClass().add("yellow-bar");
        }
        else if(hpPercent < .30){
            hbar.getStyleClass().remove(2);
            hbar.getStyleClass().add("red-bar");
        }
        else{
            hbar.getStyleClass().remove(2);
            hbar.getStyleClass().add("green-bar");
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
        readBattleData();
        startLoader();
        loadFonts();
        setBackground();
        formatDisplayArea();
        setupBattle();
    }

    private void transferBattleData(GameManager game) {
        this.gameData = game.getGameData();
        this.battleLogic = game.getBattleLogic();
        battleLogic.getGameInfo(game);

    }

    private void readBattleData() {
        team = gameData.getTeam();
        enemyTeam = gameData.getEnemyTeam();
        inventory = gameData.getInventory();

    }


    private void loadFonts() {
        darwinFont = Font.loadFont(getClass().getResource("/fonts/Darwin.ttf").toExternalForm(), 10);
    }

    private void formatDisplayArea() {

        mainDisplay.setTextFill(Color.web("0x000000"));
        mainDisplay.setFont(darwinFont);
        formatTPArea();
        for(int i = 0; i < actionMenu.getChildren().size();i++){
            Label action = (Label)actionMenu.getChildren().get(i);
            action.setFont(darwinFont);
        }

    }

    private void formatTPArea() {
        tpBar.getTransforms().add(new Shear(0.95, 0));
        tpDisplay.setFont(darwinFont);
        tpDisplay.getStyleClass().add("black-text");

    }

    private void setBackground() {
        BackgroundImage battleBack = new BackgroundImage(new Image("images/Plains_Day.png",900,500,false,true), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        backgroundImage.setBackground(new Background(battleBack));
        backgroundImage.toBack();

    }

    private void setupBattle(){
        createHeroButtons();
        createHeroGraphics();
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
            populateHeroUIElements(hero);
            formatHeroButton((Label)hero.getChildren().get(0));
            formatHeroBar((ProgressBar)hero.getChildren().get(1));
            hero.setOnMousePressed(event -> selectHero(hero));
            heroSelectorArea.getChildren().add(hero);
        }
    }

    private void populateHeroUIElements(StackPane hero) {
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
        hBar.getStyleClass().add("healthBar");
        hBar.getStyleClass().add("green-bar");
        hBar.setProgress(1);

    }

    private void roundHPPercent(ProgressBar hbar, Snapshot heroSnapshot) {
        if(heroSnapshot.getHpPercent() < .07 && heroSnapshot.getHpPercent() > 0){
            hbar.setProgress(0.1);
        }
    }

    private void createHeroGraphics() {
        for (int i = 0; i < team.size(); i++) {
            ImageView image = new ImageView(new Image(team.get(i).getBattlerGraphicPath()));
            image.setId(Integer.toString(i));
            image.setFitHeight(team.get(i).getSizeY());
            image.setFitWidth(team.get(i).getSizeX());
            image.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    showCharacterInfo(image);
                }
            });
            image.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    hideCharacterInfo();
                }
            });
            heroGraphicsArea.getChildren().add(image);
        }
    }

    private void showCharacterInfo(ImageView image){
        battlerInfoDisplay.setVisible(true);
        int index = Integer.parseInt(image.getId());
        showCharacterMiniImage(index);
        showHeroUpperLabels(index);
        showHeroLowerLabels();

    }

    private void showCharacterMiniImage(int index){
        ImageView display = (ImageView)battlerInfoDisplay.getChildren().get(0);
        display.setImage(new Image(team.get(index).getMiniGraphicPath()));
        display.setFitHeight(80);
        display.setFitWidth(80);
    }
    private void showHeroUpperLabels(int index){
        Label name = (Label)battlerInfoDisplay.getChildren().get(1);
        Label hp = (Label)battlerInfoDisplay.getChildren().get(2);
        name.setText(team.get(index).getName());
        hp.setText("HP: " + team.get(index).getCurrStats().get("hp") + "/" + team.get(index).getMaxHP());
    }
    private void showHeroLowerLabels(){

    }

    private void hideCharacterInfo(){
        battlerInfoDisplay.setVisible(false);
    }

    private void createEnemySelectors() {
        for(int i = 0; i<enemyTeam.size();i++){
            ImageView enemy = new ImageView(new Image(enemyTeam.get(i).getBattlerGraphicPath()));
            enemy.setId(Integer.toString(i));
            populateEnemyUIElements(enemy);
            enemy.setOnMouseEntered(event -> showEnemyInfo(enemy));
            enemy.setOnMouseExited(event -> hideEnemyInfo());
            enemySelectorArea.getChildren().add(enemy);

        }
    }

    private void populateEnemyUIElements(ImageView enemy) {
        int index = Integer.parseInt(enemy.getId());
        enemy.setFitHeight(enemyTeam.get(index).getSizeY());
        enemy.setFitWidth(enemyTeam.get(index).getSizeX());

    }

    private void selectEnemy(ImageView enemy) {
        int index = enemySelectorArea.getChildren().indexOf(enemy);
        gameData.setSelectedTarget(index);
        if(usingSkill){
            triggerSkill(index);
        }
        else{
            triggerAttack();
        }

    }

    private void showEnemyInfo(ImageView enemy){
        int index = Integer.parseInt(enemy.getId());
        battlerInfoDisplay.setVisible(true);
        showEnemyMiniImage(index);
        showEnemyUpperLabels(index);

    }

    private void showEnemyMiniImage(int index){
        ImageView display = (ImageView)battlerInfoDisplay.getChildren().get(0);
        display.setImage(new Image(enemyTeam.get(index).getMiniGraphicPath()));
        display.setFitHeight(80);
        display.setFitWidth(80);
    }
    private void showEnemyUpperLabels(int index){
        Label name = (Label)battlerInfoDisplay.getChildren().get(1);
        Label hp = (Label)battlerInfoDisplay.getChildren().get(2);
        name.setText(enemyTeam.get(index).getName());
        hp.setText("HP: " + enemyTeam.get(index).getCurrStats().get("hp") + "/" + enemyTeam.get(index).getMaxHP());
    }


    private void hideEnemyInfo(){
        battlerInfoDisplay.setVisible(false);
    }

    private void createItemsButtons(){
        for(int i = 0; i<gameData.getInventory().size();i++){
            Label item = new Label(inventory.get(i).getName());
            item.setId(Integer.toString(i));
            formatItemSelector(item);
            setupMouseEventsForItem(item);
            itemSelectorArea.getChildren().add(item);
        }
    }

    private void setupMouseEventsForItem(Label item) {
        item.setOnMousePressed(event -> selectItem(item));
        item.setOnMouseEntered(event -> hoverItem(item));
        item.setOnMouseExited(event -> exitItem());
    }

    private void exitItem() {
        itemInfoDisplay.setVisible(false);
    }

    private void hoverItem(Label item) {
        int index = itemSelectorArea.getChildren().indexOf(item);
        itemInfoDisplay.setVisible(true);
        loadItemImage(index);
        loadItemDescription(index);
    }

    private void loadItemImage(int index) {
        ImageView infoGraphic = (ImageView) itemInfoDisplay.getChildren().get(0);
        infoGraphic.setImage(new Image(gameData.getInventory().get(index).getImagePath()));
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

    private void loadItemDescription(int index) {
        Label quickSummary = (Label) itemInfoDisplay.getChildren().get(1);
        Label infoText = (Label) itemInfoDisplay.getChildren().get(2);
        quickSummary.setText(gameData.getInventory().get(index).getQuickSummary());
        infoText.setText(gameData.getInventory().get(index).getDescription());

    }

    //endregion
    //region Button Logic

    public void selectAttack() {
        if(!uiLocked){
            usingSkill = false;
            pushMessage("Who will " + team.get(selectedUser).getName() + " attack?");
            unblockEnemySelectors();

        }

    }

    public void selectEndTurn() {
        if(!uiLocked){
            battleLogic.endPlayerTurn();
        }

    }

    public void selectSkills() {
        populateSkills();
        skillSelectorArea.setVisible(true);
        actionMenu.setVisible(false);
        System.out.println(team.get(selectedUser).getSkillList());

    }

    private void populateSkills() {
        finishedLoading = false;
        skillSelectorArea.getChildren().clear();
        Fighter user = team.get(selectedUser);
        for (int i = 0; i<user.getSkillList().size();i++){
            Label skill = new Label(user.getSkillList().get(i).getName());
            formatSkill(skill);
            skill.setOnMouseEntered(event -> showSkillInfo(skill));
            skill.setOnMouseExited(event -> hideSkillInfo());
            skill.setOnMousePressed(event -> selectSkill(skill));
            skillSelectorArea.getChildren().add(skill);
        }
        finishedLoading = true;
    }

    private void selectSkill(Label skillLabel) {
        Fighter user = team.get(selectedUser);
        usingSkill = true;
        int index = skillSelectorArea.getChildren().indexOf(skillLabel);
        Skill skill = team.get(selectedUser).getSkillList().get(index);
        user.setQueuedSkill(skill);
        checkSkillType(skill);



    }

    private void checkSkillType(Skill skill) {
        Fighter user = team.get(selectedUser);
        if(skill.getType().equals("buff")){
            battleLogic.tryActivateSkill(user,user);
        }
        else{
            pushMessage("Who will " + user.getName() + " use this skill on?");
            unblockEnemySelectors();
        }

    }

    private void hideSkillInfo() {
        skillInfoDisplay.setVisible(false);
    }

    private void formatSkill(Label skill) {
        skill.setScaleX(2);
        skill.setScaleY(2);
        skill.setTranslateX(-8);
        skill.setMaxWidth(60);
        skill.setMinWidth(40);
        skill.setTextFill(Color.web("0xfffff1"));
        skill.setFont(darwinFont);
    }

    private void showSkillInfo(Label skill) {

        int index = skillSelectorArea.getChildren().indexOf(skill);
        System.out.println(index);
        Label skillQuickInfo = (Label)skillInfoDisplay.getChildren().get(1);
        System.out.println(team.get(selectedUser).getSkillList().get(index).getName());
        skillQuickInfo.setText(team.get(selectedUser).getSkillList().get(index).getQuickInfo());
        skillInfoDisplay.setVisible(true);


    }

    public void selectBag() {
        if(!uiLocked){
            pushMessage("Which item will " + team.get(selectedUser).getName() +
                    " use?");
            itemSelectorArea.setVisible(true);
            actionMenu.setVisible(false);
        }

    }

    private void selectItem(Label item){
        selectedItem = itemSelectorArea.getChildren().indexOf(item);
        updateInventoryUI();
        battleLogic.useItem(selectedItem);
        updateSingleHeroBar();


    }

    private void updateSingleHeroBar() {
        StackPane hero = (StackPane)heroSelectorArea.getChildren().get(selectedUser);
        ProgressBar heroBar = (ProgressBar)hero.getChildren().get(1);
        Fighter user = team.get(selectedUser);
        Double hpPercentage = (double)user.getHp()/(double)user.getMaxHP();
        heroBar.setProgress(hpPercentage);
        updateColor(heroBar,hpPercentage);
    }

    private void updateInventoryUI(){
        pushMessage(team.get(selectedUser).getName() +  " used the " + gameData.getInventory().get(selectedItem).getName() + " !");
        itemSelectorArea.getChildren().remove(selectedItem);
        itemSelectorArea.setVisible(false);
        heroSelectorArea.setVisible(true);

    }

    public void selectFlee(javafx.scene.input.MouseEvent event) {
        pushMessage("You ran away. Everyone is disappointed.");
        battleLogic.endBattle();
    }


    private void selectHero(StackPane hero) {
        if(!uiLocked){
            selectedUser = Integer.parseInt(hero.getId());
            heroSelectorArea.setVisible(false);
            showActionMenu();
        }

    }


    private void triggerSkill(int index){
        Fighter user = team.get(selectedUser);
        Fighter target = enemyTeam.get(index);
        battleLogic.tryActivateSkill(user,target);
        skillSelectorArea.setVisible(false);
        skillInfoDisplay.setVisible(false);
        heroSelectorArea.setVisible(true);
    }

    private void triggerAttack() {

        battleLogic.tryHeroBasicAttack();
        actionMenu.setVisible(false);
        heroSelectorArea.setVisible(true);

    }

    public void updateTP(){
        double percentage = (double)gameData.getCurrentTp()/(double)gameData.getMaxTP();
        tpBar.setProgress(percentage);
        tpDisplay.setText("TP: " + gameData.getCurrentTp() + "/" + gameData.getMaxTP());
        battleLogic.checkPlayerTP();
    }

    public void blockEnemySelectors(){
        for(int i = 0; i<enemySelectorArea.getChildren().size();i++){
            ImageView selector = (ImageView)enemySelectorArea.getChildren().get(i);
            selector.setOnMousePressed(null);
        }
    }

    private void unblockEnemySelectors(){
        for(int i = 0; i<enemySelectorArea.getChildren().size();i++){
            ImageView selector = (ImageView)enemySelectorArea.getChildren().get(i);
            selector.setOnMousePressed(event -> selectEnemy(selector));
        }
    }

    private void showActionMenu() {
        actionMenu.setVisible(true);
        backButton.setVisible(true);
        pushMessage("What will " + team.get(selectedUser).getName() + " do?" );
    }

    private void removeHero(int index){
        StackPane hero = (StackPane) heroSelectorArea.getChildren().get(index);
        Label heroLabel = (Label)hero.getChildren().get(0);
        heroLabel.setTextFill(Color.web("0x333c47"));
        hero.setOnMousePressed(null);
        heroGraphicsArea.getChildren().get(index).setOpacity(.35);
        battleLogic.getMessageQueue().add(team.get(index).getName() + " is down!");
    }

    public void goBack() {
        selectorMenu.setVvalue(0);
        if(actionMenu.isVisible()){
            heroSelectorArea.setVisible(true);
            actionMenu.setVisible(false);
            blockEnemySelectors();
            backButton.setVisible(false);

        }
        else if(itemSelectorArea.isVisible()){
            actionMenu.setVisible(true);
            itemSelectorArea.setVisible(false);

        }
        else if(skillSelectorArea.isVisible()){
            actionMenu.setVisible(true);
            skillSelectorArea.setVisible(false);
        }

    }

    public void updateEnemyQuickInfo(Snapshot enemyState) {
        if(enemySelectorArea.getChildren().get(enemyState.getIndex()).isHover()){
            Fighter enemy = enemyTeam.get(enemyState.getIndex());
            Label enemyHP = (Label)battlerInfoDisplay.getChildren().get(2);
            System.out.println(team.get(0).getCurrStats().get("hp")+"/"+team.get(0).getHp());
            enemyHP.setText("HP: " + enemy.getCurrStats().get("hp") + "/" + enemy.getMaxHP());
        }
    }

    //endregion


}
