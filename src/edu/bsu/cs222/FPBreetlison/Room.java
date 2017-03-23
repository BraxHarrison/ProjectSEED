package edu.bsu.cs222.FPBreetlison;

import java.util.Arrays;
import java.util.List;

public class Room {

    private String description;
    private String name;
    private String north;
    private String south;
    private String east;
    private String west;
    //private ArrayList<Item> itemsInRoom;




    public Room(String info){


        List<String> roomInfo = stringParser(info);
        this.name = roomInfo.get(0);
        this.description = roomInfo.get(1);
        this.north = roomInfo.get(2);
        this.south = roomInfo.get(3);
        this.east = roomInfo.get(4);
        this.west = roomInfo.get(5);

    }

    private List<String> stringParser(String info) {

        List<String> details = Arrays.asList(info.split(","));
        return details;
    }


    public String getNorth(){
        return north;
    }
    public String getSouth(){
        return south;
    }
    public String getEast(){
        return east;
    }
    public String getWest(){
        return west;
    }

    public String getDescription(){
       return description;
    }
    public String getName(){
        return name;
    }

}
