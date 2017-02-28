package GUI;

import edu.bsu.cs222.FPBreetlison.Fighter;
import edu.bsu.cs222.FPBreetlison.GameData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BattleController {


    //region Variables
    public HBox characterMenu;
    public Button character1;
    public Button character2;
    public Button character3;
    public Button character4;

    public HBox actionMenu;
    public Button attack;
    public Button skills;
    public Button items;
    public Button flee;
    public HBox allCharacterVitals;
    public HBox allEnemyVitals;
    public ScrollPane objectsMenu;
    public HBox selectorArea;
    public Label historyDisplay;

    private int selectedCharacter;
    private int selectedAction;
    private int selectedItem;
    private int selectedAttack;
    private boolean canAdvanceText;
    private ObservableList<Node> buttons;

    GameData gameData = GameData.getData();

    public Label mainDisplay;
    //endregion
    //region Utility Functions
    //Generic functions that are useful in many cases
    private void delayFunction(long time){
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void pushMessage(String message){
        historyDisplay.setText(mainDisplay.getText() + "\n\n" + historyDisplay.getText());

        mainDisplay.setText(message);

       // historyDisplay.setVvalue(mainScroller.getVmax());
    }

    //endregion
    //region Initialization Functions
    @FXML
    public void initialize(){
        setCharacterButtons();
        createCharacterVitals();
        createEnemyVitals();
        pushMessage("Two enemy Jags appear!");
    }
//    private void bindScroller(){
//        DoubleProperty vProperty = new SimpleDoubleProperty();
//        vProperty.bind(mainDisplay.hmaxProperty());
//    }
    private void setCharacterButtons() {
        ArrayList<Fighter> team = gameData.getTeam();
        buttons = characterMenu.getChildren();
        for(int i = 0; i < team.size();i++){
            Button button = (Button)buttons.get(i);
            button.setText(team.get(i).getName());
            button.setVisible(true);
        }
    }
    private void createCharacterVitals(){
        ArrayList<Fighter> team = gameData.getTeam();
        for(int i = 0; i < team.size();i++){
            Label name = new Label(team.get(i).getName());
            Label hp = new Label("HP: "+team.get(i).getHp());

            VBox characterVitals = new VBox();
            characterVitals.getChildren().add(name);
            characterVitals.getChildren().add(hp);
            allCharacterVitals.getChildren().add(characterVitals);
        }
    }
    private void createEnemyVitals(){
        ArrayList<Fighter> enemyTeam = gameData.getEnemyTeam();
        for(int i = 0; i < enemyTeam.size();i++){
            Label name = new Label(enemyTeam.get(i).getName());
            Label hp = new Label("HP: "+ enemyTeam.get(i).getHp());

            VBox enemyVitals = new VBox();
            enemyVitals.getChildren().add(name);
            enemyVitals.getChildren().add(hp);
            allEnemyVitals.getChildren().add(enemyVitals);
        }
    }

    //endregion
    //region Button Logic
    public void selectCharacter1(ActionEvent actionEvent) {

        selectedCharacter=0;
        showActionMenu();
    }
    public void selectCharacter2(ActionEvent actionEvent) {
        selectedCharacter=1;
        showActionMenu();
    }
    public void selectCharacter3(ActionEvent actionEvent) {
        selectedCharacter=2;
        showActionMenu();
    }
    public void selectCharacter4(ActionEvent actionEvent) {
        selectedCharacter=3;
        showActionMenu();
    }

    public void selectAttack(ActionEvent actionEvent) {
        pushMessage("Who will " + gameData.getTeam().get(selectedCharacter).getName() + " attack?");
        showEnemySelectors();
    }

    private void showEnemySelectors() {
        selectorArea.getChildren().clear();
        ArrayList<Fighter> enemyTeam = gameData.getEnemyTeam();
        for(int i = 0; i<enemyTeam.size();i++){
            Button enemy = new Button("Jag");
            enemy.setId(Integer.toString(i));
            enemy.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    selectEnemy(enemy);
                }
            });
            selectorArea.getChildren().add(enemy);
        }
    }

    private void selectEnemy(Button selected) {
        int id = Integer.parseInt(selected.getId());
        triggerAttack(id);
    }

    private void triggerAttack(int id) {
        Fighter attacker = gameData.getTeam().get(selectedCharacter);
        attacker.doBasicAttack(gameData.getEnemyTeam().get(id));
        pushMessage(attacker.generateAttackDescription());
        updateEnemyVitals();
    }

    private void updateEnemyVitals() {
        ArrayList<Fighter> enemyTeam = gameData.getEnemyTeam();
        for(int i = 0; i<allEnemyVitals.getChildren().size();i++){
            VBox enemyVital = (VBox) allEnemyVitals.getChildren().get(i);
            Label enemyHP = (Label)enemyVital.getChildren().get(1);
            enemyHP.setText("HP: " + enemyTeam.get(i).getHp());
        }
    }
    private void updateTP(){

    }

    public void selectSkill(ActionEvent actionEvent) {

    }

    public void selectItems(ActionEvent actionEvent) {

    }

    public void selectFlee(ActionEvent actionEvent) {

    }

    private void setCharacterButtonOpacity(){
        for(int i = 0; i < buttons.size(); i++){
            Button button = (Button)buttons.get(i);
            button.setOpacity(.5);
        }
        buttons.get(selectedCharacter).setOpacity(1);

    }
    private void showActionMenu() {
        actionMenu.setVisible(true);
        setCharacterButtonOpacity();
        pushMessage("What will " + gameData.getTeam().get(selectedCharacter).getName() + " do?" );
    }



    //endregion


}
