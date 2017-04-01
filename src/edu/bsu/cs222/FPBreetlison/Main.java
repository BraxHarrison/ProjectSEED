package edu.bsu.cs222.FPBreetlison;

import edu.bsu.cs222.FPBreetlison.Model.GameManager;
import edu.bsu.cs222.FPBreetlison.Model.GameData;
import javafx.application.Application;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main extends Application {

GameData gameData = new GameData();
GameManager game = new GameManager();

    public Main() throws ParserConfigurationException, SAXException, IOException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        gameData.setStage(primaryStage);
        game.init(gameData);
        game.play();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
