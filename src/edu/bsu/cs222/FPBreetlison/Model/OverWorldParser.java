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

public class OverWorldParser {

    Document document;
    NodeList nodeList;
    Map<String, Room> rooms;


    private void createDoc() throws ParserConfigurationException, IOException, SAXException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("database/GameInfo.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.document = builder.parse(inputStream);
    }

    Map<String, Room> parseRoomInfo() throws ParserConfigurationException, IOException, SAXException {

        createDoc();
        rooms = createRoomMap();
        setRoomDescriptions();
        return rooms;

    }

    private void setRoomDescriptions() {

    }

    private Map<String, Room> createRoomMap(){
        Map<String, Room> rooms = new HashMap<>();
        nodeList = this.document.getElementsByTagName("room");
        for(int i = 0; i< nodeList.getLength();i++){

            Node roomInfo = nodeList.item(i);
            Element roomEle = (Element)roomInfo;
            Room room = new Room(createRoomString(roomEle));
            rooms.put(room.getName(),room);
        }


        return rooms;

    }

    private String createRoomString(Element roomElement) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(roomElement.getAttribute("name") + ",");
        strBuilder.append(roomElement.getAttribute("des") + ",");
        strBuilder.append(roomElement.getAttribute("north") + ",");
        strBuilder.append(roomElement.getAttribute("south") + ",");
        strBuilder.append(roomElement.getAttribute("east") + ",");
        strBuilder.append(roomElement.getAttribute("west") + ",");
        return strBuilder.toString();

    }





}
