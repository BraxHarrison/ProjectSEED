package GUI;

import edu.bsu.cs222.FPBreetlison.BattleManager;
import edu.bsu.cs222.FPBreetlison.Fighter;
import edu.bsu.cs222.FPBreetlison.GameManager;
import edu.bsu.cs222.FPBreetlison.GameData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BattleController {


    //region Variables

    public HBox heroSelectorArea;

    public HBox actionMenu;
    public Button attack;
    public Button skills;
    public Button items;
    public Button flee;
    public VBox allHeroVitals;
    public VBox allEnemyVitals;
    public ScrollPane objectsMenu;
    public HBox enemySelectorArea;
    public Label historyDisplay;
    public ProgressBar tpBar;
    public Label tpDisplay;

    GameData gameData;
    GameManager game;
    BattleManager battleLogic;


    public Label mainDisplay;
    //endregion
    //region Utility Functions

    public void pushMessage(String message){
        historyDisplay.setText(mainDisplay.getText() + "\n\n" + historyDisplay.getText());

        mainDisplay.setText(message);

       // historyDisplay.setVvalue(mainScroller.getVmax());
    }
    public void delayFunction(long time){
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //endregion
    //region Initialization Functions

    public void initialize(GameManager game){
        this.game = game;
        this.gameData = game.getGameData();
        this.battleLogic = game.getBattleLogic();
        //I don't really like this. I feel like this should be done from GameManager
        battleLogic.getGameInfo(game);
        setupBattle();
    }

    private void setupBattle(){
        createHeroButtons();
        createHeroVitals();
        createEnemyVitals();
        createEnemySelectors();
        pushMessage("An enemy group led by " + gameData.getEnemyTeam().get(0).getName()
                + " appears!");
        battleLogic.start();
    }

    private void createHeroButtons() {

        ArrayList<Fighter> team = gameData.getTeam();
        for(int i = 0; i<team.size();i++){
            Button Hero = new Button(team.get(i).getName());
            Hero.setText(team.get(i).getName());
            Hero.setId(Integer.toString(i));
            Hero.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    selectHero(Hero);
                }
            });
            heroSelectorArea.getChildren().add(Hero);
        }
    }

    private void selectHero(Button hero) {
        gameData.setSelectedUser(Integer.parseInt(hero.getId()));
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

    public void selectAttack(ActionEvent actionEvent) {
        pushMessage("Who will " + gameData.getTeam().get(gameData.getSelectedUser()).getName() + " attack?");
        showSelector(enemySelectorArea);
    }

    public void createEnemySelectors() {
        ArrayList<Fighter> enemyTeam = gameData.getEnemyTeam();
        for(int i = 0; i<enemyTeam.size();i++){
            Button enemy = new Button(enemyTeam.get(i).getName());
            enemy.setId(Integer.toString(i));
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

    public void hideSelector(HBox selector){
        selector.setVisible(false);
    }

    public void showSelector(HBox selector){
        selector.setVisible(true);
    }

    public boolean detectHeroKO() {
        ObservableList<Node> selectors = heroSelectorArea.getChildren();
        ArrayList<Fighter> fighters = gameData.getTeam();
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
        setHeroButtonsFullOpacity();
    }

    private void triggerAttack() {

        battleLogic.tryBasicAttack();
        updateEnemyVitals();

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

    public void selectSkill(ActionEvent actionEvent) {

    }

    public void selectItems(ActionEvent actionEvent) {

    }

    public void selectFlee(ActionEvent actionEvent) {

    }
    public void setHeroButtonsFullOpacity(){
        ObservableList<Node> heroButtons = heroSelectorArea.getChildren();
        for(int i = 0; i < heroButtons.size(); i++){
            Button button = (Button) heroButtons.get(i);
            button.setOpacity(1);
        }
    }

    private void setHeroButtonOpacity(){
        ObservableList<Node> heroButtons = heroSelectorArea.getChildren();
        for(int i = 0; i < heroButtons.size(); i++){
            Button button = (Button) heroButtons.get(i);
            button.setOpacity(.5);
        }
        if(!heroButtons.isEmpty()){
            heroButtons.get(gameData.getSelectedUser()).setOpacity(1);
        }


    }

    private void showActionMenu() {
        actionMenu.setVisible(true);
        setHeroButtonOpacity();
        pushMessage("What will " + gameData.getTeam().get(gameData.getSelectedUser()).getName() + " do?" );
    }

    public void selectEndTurn(ActionEvent actionEvent) {

        battleLogic.endPlayerTurn();
        setHeroButtonsFullOpacity();
    }

    //endregion


}
