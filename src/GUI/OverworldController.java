package GUI;

import edu.bsu.cs222.FPBreetlison.GameController;
import edu.bsu.cs222.FPBreetlison.GameData;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static edu.bsu.cs222.FPBreetlison.GameData.gameData;

public class OverworldController {

    GameController game;

    public void initialize(GameController game){
        this.game = game;
    }
    public void startBattle(){
        game.startBattle();
    }
}
