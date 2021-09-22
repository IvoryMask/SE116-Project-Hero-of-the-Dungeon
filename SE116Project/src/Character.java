import java.util.*;

public class Character {

    // attributes
    boolean isAlive;
    int hitPoints;
    String name;

    ArrayList<Item> inventory;
    ArrayList<Weapon> weapons;
    ArrayList<Clothing> clothes;
    Weapon currentWeapon;
    Clothing clothing;
    int totalWeight;

    Room room;
    Level level;

    final int weightLimit = 100;

    // constructor
    public Character(Room room){
        inventory = new ArrayList<>();
        weapons = new ArrayList<>();
        clothes = new ArrayList<>();
        currentWeapon = new Bow(bows.longbows);
        clothing = new Clothing(armors.light_clothing);

        clothes.add(clothing);
        weapons.add(currentWeapon);
        inventory.add(currentWeapon);
        inventory.add(clothing);

        totalWeight = 0;
        hitPoints = 100;
        isAlive = true;
        name = "";

        this.room = room;
        level = room.getLevel();
    }

    // Getter + Setters
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean getIsAlive(){
        return isAlive;
    }

    private void setAlive(){
        if(hitPoints <= 0)
            isAlive = false;
        else
            isAlive = true;
    }

    public int getHitPoints(){
        return hitPoints;
    }

    public Room getRoom(){
        return room;
    }

    public Level getLevel(){
        return level;
    }

    public Weapon getCurrentWeapon(){
        return currentWeapon;
    }

    public Clothing getClothing(){
        return clothing;
    }

    public void increaseHitPoints(int hitPoints){
        this.hitPoints += hitPoints;
    }

    public void decreaseHitPoints(int hitPoints){
        this.hitPoints -= hitPoints;
        setAlive();
    }

    public void increaseTotalWeight(int weight){
        this.totalWeight += weight;
    }

    public void decreaseTotalWeight(int weight){
        this.totalWeight -= weight;
    }

    // Functions
    public int calculateTotalWeight(ArrayList<Item> items) {

        int totalWeight2 = 0;

        for(Item item: items){
            totalWeight2 += item.getWeight();
        }

        return totalWeight2;
    }

    public void displayInventory(){
        System.out.println("You have the following items: ");

        for(int i = 0; i < inventory.size(); i++){
            System.out.println((i + 1) + ". " + inventory.get(i).getName());
        }
        System.out.println("Total weight: " + totalWeight);
    }

    public void displayWeapons(){

        System.out.println("These are the weapons in your inventory:");
        int count = 0;

        for(Weapon weapon: weapons){
            count++;

            System.out.println(count + ". Name: " + weapon.getName() + " | Damage: " + weapon.getDamage() +
                    " Range: " + weapon.getRange() + " | Weight: " + weapon.getWeight());
        }
    }

    public void displayClothes(){

        System.out.println("These are the armors in your inventory:");
        int count = 0;

        for(Clothing clothing: clothes){
            count++;

            System.out.println(count + ". Name: " + clothing.getName() + " | Damage: " + clothing.getProtection() +
                    " | Weight: " + clothing.getWeight());
        }
    }

    // movement

    // weapon use

    // current room
    public void enterRoom(){
        System.out.println("There are " + room.townspeople.length + " townspeople,");
        System.out.println("And " + room.monsters.size() + " monsters in the room.");

        room.displayDoors();
    }

}

class Hero extends Character{

    // attributes
    int peopleSaved;
    String background;
    String gender;
    String description;
    Scanner scan = new Scanner(System.in);

    public Hero(Room room){
        super(room);
        peopleSaved = 0;
    }

    // Getters + Setters
    public void setBackground(String background){
        this.background = background;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setDescription(String description){
        this.description = description;
    }


    // Choose Weapon
    public void chooseWeapon(){
        displayWeapons();

        System.out.print("Enter the number of the weapon you want to use (0 for none): ");
        int number = scan.nextInt();

        if(number == 0){
            return;
        }

        System.out.println();

        currentWeapon = weapons.get(number - 1);
    }

    // Choose armor
    public void chooseArmor(){
        displayClothes();

        System.out.print("Enter the number of the armor you want to use (0 for none): ");
        int number = scan.nextInt();

        if(number == 0){
            return;
        }

        System.out.println();

        clothing = clothes.get(number - 1);
    }

    // Pick items
    public void pickItems(){

        room.displayItems();

        System.out.print("Drop before pick? (y-->1/n-->0): ");
        int answer = scan.nextInt();
        System.out.println();

        if(answer == 1){
            dropItems();
            displayInventory();
        }

        System.out.println("Enter the item numbers you want to pick (Enter \"0\" to pick all)");
        System.out.println("(Enter \"-1\" to stop)");

        ArrayList<Integer> indexes = new ArrayList<>();

        while (scan.hasNext()) {
            if (scan.hasNextInt()) {
                int a = scan.nextInt();

                if(a == -1){
                    break;
                }

                if(a == 0){
                    if((calculateTotalWeight(room.getItems()) + totalWeight) < weightLimit) {
                        increaseTotalWeight(calculateTotalWeight(room.getItems()));
                        inventory.addAll(room.getItems());
                        addWeapons();
                        addClothes();
                        room.removeAllItems();
                        break;
                    }
                    else{
                        System.out.println("You do not have enough room to pick all items");
                        break;
                    }
                }
                indexes.add(a);
            }
            else
                scan.next();
        }

        if(indexes.size() != 0){

            if((calculateTotalWeight(room.getSelectedItems(indexes)) + totalWeight)  < weightLimit) {
                increaseTotalWeight(calculateTotalWeight(room.getSelectedItems(indexes)));
                inventory.addAll(room.getSelectedItems(indexes));
                room.items.removeAll(room.getSelectedItems(indexes));
                addWeapons();
                addClothes();
            }
            else{
                System.out.println("You do not have enough room to pick these items");
            }
        }
    }

    private void addWeapons(){

        weapons.clear();

        for(Item item: inventory){
            if(item instanceof Weapon)
                weapons.add((Weapon) item);
        }
    }

    private void addClothes(){

        clothes.clear();

        for(Item item: inventory){
            if(item instanceof Clothing)
                clothes.add((Clothing) item);
        }
    }

    public void displayHero(){
        System.out.println("Hero:");
        System.out.println("Name: " + name);
        System.out.println("Background: " + background);
        System.out.println("Gender: " + gender);
        System.out.println("Description: " + description);
        System.out.println("HP: " + hitPoints);
        System.out.println("# of Saved people: " + peopleSaved);
        System.out.println();
    }

    // Drop items
    public void dropItems() {

        displayInventory();
        System.out.println("Enter the item numbers you want to drop (Enter \"0\" to drop all)");
        System.out.println("(Enter \"-1\" to stop)");

        while (scan.hasNext()) {
            if (scan.hasNextInt()) {
                int a = scan.nextInt();

                if(a == -1){
                    break;
                }

                if(a == 0){
                    totalWeight = 0;
                    inventory.clear();
                    weapons.clear();
                    clothes.clear();
                    break;
                }
                weapons.remove(inventory.get(a - 1));
                clothes.remove(inventory.get(a - 1));
                decreaseTotalWeight(inventory.get(a - 1).getWeight());
                inventory.remove((a - 1));
            }
            else
                scan.next();
        }
    }

    // rescue
    public void rescue(){

        if(room.getTownspeople().length == 0){
            System.out.println("There is no townpeople in this room!");
            return;
        }

        for(Monster monster: room.getMonsters()){
            if(monster.getIsAlive()){
                System.out.println("You cannot rescue townspeople before killing all the monsters");
                return;
            }
        }
        room.rescueTownspeople();
        System.out.println("All townspeople are saved!");

        for(Townspeople townspeople: room.getTownspeople()){
            townspeople.setSaved(true);
        }
        room.getTownspeople()[0].heal(this);

    }

    // fight
    public boolean fight(Monster monster){
        if(monster.getDistanceFromHero() - currentWeapon.getRange() <= 0){
            monster.decreaseHitPoints(currentWeapon.getDamage());
            System.out.println("The hero caused " + currentWeapon.getDamage() + "HP damage to the monster");

            if(! monster.getIsAlive()){
                System.out.println("Bravo! The monster is dead!");
                monster.die();
                return true;
            }

            monster.fightBack(this);
            System.out.println("The monster caused " + (monster.currentWeapon.getDamage() - clothing.getProtection()) + "HP damage to the hero");

            if(! this.getIsAlive()){
                System.out.println("The hero has fallen...");
                System.out.println("Your score is: " + calculateValue());
                return false;
            }

        }
        else{
            System.out.println("The monster is not in your range.");
            return true;
        }
        return true;
    }

    public void setRoom(int number){
        Room room = level.findByNumber(number);
        this.room = room;
    }

    // Overloaded
    public void setRoom(Room room){
        this.room = room;
    }

    private int calculateValue(){
        int sum = 0;
        for(Item item: inventory){
            sum += item.value;
        }

        return sum;
    }
}

class Monster extends Character{

    // attributes
    int distanceFromHero;

    // constructor
    public Monster(Room room){
        super(room);

        hitPoints = 50;
        isAlive = true;

        int min = 0;
        int max = 40;
        distanceFromHero = min + (int) (Math.random() * (max - min + 1));

        names theName = names.values()[new Random().nextInt(names.values().length)];
        name = theName.name();

        min = 0;
        max = 2;
        int inventorySize = min + (int) (Math.random() * (max - min + 1));

        switch(inventorySize){

            case 2:
                Sword newWeapon = new Sword(swords.values()[new Random().nextInt(swords.values().length)]);
                weapons.add(newWeapon);
                inventory.add(newWeapon);

            case 1:
                Axe newAxe = new Axe(axes.values()[new Random().nextInt(axes.values().length)]);
                weapons.add(newAxe);
                inventory.add(newAxe);
            case 0:
                Clothing newCloth = new Clothing(armors.values()[new Random().nextInt(armors.values().length)]);
                clothes.add(newCloth);
                inventory.add(newCloth);
                break;

        }

    }

    // Getters + Setters
    public int getDistanceFromHero(){
        return distanceFromHero;
    }

    // functions
    public void die(){

        if(hitPoints > 0)
            return;

        isAlive = false;

        room.addItems(inventory);
        room.addItems(currentWeapon);
        room.addItems(clothing);

        inventory.clear();
        clothing = null;
        currentWeapon = null;

        for(Monster monster: room.getMonsters()){
            if(monster == this){
                room.getMonsters().remove(monster);
                break;
            }
        }

    }

    // fight back
    public void fightBack(Hero hero){
        hero.decreaseHitPoints(currentWeapon.getDamage() - hero.clothing.getProtection());
    }

}

class Townspeople extends Character{

    // attributes
    final int healPoint = 20;
    boolean isSaved;

    // constructor
    public Townspeople(Room room){
        super(room);
        isSaved = false;
    }

    // Getters + Setters
    public void setSaved(boolean isSaved){
        this.isSaved = isSaved;
    }

    // functions
    public void heal(Hero hero){

        int min = 0;
        int max = 4;
        int rdm = min + (int) (Math.random() * (max - min + 1));

        if(isSaved && rdm == 2)
            hero.increaseHitPoints(healPoint);
    }
}
