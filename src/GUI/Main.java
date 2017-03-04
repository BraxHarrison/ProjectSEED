package GUI;

import edu.bsu.cs222.FPBreetlison.GameController;
import edu.bsu.cs222.FPBreetlison.GameData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

GameData gameData = new GameData();
GameController game = new GameController();

    @Override
    public void start(Stage primaryStage) throws Exception{
        gameData.setStage(primaryStage);
        //gameData.setGame(game);
        game.init(gameData);
        game.play();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
