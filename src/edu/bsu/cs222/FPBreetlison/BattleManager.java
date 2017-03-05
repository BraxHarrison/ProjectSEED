package edu.bsu.cs222.FPBreetlison;

import GUI.BattleController;

public class BattleManager {

    GameController game;
    GameData gameData;
    BattleController battleControl;
    String phase;

    Fighter attacker;
    Fighter target;

    public void getGameInfo(GameController game){
        this.game = game;
        gameData = game.getGameData();
        battleControl = game.getBattleControl();

    }

    public void start(){
        checkSpeeds();
        runStateMachine();
    }

    private void checkSpeeds() {
        //Check compare the fastest members from each team to see which team goes first
        phase = "hero";
    }

    private void runStateMachine(){
        switch(phase){
            case "hero":
                System.out.println("Hero turn!");
                enableCharacterMenu();
                break;
            case "enemy":
                System.out.println("Enemy turn!");
                disableCharacterMenu();
                break;
        }
    }

    private void enableCharacterMenu() {
        battleControl.characterMenu.setVisible(true);
    }
    private void disableCharacterMenu(){
        battleControl.characterMenu.setVisible(false);
    }

    public void checkPlayerTP(){
        if(gameData.getTp() >= 0){
            phase = "enemy";
        }
    }

    public void tryBasicAttack(){
        attacker = gameData.getTeam().get(gameData.getSelectedUser());
        target = gameData.getEnemyTeam().get(gameData.getSelectedTarget());
        int cost = attacker.getTpCost();
        if(gameData.getTp() >= cost ){
            startBasicAttack(cost);
        }
        else{
            battleControl.pushMessage("There's not enough time left for " + attacker.getName()
            + " to attack!");
        }
    }

    private void startBasicAttack(int cost) {
        gameData.subtractTp(cost);
        attacker.doBasicAttack(target);
        battleControl.pushMessage(attacker.getName() + " hit " +  target.getName() + " with a basic attack!");
    }

    public void checkEnemyTP(){

    }
}
