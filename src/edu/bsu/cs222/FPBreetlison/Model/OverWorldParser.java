package edu.bsu.cs222.FPBreetlison.Model;

import edu.bsu.cs222.FPBreetlison.Model.Objects.Room;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

class OverWorldParser {

    private Document document;

    private void createDoc() throws ParserConfigurationException, IOException, SAXException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("database/GameInfo.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(inputStream);
    }

    Map<String, Room> parseRoomInfo() throws ParserConfigurationException, IOException, SAXException {
        createDoc();
        return createRoomMap();
    }

    private Map<String, Room> createRoomMap(){
        Map<String, Room> rooms = new HashMap<>();
        NodeList nodeList = this.document.getElementsByTagName("room");
        for(int i = 0; i< nodeList.getLength(); i++){

            Node roomInfo = nodeList.item(i);
            Element roomEle = (Element)roomInfo;
            Room room = new Room(createRoomString(roomEle));
            rooms.put(room.getName(),room);
        }
        return rooms;
    }

    private String createRoomString(Element roomElement) {
        return (roomElement.getAttribute("name") + ",") +
                roomElement.getAttribute("des") + "," +
                roomElement.getAttribute("north") + "," +
                roomElement.getAttribute("south") + "," +
                roomElement.getAttribute("east") + "," +
                roomElement.getAttribute("west") + ",";

    }
}
