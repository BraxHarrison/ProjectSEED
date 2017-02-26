package GUI;

import com.sun.javafx.collections.ObservableSequentialListWrapper;
import edu.bsu.cs222.FPBreetlison.Character;
import edu.bsu.cs222.FPBreetlison.GameController;
import edu.bsu.cs222.FPBreetlison.GameData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class OverworldController {


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

    private int selectedCharacter;
    private int selectedAction;
    private int selectedItem;
    private int selectedAttack;

    GameData gameData = GameData.getData();
    GameController game = new GameController();

    public Label mainDisplay;
    //endregion

    @FXML
    public void initialize(){
        setCharacterButtons();
    }
    private void setCharacterButtons() {
        ArrayList<Character> team = gameData.getTeam();
        ObservableList<Node> buttons = characterMenu.getChildren();
        hideAllButtons(buttons);
        for(int i = 0; i < team.size();i++){
            Button button = (Button)buttons.get(i);
            button.setText(team.get(i).getName());
            button.setVisible(true);
        }
    }
    private void hideAllButtons(ObservableList<Node> buttons){
        for(int i = 0; i < 3; i++){
            Button button = (Button)buttons.get(i);
            button.setVisible(false);
        }
    }

    public void characterSelect(ActionEvent actionEvent) {
        Button selectedButton = (Button) actionEvent.getSource();
        String selectedCharacter = selectedButton.getId();
        showCharacterMenu(selectedCharacter);

    }

    private void showCharacterMenu(String selectedCharacter) {

    }

    private int processIDText(String id) {
        id.replaceAll("button","");
        return Integer.parseInt(id);
    }

    public void actionSelect(ActionEvent actionEvent) {
    }
    private void displayAttacks(){
        //get character attacks
        //While iterating through list of attacks:
        //create button
        //.getChildren().add(button);
    }
    private void displayItems(){

    }
    private void generateButtons(){



    }


    public void selectCharacter1(ActionEvent actionEvent) {
        selectedCharacter=0;
    }
    public void selectCharacter2(ActionEvent actionEvent) {
        selectedCharacter=1;
    }
    public void selectCharacter3(ActionEvent actionEvent) {
        selectedCharacter=2;
    }
    public void selectCharacter4(ActionEvent actionEvent) {
        selectedCharacter=3;
    }

    public void selectAttack(ActionEvent actionEvent) {

    }

    public void selectSkill(ActionEvent actionEvent) {
    }

    public void selectItems(ActionEvent actionEvent) {
    }

    public void selectFlee(ActionEvent actionEvent) {
    }
}
