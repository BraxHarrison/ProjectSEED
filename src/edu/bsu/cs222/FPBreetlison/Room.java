package edu.bsu.cs222.FPBreetlison;

public class Room {

    public Room(String name){

    }

    public int GetNorth(){
        return 2;
    }
    public int GetSouth(){
        return 2;
    }
    public int GetEast(){
        return 2;
    }
    public int GetWest(){
        return 2;
    }

    //These can return the room number which can be looked up.

    //Dialogue and Event logic can be handled in the room class.

    //Possible ideas for methods: hasNewEvent(), chatWithCharacters(), executeEvent().
}
