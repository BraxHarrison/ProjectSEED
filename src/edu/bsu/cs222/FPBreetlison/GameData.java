package edu.bsu.cs222.FPBreetlison;

import java.util.ArrayList;

public class GameData {

    public static GameData gameData;

    private ArrayList<Character> team;

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
        team = new ArrayList<Character>();
        team.add(new Character("Eve",2,2,2,2,2));
        team.add(new Character("Quinn",2,2,2,2,2));
        System.out.print("Initializing...");
    }

    public ArrayList<Character> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<Character> team) {
        this.team = team;
    }









}
