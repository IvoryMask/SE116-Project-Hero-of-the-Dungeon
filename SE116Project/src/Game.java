import java.util.Scanner;

public class Game {

    // attributes
    static boolean isGameOver = false;

    public static void main(String [] args){

        Scanner scan = new Scanner(System.in);

        // adjustments
        Level[] levels = new Level[16];

        for(int i = 0; i < 16; i++){
            levels[i] = new Level();
        }

        Hero hero = new Hero(levels[0].getRooms().get(0));

        System.out.println("Welcome to the Hero of the Dungeon! You can exit by pressing \"e\" anytime you want");
        System.out.print("Enter your hero's name: ");
        hero.setName(scan.nextLine());

        System.out.print("\nWant to add background, gender, and description of the character? (y/n): ");
        System.out.println();

        if(scan.nextLine().equals("y")){
            System.out.print("Background: ");
            hero.setBackground(scan.nextLine());
            System.out.println();

            System.out.print("Gender: ");
            hero.setGender(scan.nextLine());
            System.out.println();

            System.out.print("Description: ");
            hero.setDescription(scan.nextLine());
            System.out.println();
        }

        while(! isGameOver){

            System.out.println("Level " + hero.getLevel().getLevelNumber() + ", Room " + hero.getRoom().getRoomNumber());
            System.out.println("Hero " + hero.getName() + ", " + hero.getHitPoints() + "HP, " +
                    hero.getCurrentWeapon().getName() + ", " + hero.getClothing().getName());

            System.out.print("Action: ");
            String input = scan.nextLine();
            System.out.println();

            if(input.equals("e")){
                isGameOver = true;
                break;
            }
            else if(input.equals("a")){
                if(hero.room.isThereAccessTo(hero.room.getRoomNumber() - 1)){
                    hero.setRoom(hero.room.getRoomNumber() - 1);
                }
                else{
                    System.out.println("There is no door to there.");
                }
            }
            else if(input.equals("d")){
                if(hero.room.isThereAccessTo(hero.room.getRoomNumber() + 1)){
                    hero.setRoom(hero.room.getRoomNumber() + 1);
                }
                else{
                    System.out.println("There is no door to there.");
                }
            }
            else if(input.equals("w")){
                Room goTo = hero.getRoom().isThereAccessTo("up");
                if(goTo != null){
                    hero.setRoom(goTo);
                }
                else{
                    System.out.println("There is no door to there.");
                }
            }
            else if(input.equals("s")){
                Room goTo = hero.getRoom().isThereAccessTo("down");
                if(goTo != null){
                    hero.setRoom(goTo);
                }
                else{
                    System.out.println("There is no door to there.");
                }
            }
            else if(input.equals("attack")){
                hero.room.displayMonsters();

                System.out.print("Choose the one you want to fight with (0 for none):");
                int theMonster = Integer. parseInt(scan.nextLine());
                System.out.println();

                if(theMonster != 0) {
                    if(theMonster <= hero.room.getMonsters().size())
                        hero.fight(hero.room.getMonsterAt(theMonster));
                    else
                        System.out.println("There is no such monster here");
                }
            }
            else if(input.equals("drop")){
                hero.dropItems();
            }
            else if(input.equals("pick")){
                hero.pickItems();
            }
            else if(input.equals("change")){
                System.out.print("What do you want to change: ");
                if(scan.nextLine().equals("armor")){
                    hero.chooseArmor();
                }
                else{
                    hero.chooseWeapon();
                }
                System.out.println();
            }
            else if(input.equals("see")){
                hero.getRoom().displayRoom();
            }
            else if(input.equals("rescue")){
                hero.rescue();
            }
            else if(input.equals("identification")){
                hero.displayHero();
            }
            else if(input.equals("inventory")){
                hero.displayInventory();
            }
            else if(input.equals("weapons")){
                hero.displayWeapons();
            }
            else if(input.equals("clothes")){
                hero.displayClothes();
            }
            else if(input.equals("plan")){
                hero.getRoom().getLevel().displayPlan();
            }
            else if(input.equals("help")){
                System.out.println("Here are the commands you can use:");
                System.out.println("\"e\": exit");
                System.out.println("\"a\": Go left room");
                System.out.println("\"d\": Go right room");
                System.out.println("\"w\": Go corridor up");
                System.out.println("\"s\": Go corridor down");
                System.out.println("\"see\": See doors, stairs, items, and monsters in the door");
                System.out.println("\"attack\": Attack a monster");
                System.out.println("\"rescue\": Rescue townspeople");
                System.out.println("\"change\": Change your armor or weapon");
                System.out.println("\"drop\": Drop item(s) from your inventory");
                System.out.println("\"pick\": Pick items from the room");
                System.out.println("\"identification\": Identification about your hero");
                System.out.println("\"inventory\": Display the inventory");
                System.out.println("\"weapons\": Display the weapons");
                System.out.println("\"clothes\": Display the clothes");
                System.out.println("\"plan\": Display the level plan");
                System.out.println();
            }

        }

    }
}
