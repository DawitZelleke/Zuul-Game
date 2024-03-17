import java.util.List;
import java.util.Stack;

/**
 * This class represents the main class of the "World of Zuul" text-based adventure game.
 * Users navigate through different rooms and interact with the game environment.
 *
 * The Game class creates and initializes all necessary components of the game, including rooms,
 * the parser for interpreting user commands, and manages the game flow.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 *
 * @author Lynn Marshall
 * @version October 21, 2012
 *
 * @author Dawit Zelleke (101139907)
 */
public class Game {
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private boolean gameFinished;
    private Stack<Room> previousRoomStack;
    private Item carriedItem;
    private Beamer beamer;
    public static List<Room> rooms;
    private int itemsCarried;
    private boolean hasCookie;
    


    /**
     * Constructs a Game object and initializes its internal components.
     */
    public Game() {
        createRooms();
        parser = new Parser();
        previousRoom = null;
        gameFinished = false;
        previousRoomStack = new Stack<>();
        itemsCarried = 0;
        hasCookie = false;

    }

    /**
     * Creates all the rooms and establishes the exits between them.
     */
    private void createRooms() {
        
        Room outside, theatre, pub, lab, office, transporterRoom;
        Item cookie1, cookie2, chair1, chair2, chair3, chair4, bar, computer1, computer2, computer3, tree1, tree2,beamer1,beamer2;

        // Create some items
        cookie1 = new Item( "cookie", "a chocolate chip cookie", 0.1);
        cookie2 = new Item("cookie", "a chocolate chip cookie", 0.1);
        chair1 = new Item("chair1","a wooden chair", 5);
        chair2 = new Item("chair2","a wooden chair", 5);
        chair3 = new Item("chair3","a wooden chair", 5);
        chair4 = new Item("chair4","a wooden chair", 5);
        bar = new Item("bar","a long bar with stools", 95.67);
        computer1 = new Item("computer1","a PC", 10);
        computer2 = new Item("computer2","a Mac", 5);
        computer3 = new Item("computer3","a PC", 10);
        tree1 = new Item("Tree1","a fir tree", 500.5);
        tree2 = new Item("Tree2","a fir tree", 500.5);
        beamer1 = new Beamer("beamer1","a beamer");
        beamer2 = new Beamer("beamer2","a beamer");

        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transporterRoom = new TransporterRoom("in the transporter room");
    
        // put items in the rooms

        outside.addItem("", tree1);
        outside.addItem("", tree2);
        theatre.addItem("", chair1);
        pub.addItem("", bar);
        lab.addItem("", chair2);
        lab.addItem("", computer1);
        lab.addItem("", chair3);
        lab.addItem("", computer2);
        office.addItem("", chair4);
        office.addItem("", computer3);
        outside.addItem("", beamer1);
        lab.addItem("", beamer2);

        // Set up the exits for each room
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // Start the game outside

        // Add transporter room
        transporterRoom.setExit("north", outside);
        transporterRoom.setExit("east", theatre);
        transporterRoom.setExit("south", lab);
        transporterRoom.setExit("west", pub);

        // Add transporter room to the list of rooms
        Game.rooms.add(transporterRoom);
        Game.rooms.add(outside);
        Game.rooms.add(theatre);
        Game.rooms.add(pub);
        Game.rooms.add(lab);
        Game.rooms.add(office);
    }



    /**
     * Initiates the game loop and processes user commands until the game is finished.
     */
    public void play() {
        printWelcome();

        // Main game loop: continuously process commands until game is finished
        while (!gameFinished) {
            Command command = parser.getCommand();
            processCommand(command);
        }
        System.out.println("Thank you for playing. Goodbye.");
    }

    /**
     * Prints the welcome message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Processes a given command by executing the corresponding action.
     *
     * @param command The command to be processed
     */
    private void processCommand(Command command) {
        String commandWord = command.getCommandWord();

        switch (commandWord) {
            case "help":
                printHelp();
                break;
            case "go":
                goRoom(command);
                break;
            case "look":
                look(command);
                break;
            case "eat":
                eat();
                break;
            case "take":
                take(command);
                break;
            case "drop":
                drop();
                break;
            case "charge":
                chargeBeamer();
                break;
            case "fire":
                fireBeamer();
                break;
            case "quit":
                gameFinished = true;
                break;
            case "back":
                back();
                break;
            case "stackBack":
                stackBack();
                break;
            default:
                System.out.println("I don't know what you mean...");
                break;
        }
    }

    // Implementations of user commands:

    /**
     * Prints out some help information, including available command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /**
     * Attempts to move to a specified direction if possible.
     *
     * @param command The command containing the direction to move
     */
    /**
     * Attempts to move to a specified direction if possible and prints the room description
     * along with the items the player is carrying.
     *
     * @param command The command containing the direction to move
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            // Move to the next room and print its description
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            previousRoomStack.push(previousRoom);
            printRoomAndCarry();
        }
    }

    /**
     * Prints the long description of the current room and any item the player is carrying.
     * This reduces code repetition by centralizing the functionality to print room details
     * and the carried item status.
     */
    private void printRoomAndCarry() {
        System.out.println(currentRoom.getLongDescription());
        if (carriedItem != null) {
            System.out.println("You are carrying: " + carriedItem.getName() + ".");
        }
    }

    /** 
     * "Look" was entered. Check the rest of the command to see
     * whether we really want to look.
     * 
     * @param command The command to be processed
     */
    private void look(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Look what?");
        }
        else {
            // output the long description of this room
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private void take(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        if (itemsCarried >= 5 && !hasCookie) {
            System.out.println("You must eat a cookie before picking up more items.");
            return;
        }

        String itemName = command.getSecondWord();
        Item item = currentRoom.removeItem(itemName);

        if (item != null) {
            if (carriedItem != null) {
                System.out.println("You are already holding something.");
                currentRoom.addItem(item.getName(), item); // Put the item back in the room
            } else {
                System.out.println("You picked up " + item.getName() + ".");
                carriedItem = item;
                itemsCarried++;
            }
        } else {
            System.out.println("That item is not in the room.");
        }
    }

    // drop command implementation
    private void drop() {
        if (carriedItem != null) {
            System.out.println("You have dropped " + carriedItem.getName() + ".");
            currentRoom.addItem("", carriedItem);
            carriedItem = null;
            itemsCarried--;
        } else {
            System.out.println("You have nothing to drop.");
        }
    }

    // eat command modification
    private void eat() {
        if (carriedItem instanceof Item && ((Item) carriedItem).getName().equals("cookie")) {
            System.out.println("You have eaten the cookie.");
            carriedItem = null;
            hasCookie = true; // set hasCookie flag to true
            itemsCarried = 0; // reset itemsCarried counter
        } else {
            System.out.println("You have no food to eat.");
        }
    }

     private void chargeBeamer() {
        if (!(carriedItem instanceof Beamer)) {
            System.out.println("You are not carrying a beamer.");
        } 
        else {
            Beamer beamer = (Beamer) carriedItem;
            if (beamer.isCharged()) {
                System.out.println("The beamer is already charged.");
            } else {
                beamer.charge(currentRoom);
                System.out.println("The beamer has been charged.");
            }
        }
    }

    private void fireBeamer() {
        if (!(carriedItem instanceof Beamer)) {
            System.out.println("You are not carrying a beamer.");
        }   else {
            Beamer beamer = (Beamer) carriedItem;
            if (!beamer.isCharged()) {
                System.out.println("The beamer is not charged.");
            }   else {
                currentRoom = beamer.fire();
                System.out.println("You've been transported back!");
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }                   
    private void back() {
        if (previousRoom == null) {
            System.out.println("No room to go back to.");
        } else {
            Room temp = currentRoom;
            currentRoom = previousRoom;
            previousRoom = temp;
            previousRoomStack.push(temp);
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private void stackBack() {
        if (previousRoomStack.isEmpty()) {
            System.out.println("No room to go Stack back to.");
        } else {
            previousRoom = currentRoom;
            currentRoom = previousRoomStack.pop();
            System.out.println(currentRoom.getLongDescription());
        }
    }
}
