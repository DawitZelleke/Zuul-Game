import java.util.HashMap;
import java.util.Set;

/**
 * This class represents a room in a game. It holds information about the room's description,
 * exits to other rooms, and items contained within the room.
 * @author Dawit Zelleke (101139907)
 */
public class Room {
    private String description; // Description of the room
    private HashMap<String, Room> exits; // Maps directions to other rooms (exits)
    private HashMap<String, Item> items; // Maps item names to Item objects in the room

    /**
     * Constructor for creating a new Room.
     * @param description A string describing the room.
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<>(); // Initialize the exits HashMap
        items = new HashMap<>(); // Initialize the items HashMap
    }

    /**
     * Sets an exit for this room.
     * @param direction The direction of the exit.
     * @param neighbor The room that is in the direction of the exit.
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Adds an item to the room.
     * @param itemName The name of the item.
     * @param item The item to be added.
     */
    public void addItem(String itemName, Item item) {
        items.put(itemName, item);
    }

    /**
     * Removes an item from the room.
     * @param itemName The name of the item to be removed.
     * @return The item that was removed, or null if no item with the given name was found.
     */
    public Item removeItem(String itemName) {
        return items.remove(itemName);
    }

    /**
     * Gets a short description of the room.
     * @return A string containing the description of the room.
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * Gets a long description of the room, including exits and items.
     * @return A string containing the long description of the room.
     */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString() + "\n" + getItemString();
    }

    /**
     * Constructs a string listing all exits from this room.
     * @return A string describing all exits.
     */
    private String getExitString() {
        String exitString = "Exits:";
        for (String exit : exits.keySet()) {
            exitString += " " + exit;
        }
        return exitString;
    }

    /**
     * Constructs a string listing all items in this room.
     * @return A string describing all items in the room.
     */
    private String getItemString() {
        String itemString = "Items:";
        if (items.isEmpty()) {
            return "No items in this room.";
        }
        for (String itemName : items.keySet()) {
            itemString += " " + itemName;
        }
        return itemString;
    }

    /**
     * Retrieves an exit based on the given direction.
     * @param direction The direction of the exit to retrieve.
     * @return The Room that is in the direction of the exit, or null if there is no room in that direction.
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }
}
