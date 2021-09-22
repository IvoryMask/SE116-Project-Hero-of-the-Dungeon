import java.util.*;

public class Room {

    // attributes
    ArrayList<Item> items;
    Townspeople[] townspeople;

    ArrayList<Monster> monsters;
    ArrayList<Room> doors;
    ArrayList<Room> stairs;
    ArrayList<Room> connectingDoors;

    int roomNumber;
    static int roomCounter = 0;
    Level level;
    int set;

    // constructor
    public Room(Level level){
        roomCounter++;
        roomNumber = roomCounter;

        this.level = level;

        monsters = new ArrayList<>();
        items = new ArrayList<>();
        doors = new ArrayList<>();
        stairs = new ArrayList<>();
        connectingDoors = new ArrayList<>();

        int min = 0; // There can be 0 monsters or townspeople
        int max = 5; // Programmer choice
        int x = min + (int) (Math.random() * (max - min + 1));

        townspeople = new Townspeople[(int) (x/3)];

        for(int i = 0; i < (int)(x/3); i++){
            townspeople[i] = new Townspeople(this);
        }

        for(int i = 0; i < x; i++){
            monsters.add( new Monster(this));
        }
    }

    // Getters + Setters
    public ArrayList<Monster> getMonsters(){
        return monsters;
    }

    public Townspeople[] getTownspeople(){
        return townspeople;
    }

    public void rescueTownspeople(){
        townspeople = null;
    }

    public int getRoomNumber(){
        return roomNumber;
    }

    public ArrayList<Item> getItems(){
        return items;
    }

    public Level getLevel(){
        return level;
    }

    public Monster getMonsterAt(int index){
        return monsters.get(index - 1);
    }

    public void setSet(int set){
        this.set = set;
    }

    public int getSet(){
        return set;
    }

    // functions
    public void addItems(ArrayList<Item> monstersItems){
        items.addAll(monstersItems);
    }

    // Overloaded
    public void addItems(Item item){
        items.add(item);
    }

    public void removeAllItems(){
        items.clear();
    }

    /*/
    Removes the items in selected items
    And returns the ArrayList of removed items
    So that the hero can add them into his/her inventory
     */
    public ArrayList<Item> removeSelectedItems(ArrayList<Integer> indexes){

        ArrayList<Item> collected = new ArrayList<>();

        for(int index: indexes) {
            collected.add(items.get(index - 1));
            items.remove(index - 1);
        }

        return collected;
    }

    public ArrayList<Item> getSelectedItems(ArrayList<Integer> indexes){

        ArrayList<Item> collected = new ArrayList<>();

        for(int index: indexes) {
            collected.add(items.get(index - 1));
        }

        return collected;
    }

    public void displayRoom(){
        System.out.println("The hero sees: ");
        displayDoors();
        displayStairs();
        displayItems();
        displayMonsters();
    }


    public void displayItems(){
        System.out.println("The room has the following items:");

        for(int i = 0; i < items.size(); i++){
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }

        System.out.println();
    }

    public void displayDoors(){
        System.out.println("This room has door(s) opening to:" );

        for(Room room: doors){
            System.out.println("Room - " + room.getRoomNumber() + " ");
        }

        System.out.println();
    }

    public void displayStairs(){
        System.out.println("This room has stairs opening to:" );

        for(Room room: stairs){
            if(room.level.getLevelNumber() < this.level.getLevelNumber())
                System.out.println("Stairs (up)");
            else{
                System.out.println("Stairs (down)");
            }
        }

        System.out.println();
    }

    public void displayMonsters(){
        System.out.println("The monsters in this room: " );

        int count = 0;

        for(Monster monster: monsters) {

            if (monster.getIsAlive()) {
                count++;
                System.out.println(count + ". " + monster.getName() + " | Distance: " + monster.getDistanceFromHero() +
                        " | HP: " + monster.getHitPoints());

            }
        }

        System.out.println();
    }

    public void addDoor(Room room){
        doors.add(room);
    }

    public void addDoor(Room room, boolean isConnecting){
        doors.add(room);
        connectingDoors.add(room);
    }

    public void addStairs(Room room){
        stairs.add(room);
    }

    public boolean isThereAccessTo(int roomNumber){
        for(Room door: doors){
            if(door.getRoomNumber() == roomNumber){
                return true;
            }
        }
        return false;
    }

    public Room isThereAccessTo(String way){
        if(connectingDoors.size() == 0)
            return null;
        for(Room door: connectingDoors){
            if(way.equals("down") && door.getSet() > set){
                return door;
            }
            if(way.equals("up") && door.getSet() < set){
                return door;
            }
        }
        return null;
    }
}