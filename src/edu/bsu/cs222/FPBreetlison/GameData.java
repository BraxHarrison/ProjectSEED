package edu.bsu.cs222.FPBreetlison;

import java.util.ArrayList;

public class GameData {

    public static GameData gameData;

    private ArrayList<Battler> team;

    public static GameData getData(){
        if(gameData == null){
            gameData = new GameData();
        }
        return gameData;

    }

    public GameData(){
        init();
    }

    public void init(){
        team = new ArrayList<Battler>();
        team.add(new Battler("Eve",2,2,2,2,2));
        team.add(new Battler("Quinn",2,2,2,2,2));
        System.out.print("Initializing...");
    }

    public ArrayList<Battler> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<Battler> team) {
        this.team = team;
    }









}
