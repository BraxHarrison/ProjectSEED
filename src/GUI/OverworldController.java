package GUI;

import edu.bsu.cs222.FPBreetlison.GameManager;

public class OverworldController {

    GameManager game;

    public void initialize(GameManager game){
        this.game = game;
    }
    public void startBattle(){
        game.createBattle();
    }
}
