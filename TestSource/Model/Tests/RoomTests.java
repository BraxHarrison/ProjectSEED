package Model.Tests;


import edu.bsu.cs222.FPBreetlison.Model.GameData;
import edu.bsu.cs222.FPBreetlison.Model.GameManager;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Room;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;


public class RoomTests {

    private Map<String, Room> allRooms;
    private Room testRoom;
    private GameData gameData = new GameData();


    @Before
    public void setUp(){
        testRoom = gameData.getAllRooms().get("FirstSteps");
    }


    @Test
    public void testNorth(){
        Assert.assertEquals("TheDesert", testRoom.getNorth());
    }

    @Test
    public void testSouth(){
        Assert.assertEquals("TheForest", testRoom.getSouth());
    }

    @Test
    public void testInvalidDirection(){
        Assert.assertEquals("null", testRoom.getEast());
    }
    @Test
    public void getName(){
        Assert.assertEquals("FirstSteps",testRoom.getName());
    }



    public RoomTests() throws ParserConfigurationException, SAXException, IOException {




    }



}


