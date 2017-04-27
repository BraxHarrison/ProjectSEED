package edu.bsu.cs222.FPBreetlison.Model.Objects;

import edu.bsu.cs222.FPBreetlison.Model.GameData;

import java.util.*;



public class Event implements java.io.Serializable {

    private String name;
    private String type;
    private ArrayList<Item> stock;
    private ArrayList<String> enemyPool;
    private String imagePath;

    private GameData gameData;

    public Event(String eventInfo,GameData gameData){
        this.gameData = gameData;
        List<String> info = stringParser(eventInfo);
        this.name = info.get(0);
        this.type = info.get(1);
        String stockString = info.get(2);
        this.stock = buildStock(stockString);
        this.imagePath = info.get(5);
    }

    private List<String> stringParser(String info){
        return Arrays.asList(info.split(","));
    }

    private ArrayList<Item> buildStock(String stockString){
        List<String> stockList = Arrays.asList(stockString.split("/"));
        ArrayList<Item> builtStock = new ArrayList<>();
        for(int i = 0; i<stockList.size();i++){
            builtStock.add(gameData.getAllItems().get(stockList.get(i)));
        }
        return builtStock;
    }

    public void activate(GameData data){
        gameData = data;
        switch (type) {
            case "fountain":
                activateFountain();
                break;
            case "item":
                searchItem();
                break;
            case "enemy":
                generateEncounter();
                break;
            case "shop":
                openShop();
                break;
        }
    }

    private void openShop() {

    }

    private ArrayList<Fighter> generateEncounter() {
        Random random = new Random();
        int randomInt = random.nextInt(enemyPool.size());
        return enemyTeamBuilder(randomInt);

    }

    private ArrayList<Fighter> enemyTeamBuilder(int index) {
        ArrayList<Fighter> enemyTeam = new ArrayList<>();
        String enemies = enemyPool.get(index);
        List<String> splitEnemies = Arrays.asList(enemies.split(","));
        for (String splitEnemy : splitEnemies) {
            enemyTeam.add(new Fighter(gameData.getAllEnemies().get(splitEnemy)));
        }
        return enemyTeam;
    }

    private void searchItem() {
    }

    private void activateFountain() {
        ArrayList<Fighter> team = gameData.getTeam();
        for (Fighter aTeam : team) {
            aTeam.recoverHealth(-1);
        }
    }

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public ArrayList<Item> getStock() {
        return stock;
    }
    public String getImagePath() {
        return imagePath;
    }
}
