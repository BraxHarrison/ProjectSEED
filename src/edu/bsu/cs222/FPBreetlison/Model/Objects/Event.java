//package edu.bsu.cs222.FPBreetlison.Model.Objects;
//
//import edu.bsu.cs222.FPBreetlison.Model.GameData;
//
//import java.util.*;
//
//import static java.awt.SystemColor.info;
//
///**
// * Created by cacto on 4/16/2017.
// */
//public class Event {
//
//    private String type;
//    private Map<Integer,Item> stock;
//    private ArrayList<String> enemyPool;
//    private ArrayList<Item> itemPool;
//    private String displayImagePath;
//
//    public Event(){
//
//    }
//
//    public void activate(GameData data){
//        if(type.equals("fountain")){
//            activateFountain(data);
//        }
//        else if(type.equals("item")){
//            searchItem();
//        }
//        else if(type.equals("enemy")){
//            generateEncounter();
//        }
//        else if(type.equals("shop")){
//            openShop();
//        }
//    }
//
//    private void openShop() {
//
//    }
//
//    private ArrayList<Fighter> generateEncounter() {
//        Random random = new Random();
//        int randomInt = random.nextInt(enemyPool.size());
//        return enemyTeamBuilder(randomInt);
//
//    }
//
//    private ArrayList<Fighter> enemyTeamBuilder(int index) {
//        ArrayList<Fighter> enemyTeam = new ArrayList<>();
//        String enemies = enemyPool.get(index);
//        List<String> splitEnemies = Arrays.asList(enemies.split(","));
//        for(int i = 0;i<splitEnemies.size();i++){
//
//        }
//
//    }
//
//    private void searchItem() {
//    }
//
//    private void activateFountain(GameData data) {
//        ArrayList<Fighter> team = data.getTeam();
//        HashMap<String,Fighter> allEnemies = data.getAllEnemies();
//        for(int i = 0; i<team.size();i++){
//            team.add(new Fighter(allEnemies.get()));
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//}
