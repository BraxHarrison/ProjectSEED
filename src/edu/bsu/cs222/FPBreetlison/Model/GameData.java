package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Model.Objects.Fighter;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Item;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Room;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Skill;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameData {

    private ArrayList<Fighter> team;
    private HashMap<String,Fighter> allHeroes;
    private HashMap<String,Fighter> allEnemies;
    private ArrayList<Fighter> enemyTeam;
    private HashMap<String,Skill> allSkills;
    private HashMap<String,Item> allItems;
    private ArrayList<Item> inventory;
    private Map<String, Room> allRooms;
    private Stage stage;
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
        addEnemies();
        initItems();
        maxTP = 10;
        tp = maxTP;

    }

    private void loadData() throws ParserConfigurationException, SAXException, IOException {
        OverWorldParser overworldLoader = new OverWorldParser();
        BattleXMLParser battleLoader = new BattleXMLParser();
        loadSkills(battleLoader);
        loadFighters(battleLoader);
        loadRooms(overworldLoader);
        loadItems(overworldLoader);
    }

    public void subtractTP(int amount){
        tp -= amount;
    }

    private void loadItems(OverWorldParser loader) {

        allItems = loader.createItemDatabase();
    }

    private void loadSkills(BattleXMLParser loader) {
        allSkills = loader.getSkills();
    }

    private void initLists() {
        team = new ArrayList<>();
        allSkills = new HashMap<>();
        allItems = new HashMap<>();
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

    private void addEnemies(){
        enemyTeam.add(allEnemies.get("Jag"));
        enemyTeam.add(allEnemies.get("Harshmallow"));
    }

    void removeObjectFromInventory(int index){
        inventory.remove(index);
    }

    private void initItems() {
        inventory.add(allItems.get("Patch"));
        inventory.add(allItems.get("Overclock"));


    }

    void resetHeroTP(){
        tp = maxTP;
    }

    public Map<String, Room> getAllRooms(){return allRooms;}
    public ArrayList<Fighter> getTeam() {
        return team;
    }
    public ArrayList<Fighter> getEnemyTeam() {
        return enemyTeam;
    }
    Stage getStage() {
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
    public ArrayList<Item> getInventory() {
        return inventory;
    }
}
