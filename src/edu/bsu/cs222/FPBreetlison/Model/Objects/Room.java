package edu.bsu.cs222.FPBreetlison.Model.Objects;

import java.util.Arrays;
import java.util.List;

public class Room implements java.io.Serializable {

    private String description;
    private String name;
    private String north;
    private String south;
    private String east;
    private String west;
    private String imagePath;
    //private ArrayList<Item> itemsInRoom;

    public Room(String info){

        List<String> roomInfo = stringParser(info);
        this.name = roomInfo.get(0);
        this.description = roomInfo.get(1);
        this.north = roomInfo.get(2);
        this.south = roomInfo.get(3);
        this.east = roomInfo.get(4);
        this.west = roomInfo.get(5);
        this.imagePath = roomInfo.get(6);
        System.out.println(roomInfo.size());

    }

    private List<String> stringParser(String info) {

        return Arrays.asList(info.split(","));
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
    public String getImageURL(){
        if (this.imagePath.equals("null")){
        return "images/system/system_undefined.png";
        }
        else return imagePath;

    }

    public String getDescription(){
       return description;
    }
    public String getName(){
        return name;
    }

}
