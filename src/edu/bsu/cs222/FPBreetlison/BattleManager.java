package edu.bsu.cs222.FPBreetlison;

import GUI.BattleView;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;


public class BattleManager {

    private GameManager game;
    private GameData gameData;
    private BattleView battleControl;

    private Fighter attacker;
    private Fighter target;

    private int targetNo;
    private DamageState barInfo;
    private ArrayList<DamageState> targetQueue;

    private ArrayList<String> messageQueue;

    public void getGameInfo(GameManager game){
        this.game = game;
        this.gameData = game.getGameData();
        this.battleControl = game.getBattleControl();

    }

    public void start(){
        messageQueue = new ArrayList<>();
        targetQueue = new ArrayList<>();

        requestTPUpdate();
        checkSpeeds();
    }

    public void updateTurn(String phase) {
        if(phase.equals("hero")){
            enableCharacterMenu();
        }
        else if(phase.equals("enemy")) {
            disableCharacterMenu();
            tryEnemyAttack();
            resetTP();
            endEnemyTurn();
        }
        else if(phase.equals("enemyWin")){
            battleControl.enemySelectorArea.setVisible(false);
        }
        else if(phase.equals("heroWin")){
            battleControl.enemySelectorArea.setVisible(false);
            battleControl.heroSelectorArea.setVisible(false);
        }
        //battleControl.queueMessages(messageQueue);
    }

    private void resetTP() {
        gameData.resetHeroTP();
        requestTPUpdate();
    }

    private void tryEnemyAttack() {

        ArrayList<Fighter> remainingEnemies = getRemainingEnemies();
        for(int i = 0; i<remainingEnemies.size();i++){
            barInfo = new DamageState();
            Fighter user = remainingEnemies.get(i);
            int targetNo = makeRandom(gameData.getTeam().size());Fighter target = gameData.getTeam().get(targetNo);
            if(target.getKOLvl() > 0){
                target = findNextTarget();
            }
            if(target != null){
                System.out.println(targetNo);
                barInfo.setIndex(targetNo);
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
        barInfo.setHpPercent((double)target.getHp()/(double)target.getMaxHP());
        DamageState newBarInfo = barInfo;
        messageQueue.add(user.getName() + " strikes " + target.getName() + " !");
        targetQueue.add(newBarInfo);
        //battleControl.updateHeroVitals();
    }

    private Fighter findNextTarget() {
        for(int i = 0; i<gameData.getTeam().size();i++){
            Fighter target = gameData.getTeam().get(i);
            if(target.getKOLvl()<1){
                targetNo = i;
                return target;
            }
        }
        return null;
    }

    private void endEnemyTurn() {
        battleControl.heroSelectorArea.setVisible(true);
        if(detectHeroKO()){
            updateTurn("enemyWin");
            messageQueue.add("Everyone's trashed! You lose!");
        }
        else{
            messageQueue.add("It's your turn!");
            updateTurn("hero");

        }
        battleControl.queueMessages(messageQueue);
        battleControl.queueBarUpdates(targetQueue);
    }

    public boolean detectHeroKO() {
        ObservableList<Node> selectors = battleControl.heroSelectorArea.getChildren();
        ArrayList<Fighter> fighters = gameData.getTeam();
        int KOamt = 0;

        for (int i = 0; i < fighters.size(); i++) {
            fighters.get(i).checkKO();
            StackPane hero = (StackPane)selectors.get(i);
            if (fighters.get(i).getKOLvl() > 0) {
                KOamt++;
            }
            if(fighters.get(i).getKOLvl() == 1){
                Label hLabel = (Label)hero.getChildren().get(0);
                hLabel.setTextFill(Color.web("0x333c47"));
                hero.setOnMousePressed(null);
                messageQueue.add(fighters.get(i).getName() + " is down!");
            }
        }
        if (KOamt == fighters.size()) {
            return true;
        }
        return false;
    }

    private int makeRandom(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    private void checkSpeeds() {

    }

    private void enableCharacterMenu() {
        battleControl.heroSelectorArea.setVisible(true);
        battleControl.backButton.setVisible(false);
    }

    private void disableCharacterMenu(){
        battleControl.heroSelectorArea.setVisible(false);
        battleControl.actionMenu.setVisible(false);
    }

    public void checkPlayerTP(){
//        if(gameData.getCurrentTp() <= 0){
//            updateTurn("enemy");
//        }
    }

    public void tryBasicAttack(){
        attacker = gameData.getTeam().get(battleControl.selectedUser);
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
        int random = makeRandom(attacker.getBattleStrings().size());
        //messageQueue.add(attacker.getBattleStrings().get(random));
        battleControl.pushMessage(attacker.getBattleStrings().get(random));
        battleControl.enemySelectorArea.setVisible(false);
        requestTPUpdate();
    }

    private void requestTPUpdate(){
        double percentage = (double)gameData.getCurrentTp()/(double)gameData.getMaxTP();
        battleControl.updateTP(percentage);
    }

    public void endPlayerTurn() {
        battleControl.enemySelectorArea.setVisible(false);
        if(battleControl.detectEnemyKO()){
            updateTurn("heroWin");
            messageQueue.add("The enemy team is down! You won!");
        }
        else{
            updateTurn("enemy");
        }
        battleControl.queueMessages(messageQueue);

    }

    public void useItem(int itemNo) {
        gameData.getInventory().get(itemNo).activate(gameData.getTeam().get(battleControl.selectedUser));
        gameData.getInventory().remove(itemNo);
    }

    public ArrayList<DamageState> getTargetQueue() {
        return targetQueue;
    }
}
