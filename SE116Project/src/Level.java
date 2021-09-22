import java.util.*;

public class Level {

    // attributes
    ArrayList<Room> rooms;
    int sets;
    int room;
    int totalRoom;

    int levelNumber;
    static int levelCounter = 0;

    // constructor
    public Level(){

        levelCounter++;
        levelNumber = levelCounter;

        rooms = new ArrayList<>();
        int min = 2;
        int max = 4;

        sets = min + (int) (Math.random() * (max - min + 1));
        room = min + (int) (Math.random() * (max - min + 1));

        totalRoom = sets * room;

        createRooms();
        connectCoridors();
    }

    // Getters + Setters
    public int getTotalRoom(){
        return totalRoom;
    }

    public ArrayList<Room> getRooms(){
        return rooms;
    }

    public int getRoom(){
        return room;
    }

    public int getLevelNumber(){
        return levelNumber;
    }


    // functions
    public void createRooms(){

        for(int i = 0; i < sets; i++){

            Room previousRoom = new Room(this);
            rooms.add(previousRoom);
            previousRoom.setSet(i);

            for(int j = 1; j < room; j++){
                Room currentRoom = new Room(this);
                rooms.add(currentRoom);
                currentRoom.setSet(i);

                previousRoom.addDoor(currentRoom);
                currentRoom.addDoor(previousRoom);

                previousRoom = currentRoom;
            }
        }
    }

    public void connectCoridors(){

        int min = 0;
        int max = room - 1;
        Room previousRoom = rooms.get(min + (int) (Math.random() * (max - min + 1)));

        for(int i = 1; i < sets; i++){

            max = (i + 1)*room - 1;
            min = i*room;
            Room currentRoom = rooms.get(min + (int) (Math.random() * (max - min + 1)));

            previousRoom.addDoor(currentRoom, true);
            currentRoom.addDoor(previousRoom, true);

            previousRoom = rooms.get(min + (int) (Math.random() * (max - min + 1)));

        }
    }

    public void addStairs(Level otherLevel){
        int min = 1;
        int max = totalRoom;
        int randomRoomFromMe = min + (int) (Math.random() * (max - min + 1));
        Room randomRoom1 = rooms.get(randomRoomFromMe);

        max = otherLevel.getTotalRoom();
        int randomRoomFromOther = min + (int) (Math.random() * (max - min + 1));
        Room randomRoom2 = otherLevel.getRooms().get(randomRoomFromOther);;

        randomRoom1.addStairs(randomRoom2);
        randomRoom2.addStairs(randomRoom1);
    }

    public void displayPlan(){
        int count = 0;

        for(int i = 0; i < sets; i++){

            for(int j = 0; j < room; j++){
                if(j != 0)
                    System.out.print("←");

                if(rooms.get(count).connectingDoors != null){
                    for(Room door: rooms.get(count).connectingDoors){
                        if(door.getSet() > rooms.get(count).getSet()){
                            System.out.print("↓");
                        }
                        if(door.getSet() < rooms.get(count).getSet()){
                            System.out.print("↑");
                        }
                    }
                }

                System.out.print("R" + rooms.get(count).getRoomNumber() );

                if(j != (room - 1))
                    System.out.print("→ ");

                count++;
            }
            System.out.println();
        }
    }

    public Room findByNumber(int number){
        for(Room room: rooms){
            if(room.getRoomNumber() == number){
                return room;
            }
        }

        return null;
    }

}
