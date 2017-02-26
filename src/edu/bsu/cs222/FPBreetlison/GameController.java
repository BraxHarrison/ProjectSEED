package edu.bsu.cs222.FPBreetlison;

public class GameController {

    GameData gameData = GameData.getData();

    public void play(){

        System.out.print("Game is running");
        BattleManager battleManager = new BattleManager();
        battleManager.startBattle();

    }
    public void quit(){
        System.out.println("Game has been quit.");
    }

    private void initialize(){

    }
}
