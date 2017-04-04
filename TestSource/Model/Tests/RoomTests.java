package Model.Tests;


import edu.bsu.cs222.FPBreetlison.Model.GameData;
import edu.bsu.cs222.FPBreetlison.Model.Objects.Room;
import org.junit.Before;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;


public class RoomTests {

    private Map<String, Room> allRooms;


    private GameData gameData = new GameData();

    public RoomTests() throws ParserConfigurationException, SAXException, IOException {

    }

    @Before
    public void setUp(){


    }

}


