package edu.bsu.cs222.FPBreetlison;

import GUI.BattleController;

import java.util.concurrent.TimeUnit;

public class BattleManager {

    GameManager game;
    GameData gameData;
    BattleController battleControl;

    Fighter attacker;
    Fighter target;
    boolean battleRunning;

    public void getGameInfo(GameManager game){
        this.game = game;
        this.gameData = game.getGameData();
        this.battleControl = game.getBattleControl();

    }

    public void testMethod(){

    }

    public void start(){
        checkSpeeds();
    }

    private void updateTurn(String phase) {
        if(phase.equals("hero")){
            enableCharacterMenu();
        }
        if(phase.equals("enemy")) {
            disableCharacterMenu();
            triggerEnemyAttack();
            resetTP();

        }
    }

    private void resetTP() {
        gameData.resetHeroTP();
        updateTP();
    }

    private void triggerEnemyAttack() {
        battleControl.pushMessage("The enemy team attacks!");
        //Delay currently doesn't work. Find alternative
        //Code for AI(target selection, move selection, etc) goes here
        battleControl.pushMessage("Enemy turn concluded");
        updateTurn("hero");

    }

    private void checkSpeeds() {
        //Check compare the fastest members from each team to see which team goes first
        String phase = "hero";
        updateTurn(phase);
    }

    private void enableCharacterMenu() {
        battleControl.characterMenu.setVisible(true);
    }
    private void disableCharacterMenu(){
        battleControl.characterMenu.setVisible(false);
        battleControl.actionMenu.setVisible(false);
    }

    public void checkPlayerTP(){
        if(gameData.getCurrentTp() <= 0){
            updateTurn("enemy");
        }
    }

    public void tryBasicAttack(){
        attacker = gameData.getTeam().get(gameData.getSelectedUser());
        target = gameData.getEnemyTeam().get(gameData.getSelectedTarget());
        int cost = attacker.getTpCost();
        if(gameData.getCurrentTp() >= cost ){
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
        updateTP();
    }

    public void updateTP() {
        battleControl.tpBar.setProgress((double)gameData.getCurrentTp()/(double)gameData.getMaxTP());
        battleControl.tpDisplay.setText("TP: " + gameData.getCurrentTp() + "/" + gameData.getMaxTP());
        checkPlayerTP();
    }

    public void checkEnemyTP(){

    }

    public void endPlayerTurn() {
        updateTurn("enemy");
    }
}
