package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Model.Objects.Fighter;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Item;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Room;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GameData {

    public static GameData gameData;
    private ArrayList<Fighter> team;
    private ArrayList<Fighter> allFighters;
    private ArrayList<Fighter> enemyTeam;
    private ArrayList<Item> allItems;
    private ArrayList<Item> inventory;
    private Map<String, Room> allRooms;
    private Fighter target;
    private Stage stage;
    private Room currentRoom;
    private int tp;
    private int maxTP;
    private int selectedUser;
    private int selectedTarget;

    private GameManager game;

    public GameData() throws IOException, SAXException, ParserConfigurationException {
        init();

    }

    private void init() throws ParserConfigurationException, SAXException, IOException {
        loadFighters();
        loadRooms();
        loadLists();
        addHeroes();
        addEnemies();
        initItems();
        currentRoom = allRooms.get("FirstSteps");
        maxTP = 10;
        tp = maxTP;

    }

    private void loadLists() {
        team = new ArrayList<Fighter>();
        allItems = new ArrayList<Item>();
        inventory = new ArrayList<Item>();
        enemyTeam = new ArrayList<Fighter>();
    }

    private void loadRooms() throws IOException, SAXException, ParserConfigurationException {

        OverWorldParser loader = new OverWorldParser();
        allRooms = loader.parseRoomInfo();

    }

    private void loadFighters() throws IOException, SAXException, ParserConfigurationException {
        BattleXMLParser loader = new BattleXMLParser();
        allFighters = loader.parseFighterInfo();

    }

    private void addHeroes(){
        team.add(allFighters.get(0));
    }

    private void addEnemies(){
//        enemyTeam.add(new Fighter("Jag,25,10,3,3,3,7,2,images/Ragtime_Pepe.jpg"));
//        enemyTeam.add(new Fighter("Blisterbulb,30,6,6,7,7,3,6,images/Ragtime_Pepe.jpg"));
        enemyTeam.add(new Fighter("Harshmallow,20,3,4,9,11,4,4,images/battleGraphics/fullBattlerGraphics/Battle_Full_Harshmallow.png"));
    }

    private void initItems() {
        allItems.add(new Item("Patch, A cluster of raw pixels. " +
                "Integrates with the user's body to recover health.,Recover 15 HP,15,/images/battleGraphics/itemGraphics/item_patch.png,heal"));
        allItems.add(new Item("Overclock,A bizarre pocketwatch that creates a bubble of " +
                "sped-up time around the user. Allows user to attack more quickly.,-1 TP Cost,1,/images/battleGraphics/itemGraphics/item_overclock.png,buff,speed"));
        inventory.add(allItems.get(0));
        inventory.add(allItems.get(1));


    }

    public void collectItem(Item item){
        inventory.add(item);
    }

    public void resetHeroTP(){
        tp = maxTP;
    }

    public void changeRoom(String direction){

    }

    public Room getCurrentRoom(){return currentRoom;}
    public Map<String, Room> getAllRooms(){return allRooms;}
    public ArrayList<Fighter> getTeam() {
        return team;
    }
    public void setTeam(ArrayList<Fighter> team) {
        this.team = team;
    }
    public ArrayList<Fighter> getEnemyTeam() {
        return enemyTeam;
    }
    public void setEnemyTeam(ArrayList<Fighter> enemyTeam) {
        this.enemyTeam = enemyTeam;
    }
    public Fighter getTarget() {
        return target;
    }
    public void setTarget(Fighter target) {
        this.target = target;
    }
    public GameManager getGame() {
        return game;
    }
    public void setGame(GameManager game) {
        this.game = game;
    }
    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public int getMaxTP() {
        return maxTP;
    }
    public int getCurrentTp() {
        return tp;
    }
    public int getSelectedUser() {
        return selectedUser;
    }
    public void setSelectedUser(int selectedUser) {
        this.selectedUser = selectedUser;
    }
    public int getSelectedTarget() {
        return selectedTarget;
    }
    public void setSelectedTarget(int selectedTarget) {
        this.selectedTarget = selectedTarget;
    }
    public void subtractTp(int cost) {
        tp -= cost;
        if(tp < 0){
            tp = 0;
        }
    }
    public ArrayList<Item> getInventory() {
        return inventory;
    }
}
