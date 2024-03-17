import java.util.ArrayList;
import java.util.List;

/**
 * Represents a transporter room in the game, where the player is randomly transported to another room upon leaving.
 * @author Dawit Zelleke (101139907)
 */

public class TransporterRoom extends Room {

    private List<Room> possibleRooms;

    /**
     * Constructs a TransporterRoom with a description.
     * 
     * @param description The description of the transporter room.
     */
    public TransporterRoom(String description) {
        super(description);
        possibleRooms = new ArrayList<>();
    }

    /**
     * Adds a room to the list of possible rooms to transport to.
     * 
     * @param room The room to add to the list of possible rooms.
     */
    public void addPossibleRoom(Room room) {
        possibleRooms.add(room);
    }

    /**
     * Overrides the getExit method to return a random room.
     * 
     * @param direction Ignored.
     * @return A randomly selected room.
     */
    @Override
    public Room getExit(String direction) {
        return findRandomRoom();
    }

    /**
     * Chooses a random room from the list of possible rooms.
     * 
     * @return The randomly selected room.
     */
    private Room findRandomRoom() {
        int randomIndex = (int) (Math.random() * possibleRooms.size());
        return possibleRooms.get(randomIndex);
    }
}
