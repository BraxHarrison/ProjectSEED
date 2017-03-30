package edu.bsu.cs222.FPBreetlison;

import GUI.BattleView;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Random;


public class BattleManager {

    private GameManager game;
    private GameData gameData;
    private BattleView battleView;

    private Fighter attacker;
    private Fighter target;

    private int targetNo;
    private DamageState fighterSnapshot;
    private ArrayList<DamageState> targetQueue;

    private ArrayList<String> messageQueue;

    public void getGameInfo(GameManager game){
        this.game = game;
        this.gameData = game.getGameData();
        this.battleView = game.getBattleControl();

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
            battleView.enemySelectorArea.setVisible(false);
        }
        else if(phase.equals("heroWin")){
            battleView.enemySelectorArea.setVisible(false);
            battleView.heroSelectorArea.setVisible(false);
        }
        //battleView.queueMessages(messageQueue);
    }

    private void resetTP() {
        gameData.resetHeroTP();
        requestTPUpdate();
    }

    private void tryEnemyAttack() {

        ArrayList<Fighter> remainingEnemies = getRemainingEnemies();
        for(int i = 0; i<remainingEnemies.size();i++){
            fighterSnapshot = new DamageState();
            Fighter user = remainingEnemies.get(i);
            targetNo = makeRandom(gameData.getTeam().size());
            Fighter target = gameData.getTeam().get(targetNo);
            if(target.isKO()){
                target = findNextTarget();
            }
            if(target != null){
                fighterSnapshot.setIndex(targetNo);
                System.out.println("ACTUAL: " + user.getName() + " hits " + target.getName());
                doEnemyAttack(user,target);
            }
            else{
                fighterSnapshot.setIndex(i);
                fighterSnapshot.setHpPercent(0);
                fighterSnapshot.setKOState(true);
                targetQueue.add(fighterSnapshot);
                updateTurn("enemyWin");
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
        fighterSnapshot.setHpPercent((double)target.getHp()/(double)target.getMaxHP());
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
                System.out.println(target.getName() + " has " + target.getHp() + " so clearly they are able to fight.");
                return target;
            }
            System.out.println(target.getName() + " is down. Searching for next target...");
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

    public boolean detectHeroKO() {
        ObservableList<Node> selectors = battleView.heroSelectorArea.getChildren();
        ArrayList<Fighter> fighters = gameData.getTeam();
        int KOamt = 0;

        for (int i = 0; i < fighters.size(); i++) {
            fighters.get(i).checkKOLevel();
            if (fighters.get(i).getKOLvl() > 0) {
                KOamt++;
            }
//            if(fighters.get(i).getKOLvl() == 1){
//                battleView.removeHero(i);
//            }
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
        int random = makeRandom(attacker.getBattleStrings().size());
        //messageQueue.add(attacker.getBattleStrings().get(random));
        battleView.pushMessage(attacker.getBattleStrings().get(random));
        battleView.enemySelectorArea.setVisible(false);
        requestTPUpdate();
    }

    private void requestTPUpdate(){
        double percentage = (double)gameData.getCurrentTp()/(double)gameData.getMaxTP();
        battleView.updateTP(percentage);
    }

    public void endPlayerTurn() {
        battleView.enemySelectorArea.setVisible(false);
        if(battleView.detectEnemyKO()){
            updateTurn("heroWin");
            messageQueue.add("The enemy team is down! You won!");
        }
        else{
            updateTurn("enemy");
        }
        battleView.queueMessages(messageQueue);

    }

    public void useItem(int itemNo) {
        gameData.getInventory().get(itemNo).activate(gameData.getTeam().get(battleView.selectedUser));
        gameData.getInventory().remove(itemNo);
    }

    public ArrayList<DamageState> getTargetQueue() {
        return targetQueue;
    }
    public ArrayList<String> getMessageQueue() {
        return messageQueue;
    }
}
