package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Controller.BattleView;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Skill;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Snapshot;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Fighter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;


public class BattleManager {

    private GameData gameData;
    private GameManager game;
    private BattleView battleView;
    private Animator animator;

    private Fighter attacker;
    private Fighter target;

    private int targetNo;
    private Snapshot fighterSnapshot;
    private ArrayList<Snapshot> targetQueue;
    private ArrayList<String> messageQueue;

    boolean heroWon;

    public void getGameInfo(GameManager game){
        this.game = game;
        this.gameData = game.getGameData();
        gameData.addEnemies();
        this.battleView = game.getBattleControl();
        animator = new Animator(game);

    }

    public void start(){

        messageQueue = new ArrayList<>();
        targetQueue = new ArrayList<>();
        gameData.resetHeroTP();
        battleView.updateTP();
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
                endBattle();
                break;
            case "heroWin":
                heroWon = true;
                battleView.blockEnemySelectors();
                battleView.heroSelectorArea.setVisible(false);
                battleView.uiLocked = true;
                gameData.getWallet().collect(500,"KB");
                gainExp();
                endBattle();
                break;
        }
        animator.backButtonSlideIn();
    }


    public void endBattle(){
        revertFighterStats();
        int dur = 1000;
        battleView.uiLocked = true;
        animator.backButtonSlideIn();
        if(heroWon){dur += 4000; heroWon = false;}
        Timeline counter = new Timeline();
        counter.getKeyFrames().add(new KeyFrame(
                Duration.millis(dur),
                ae -> game.setUpOverworld()));
        counter.play();
        game.setBattleUnderway(false);
    }

    private void revertFighterStats() {
        for(int i = 0; i< gameData.getTeam().size();i++){
            gameData.getTeam().get(i).revertStats();
        }
    }

    private void resetTP() {
        gameData.resetHeroTP();
        battleView.updateTP();
    }

    private void tryEnemyAttack() {

        for (int i = 0; i< gameData.getEnemyTeam().size();i++) {
            if(!gameData.getEnemyTeam().get(i).isKO()){
                findLivingHero(i);
            }
        }
    }

    private void findLivingHero(int i) {
        fighterSnapshot = new Snapshot();
        fighterSnapshot.setAttackerIndex(i);
        fighterSnapshot.setAnimType("enemyLunge");
        targetNo = makeRandom(gameData.getTeam().size());
        Fighter target = gameData.getTeam().get(targetNo);
        if (target.isKO()) {
            target = findNextTarget();
        }
        if (target != null) {
            fighterSnapshot.setIndex(targetNo);
            doEnemyAttack(gameData.getEnemyTeam().get(i), target);
        }
        else {
            fighterSnapshot.setIndex(targetNo);
            fighterSnapshot.calcHPPercent(null);
            fighterSnapshot.setKOState(true);
            targetQueue.add(fighterSnapshot);
            updateTurn("enemyWin");
        }
    }
//
//    private ArrayList<Fighter> getRemainingEnemies() {
//        ArrayList<Fighter> fighters = gameData.getEnemyTeam();
//        ArrayList<Fighter> ableFighters = new ArrayList<>();
//        for (Fighter fighter : fighters) {
//            if (fighter.getKOLvl() == 0) {
//                ableFighters.add(fighter);
//            }
//        }
//        return ableFighters;
//    }

    private void doEnemyAttack(Fighter user, Fighter target){
        user.doBasicAttack(target);
        fighterSnapshot.calcHPPercent(target);
        detectHeroKO();
        fighterSnapshot.setKOState(target.isKO());
       // System.out.println("MaxHP: " + target.getHp() + "/CurrHP: " + target.getCurrStats().get("hp"));
        messageQueue.add(user.getName() + " strikes " + target.getName() + " !");
        targetQueue.add(fighterSnapshot);

    }

    private Fighter findNextTarget() {

        for(int i = 0; i<gameData.getTeam().size();i++){
            Fighter target = gameData.getTeam().get(i);
            if(!target.isKO()){
                targetNo = i;
                return target;
            }
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
        battleView.uiLocked = true;
    }

    private boolean detectHeroKO() {
        ArrayList<Fighter> fighters = gameData.getTeam();
        int KOamt = 0;

        for (Fighter fighter : fighters) {
            fighter.checkKOLevel();
            if (fighter.getKOLvl() > 0) {
                KOamt++;

            }
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
        animator.backButtonSlideIn();
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

    public void tryHeroBasicAttack(){
        attacker = gameData.getTeam().get(battleView.selectedUser);
        target = gameData.getEnemyTeam().get(gameData.getSelectedTarget());
        int cost = attacker.getTpCost();
        if(gameData.getCurrentTp() >= cost ){
            battleView.handleAnimation("heroLunge");
            messageQueue.add(attacker.getName() + " attacks " + target.getName() +"!");
            battleView.queueMessages(messageQueue);
            startHeroBasicAttack(cost);
        }
        else{
            battleView.pushMessage("There's not enough time left for " + attacker.getName()
                    + " to attack!");
        }

    }

    private void startHeroBasicAttack(int cost) {
        gameData.subtractTp(cost);
        attacker.doBasicAttack(target);
        battleView.handleAnimation("heroLunge");
        updateUIForHeroAttack();
        detectEnemyKO();
        //startDamageAnimation();
        battleView.queueMessages(messageQueue);

    }

    private void startDamageAnimation() {
        Snapshot snapshot = new Snapshot();
        snapshot.setAttackerIndex(battleView.selectedUser);
        snapshot.setIndex(battleView.selectedTarget);
        animator.animateDamageToEnemy(snapshot);
    }

    private void updateUIForHeroAttack(){
        Snapshot enemyState = new Snapshot();
        enemyState.setIndex(gameData.getSelectedTarget());
        enemyState.calcHPPercent(target);
        battleView.updateEnemyQuickInfo(enemyState);
        battleView.blockEnemySelectors();
        battleView.updateTP();
    }

    private void detectEnemyKO() {
        ObservableList<Node> selectors = battleView.enemySelectorArea.getChildren();
        ArrayList<Fighter> fighters = gameData.getEnemyTeam();
        int KOamt = 0;
        for (int i = 0; i < fighters.size(); i++) {
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

    public void endPlayerTurn() {
        battleView.blockEnemySelectors();
        updateTurn("enemy");
        battleView.queueMessages(messageQueue);
        battleView.uiLocked = true;

    }

    public void useItem(int itemNo) {
        gameData.getInventory().get(itemNo).activate(gameData.getTeam().get(battleView.selectedUser));
        gameData.removeObjectFromInventory(itemNo);
    }

    public ArrayList<String> getMessageQueue() {
        return messageQueue;
    }

    public void tryActivateSkill(Fighter user, Fighter target) {
        Skill skill = user.getQueuedSkill();
        attacker = user;
        this.target = target;
        if(gameData.getCurrentTp() >= skill.getTpCost()){
            activateSkill(user, target);
            updateUIForHeroAttack();
            detectEnemyKO();
            battleView.handleAnimation(skill.getAnimType());
            battleView.queueMessages(messageQueue);
        }
        else{
            battleView.pushMessage("There's not enough time to use that skill!");
        }


    }
    private void activateSkill(Fighter user, Fighter target){
        Skill skill = user.getQueuedSkill();
        user.getQueuedSkill().use(user, target);
        gameData.subtractTP(skill.getTpCost());
        battleView.updateTP();
        messageQueue.add(user.getName() + " used " + skill.getName() + "!");
        extraMessage(skill);
        battleView.queueMessages(messageQueue);
    }

    private void extraMessage(Skill skill){
        if(!skill.getExtraMessage().equals("null")){
            messageQueue.add(skill.getExtraMessage());
        }

    }

    private void gainExp() {
        for(int i = 0; i<gameData.getTeam().size();i++){
            Fighter fighter = gameData.getTeam().get(i);
            fighter.getExp(200);
            fighter.checkLevel();
            if(fighter.isLeveledUp()){
                messageQueue.add(fighter.getName() + " leveled up!");
                fighter.setLeveledUp(false);
            }
        }
        battleView.queueMessages(messageQueue);

    }
}
