package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Controller.BattleView;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Snapshot;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Fighter;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Random;


public class BattleManager {

    private GameData gameData;
    private BattleView battleView;

    private Fighter attacker;
    private Fighter target;

    private int targetNo;
    private Snapshot fighterSnapshot;
    private ArrayList<Snapshot> targetQueue;

    private ArrayList<String> messageQueue;

    public void getGameInfo(GameManager game){
        this.gameData = game.getGameData();
        this.battleView = game.getBattleControl();

    }

    public void start(){
        messageQueue = new ArrayList<>();
        targetQueue = new ArrayList<>();

        requestTPUpdate();
        checkSpeeds();
    }

    private void updateTurn(String phase) {
        switch (phase) {
            case "hero":
                enableCharacterMenu();
                break;
            case "enemy":
                disableCharacterMenu();
                tryEnemyAttack();
                resetTP();
                endEnemyTurn();
                break;
            case "enemyWin":
                battleView.blockEnemySelectors();
                battleView.uiLocked = true;
                battleView.backButton.setVisible(false);
                break;
            case "heroWin":
                battleView.blockEnemySelectors();
                battleView.heroSelectorArea.setVisible(false);
                battleView.uiLocked = true;
                battleView.backButton.setVisible(false);
                break;
        }
    }

    private void resetTP() {
        gameData.resetHeroTP();
        requestTPUpdate();
    }

    private void tryEnemyAttack() {

        ArrayList<Fighter> remainingEnemies = getRemainingEnemies();
        for (Fighter remainingEnemy : remainingEnemies) {
            fighterSnapshot = new Snapshot();
            targetNo = makeRandom(gameData.getTeam().size());
            Fighter target = gameData.getTeam().get(targetNo);
            if (target.isKO()) {
                target = findNextTarget();
            }
            if (target != null) {
                fighterSnapshot.setIndex(targetNo);
                doEnemyAttack(remainingEnemy, target);
            } else {
                fighterSnapshot.setIndex(targetNo);
                fighterSnapshot.calcHPPercent(null);
                fighterSnapshot.setKOState(true);
                targetQueue.add(fighterSnapshot);
                updateTurn("enemyWin");
            }
        }
    }

    private ArrayList<Fighter> getRemainingEnemies() {
        ArrayList<Fighter> fighters = gameData.getEnemyTeam();
        ArrayList<Fighter> ableFighters = new ArrayList<>();
        for (Fighter fighter : fighters) {
            if (fighter.getKOLvl() == 0) {
                ableFighters.add(fighter);
            }
        }
        return ableFighters;
    }

    private void doEnemyAttack(Fighter user, Fighter target){
        user.doBasicAttack(target);
        fighterSnapshot.calcHPPercent(target);
        detectHeroKO();
        fighterSnapshot.setKOState(target.isKO());
        messageQueue.add(user.getName() + " strikes " + target.getName() + " !");
        targetQueue.add(fighterSnapshot);

    }

    private Fighter findNextTarget() {

        //Seems like this isn't functioning properly
        for(int i = 0; i<gameData.getTeam().size();i++){
            Fighter target = gameData.getTeam().get(i);
            if(!target.isKO()){
                targetNo = i;
                return target;
            }
            //The problem is that when nothing is chosen, they still send the last instance of snapshot. We need to find a way to remove the snapshot if the hero is already KOd
        }
        return null;
    }

    private void endEnemyTurn() {
        battleView.heroSelectorArea.setVisible(true);
        if(detectHeroKO()){
            updateTurn("enemyWin");
            messageQueue.add("Everyone's trashed! You lose!");

        }
        else{
            messageQueue.add("It's your turn!");
            updateTurn("hero");

        }
        battleView.queueMessages(messageQueue);
        battleView.queueBarUpdates(targetQueue);
    }

    private boolean detectHeroKO() {
        ArrayList<Fighter> fighters = gameData.getTeam();
        int KOamt = 0;

        for (Fighter fighter : fighters) {
            fighter.checkKOLevel();
            if (fighter.getKOLvl() > 0) {
                KOamt++;

            }
//            if(fighters.get(i).getKOLvl() == 1){
//                battleView.removeHero(i);
//            }
        }
        return KOamt == fighters.size();
    }

    private int makeRandom(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    private void checkSpeeds() {

    }

    private void enableCharacterMenu() {
        battleView.heroSelectorArea.setVisible(true);
        battleView.backButton.setVisible(false);
    }

    private void disableCharacterMenu(){
        battleView.heroSelectorArea.setVisible(false);
        battleView.actionMenu.setVisible(false);
    }

    public void checkPlayerTP(){
//        if(gameData.getCurrentTp() <= 0){
//            updateTurn("enemy");
//        }
    }

    public void tryBasicAttack(){
        attacker = gameData.getTeam().get(battleView.selectedUser);
        target = gameData.getEnemyTeam().get(gameData.getSelectedTarget());
        int cost = attacker.getTpCost();
        if(gameData.getCurrentTp() >= cost ){
            startBasicAttack(cost);
        }
        else{
            battleView.pushMessage("There's not enough time left for " + attacker.getName()
                    + " to attack!");
        }

    }

    private void startBasicAttack(int cost) {
        gameData.subtractTp(cost);
        attacker.doBasicAttack(target);
        updateUIForHeroAttack();
        detectEnemyKO();

    }

    private void updateUIForHeroAttack(){
        Snapshot enemyState = new Snapshot();
        enemyState.setIndex(gameData.getSelectedTarget());
        enemyState.calcHPPercent(target);
        battleView.updateEnemyQuickInfo(enemyState);
        int random = makeRandom(attacker.getBattleStrings().size());
        battleView.pushMessage(attacker.getBattleStrings().get(random));
        battleView.blockEnemySelectors();
        requestTPUpdate();
    }

    private void detectEnemyKO() {
        ObservableList<Node> selectors = battleView.enemySelectorArea.getChildren();
        ArrayList<Fighter> fighters = gameData.getEnemyTeam();
        int KOamt = 0;
        for (int i = 0; i < selectors.size(); i++) {
            fighters.get(i).checkKOLevel();
            if (fighters.get(i).getKOLvl() > 0) {
                KOamt++;
            }
            if(fighters.get(i).getKOLvl() == 1){
                selectors.get(i).setVisible(false);
                messageQueue.add(fighters.get(i).getName() + " is down!");
            }
        }
        if (KOamt == fighters.size()) {
            messageQueue.add("The enemy team is down! You won!");
            battleView.queueMessages(messageQueue);
            updateTurn("heroWin");
        }



    }

    private void requestTPUpdate(){
        double percentage = (double)gameData.getCurrentTp()/(double)gameData.getMaxTP();
        battleView.updateTP(percentage);
    }

    public void endPlayerTurn() {
        battleView.blockEnemySelectors();
        updateTurn("enemy");
        battleView.queueMessages(messageQueue);

    }

    public void useItem(int itemNo) {
        gameData.getInventory().get(itemNo).activate(gameData.getTeam().get(battleView.selectedUser));
        gameData.removeObjectFromInventory(itemNo);
    }

    public ArrayList<String> getMessageQueue() {
        return messageQueue;
    }
}
