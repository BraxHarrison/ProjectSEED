package edu.bsu.cs222.FPBreetlison;

import GUI.BattleController;

import java.util.ArrayList;
import java.util.Random;


public class BattleManager {

    private GameManager game;
    private GameData gameData;
    private BattleController battleControl;

    private Fighter attacker;
    private Fighter target;


    public void getGameInfo(GameManager game){
        this.game = game;
        this.gameData = game.getGameData();
        this.battleControl = game.getBattleControl();

    }

    public void start(){
        requestTPUpdate();
        checkSpeeds();
    }

    public void updateTurn(String phase) {
        //Investigate this for possible memory leaks
        if(phase.equals("hero")){
            battleControl.pushMessage("It's your team's turn!");
            enableCharacterMenu();
        }
        else if(phase.equals("enemy")) {
            battleControl.pushMessage("It's the enemy's turn!");
            disableCharacterMenu();
            tryEnemyAttack();
            resetTP();
            endEnemyTurn();
        }
        else if(phase.equals("enemyWin")){
            battleControl.pushMessage("You were defeated!");
            battleControl.hideSelector(battleControl.enemySelectorArea);
        }
        else if(phase.equals("heroWin")){
            battleControl.pushMessage(("You were victorious!"));
            battleControl.hideSelector(battleControl.enemySelectorArea);
        }
    }

    private void resetTP() {
        gameData.resetHeroTP();
        requestTPUpdate();
    }

    private void tryEnemyAttack() {

        //This is horrible someone please fix it
        ArrayList<Fighter> remainingEnemies = getRemainingEnemies();
        for(int i = 0; i<remainingEnemies.size();i++){
            Fighter user = remainingEnemies.get(i);
            Fighter target = gameData.getTeam().get(makeRandom(gameData.getTeam().size()));
            if(target.getKOLvl() > 0){
                target = findNextTarget();
            }
            if(target != null){
                doEnemyAttack(user,target);
            }
        }
    }

    private ArrayList<Fighter> getRemainingEnemies() {
        ArrayList<Fighter> fighters = gameData.getEnemyTeam();
        ArrayList<Fighter> ableFighters = new ArrayList<>();
        for(int i = 0; i < fighters.size();i++){
            if(fighters.get(i).getKOLvl() == 0){
                ableFighters.add(fighters.get(i));
            }
        }
        return ableFighters;
    }

    private void doEnemyAttack(Fighter user, Fighter target){
        user.doBasicAttack(target);
        battleControl.pushMessage(user.getName() + " strikes " + target.getName() + " !");
        battleControl.updateHeroVitals();
    }

    private Fighter findNextTarget() {
        for(int i = 0; i<gameData.getTeam().size();i++){
            Fighter target = gameData.getTeam().get(i);
            if(target.getKOLvl()<1){
                return target;
            }
        }
        return null;
    }

    private void endEnemyTurn() {
        battleControl.showSelector(battleControl.characterSelectorArea);
        if(battleControl.detectHeroKO()){
            updateTurn("enemyWin");
        }
        else{
            updateTurn("hero");
        }



    }

    private int makeRandom(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    private void checkSpeeds() {
        //Check compare the fastest members from each team to see which team goes first
        //String phase = "hero";
        //updateTurn(phase);
    }

    private void enableCharacterMenu() {
        battleControl.showSelector(battleControl.characterSelectorArea);
    }

    private void disableCharacterMenu(){
        battleControl.hideSelector(battleControl.characterSelectorArea);
        battleControl.hideSelector(battleControl.actionMenu);
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
            if(battleControl.detectEnemyKO()){
                updateTurn("heroWin");
            }
        }
        else{
            battleControl.pushMessage("There's not enough time left for " + attacker.getName()
            + " to attack!");
        }
    }

    private void startBasicAttack(int cost) {
        gameData.subtractTp(cost);
        attacker.doBasicAttack(target);
        int random = makeRandom(attacker.getBattleStrings().size());
        battleControl.pushMessage(attacker.getBattleStrings().get(random));
        battleControl.hideSelector(battleControl.enemySelectorArea);
        battleControl.hideSelector(battleControl.actionMenu);
       requestTPUpdate();
    }

    private void requestTPUpdate(){
        double percentage = (double)gameData.getCurrentTp()/(double)gameData.getMaxTP();
        battleControl.updateTP(percentage);
    }

    public void endPlayerTurn() {
        battleControl.hideSelector(battleControl.enemySelectorArea);
        if(battleControl.detectEnemyKO()){
            updateTurn("heroWin");
        }
        else{
        updateTurn("enemy");
        }


    }
}
