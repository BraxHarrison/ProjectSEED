package GUI;

import edu.bsu.cs222.FPBreetlison.GameData;
import edu.bsu.cs222.FPBreetlison.GameManager;
import javafx.scene.control.Label;


public class OverworldController {


    GameManager game;
    GameData gameData;
    public Label roomDescription;



    public void initialize(GameManager game){
        this.game = game;
        this.gameData = game.getGameData();
        roomDescription.setText(gameData.getCurrentRoom().getDescription());
    }
    public void startBattle(){
        game.createBattle();
    }
}
