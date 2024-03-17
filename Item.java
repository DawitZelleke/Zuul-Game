/**
 * Represents an item within a room in the adventure game.
 * Each item has a description and a weight.
 * @author Dawit Zelleke(101139907)
 */
public class Item {
    private String description;
    private double weight;
    private String name;

    /**
     * Constructs an Item object with the given description and weight.
     *
     * @param description The description of the item.
     * @param weight      The weight of the item.
     */
    public Item(String name,String description, double weight) {
        this.description = description;
        this.weight = weight;
        this.name = name;
    }
    

    /**
     * Retrieves the description of the item.
     *
     * @return The description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the name of the item
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the weight of the item.
     *
     * @return The weight of the item.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Provides a string representation of the item, including its description and weight.
     *
     * @return A string representation of the item.
     */
    @Override
    public String toString() {
        return description + " ( Weight: " + weight + ")";
    }
}
