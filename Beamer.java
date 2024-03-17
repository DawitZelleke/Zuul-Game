/**
 * Represents a beamer item that can be charged with a room and then used to teleport back to that room.
 * Inherits from the {@code Item} class.
 * @author (101139907)
 */
public class Beamer extends Item {
    /**
     * The room with which the beamer is currently charged. Null if the beamer is not charged.
     */
    private Room chargedRoom;

    /**
     * Indicates whether the beamer is charged with a room.
     */
    private boolean isCharged;

    /**
     * Constructs a new Beamer with the specified name and description. Initially, the beamer is not charged.
     *
     * @param name The name of the beamer.
     * @param description The description of the beamer.
     */
    public Beamer(String name, String description) {
        super(name, description, 0.1);
        this.isCharged = false;
    }

    /**
     * Returns whether the beamer is charged.
     *
     * @return true if the beamer is charged, false otherwise.
     */
    public boolean isCharged() {
        return isCharged;
    }

    /**
     * Charges the beamer with the current room. After charging, the beamer can be used to return to this room.
     *
     * @param currentRoom The room to charge the beamer with.
     */
    public void charge(Room currentRoom) {
        this.chargedRoom = currentRoom;
        this.isCharged = true;
    }

    /**
     * Uses the beamer to return to the charged room, if it is charged. The beamer is then discharged.
     *
     * @return The room to which the beamer was charged, or null if the beamer was not charged.
     */
    public Room fire() {
        if (isCharged) {
            isCharged = false;
            return chargedRoom;
        } else {
            return null;
        }
    }
}
