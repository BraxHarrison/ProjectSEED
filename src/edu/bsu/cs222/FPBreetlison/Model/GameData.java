package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Model.Objects.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GameData implements java.io.Serializable {

    private ArrayList<Fighter> team;
    private ArrayList<Fighter> enemyTeam;
    private Wallet wallet;

    private HashMap<String,Fighter> allHeroes;
    private HashMap<String,Fighter> allEnemies;
    private HashMap<String,Skill> allSkills;
    private HashMap<String,Item> allItems;
    private HashMap<String,Event> allEvents;
    private ArrayList<Item> inventory;
    private Map<String, Room> allRooms;
    private Room currentRoom;
    private int tp;
    private int maxTP;
    private int selectedTarget;


    public GameData() throws IOException, SAXException, ParserConfigurationException {
        init();

    }

    private void init() throws ParserConfigurationException, SAXException, IOException {
        initLists();
        loadData();
        addHeroes();
        initItems();
        maxTP = 10;
        tp = maxTP;
        wallet = new Wallet();
        wallet.collect(200,"KB");


    }

    private void loadData() throws ParserConfigurationException, SAXException, IOException {
        OverWorldParser overworldLoader = new OverWorldParser();
        BattleXMLParser battleLoader = new BattleXMLParser();
        loadSkills(battleLoader);
        loadFighters(battleLoader);
        loadRooms(overworldLoader);
        loadItems(overworldLoader);
        loadEvents(overworldLoader);
    }

    public void subtractTP(int amount){
        tp -= amount;
    }

    private void loadItems(OverWorldParser loader) {
        allItems = loader.createItemDatabase();
    }

    private void loadEvents(OverWorldParser loader){
        allEvents = loader.createEventDatabase();
    }

    private void loadSkills(BattleXMLParser loader) {
        allSkills = loader.getSkills();
    }

    private void initLists() {
        team = new ArrayList<>();
        allSkills = new HashMap<>();
        allItems = new HashMap<>();
        allEvents = new HashMap<>();
        inventory = new ArrayList<>();
        enemyTeam = new ArrayList<>();
    }

    private void loadRooms(OverWorldParser loader) throws IOException, SAXException, ParserConfigurationException {
        allRooms = loader.parseRoomInfo();
    }

    private void loadFighters(BattleXMLParser loader) throws IOException, SAXException, ParserConfigurationException {
        loader.parseBattleData();
        allHeroes = loader.getHeroes();
        allEnemies = loader.getEnemies();

    }

    private void addHeroes(){
        team.add(allHeroes.get("Roxy"));
        team.add(allHeroes.get("Smitty"));

    }

    public void addEnemies(){
        enemyTeam.clear();
        if(currentRoom.getName().equals("Colossal Plains")){
            enemyTeam.add(new Fighter(allEnemies.get("Blisterbulb")));
            enemyTeam.add(new Fighter(allEnemies.get("Harshmallow")));
            enemyTeam.add(new Fighter(allEnemies.get("Jag Inf.")));
        }
        else if (currentRoom.getName().equals("Luminous Caves")){
            enemyTeam.add(new Fighter(allEnemies.get("Harshmallow")));
            enemyTeam.add(new Fighter(allEnemies.get("Harshmallow")));
            enemyTeam.add(new Fighter(allEnemies.get("Harshmallow")));
        }
        else {
            enemyTeam.add(new Fighter(allEnemies.get("Jag Inf.")));
            enemyTeam.add(new Fighter(allEnemies.get("Jag Inf.")));
        }
    }

    void removeObjectFromInventory(int index){
        inventory.remove(index);
    }

    private void initItems() {
        inventory.add(allItems.get("Patch"));
        inventory.add(allItems.get("Chorps"));
        inventory.add(allItems.get("BluRasp Sapphire"));
        inventory.add(allItems.get("Overclock"));
    }

    void resetHeroTP(){
        tp = maxTP;
    }

    public Map<String, Room> getAllRooms(){return allRooms;}
    public Room getCurrentRoom() {
        return currentRoom;
    }
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
    public ArrayList<Fighter> getTeam() {
        return team;
    }
    public ArrayList<Fighter> getEnemyTeam() {
        return enemyTeam;
    }
    public HashMap<String, Fighter> getAllEnemies() {
        return allEnemies;
    }
    public HashMap<String, Fighter> getAllHeroes() {
        return allHeroes;
    }
    public int getMaxTP() {
        return maxTP;
    }
    public int getCurrentTp() {
        return tp;
    }
    int getSelectedTarget() {
        return selectedTarget;
    }
    public void setSelectedTarget(int selectedTarget) {
        this.selectedTarget = selectedTarget;
    }
    void subtractTp(int cost) {
        tp -= cost;
        if(tp < 0){
            tp = 0;
        }
    }
    public HashMap<String, Item> getAllItems() {
        return allItems;
    }
    public ArrayList<Item> getInventory() {
        return inventory;
    }
    public Wallet getWallet(){
        return wallet;
    }
}
