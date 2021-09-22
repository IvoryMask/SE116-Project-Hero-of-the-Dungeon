public class Item {
    // attributes
    String name;
    int weight;
    int value;

    public String getName(){
        return name;
    }

    public int getWeight(){ return weight; }
}

class Clothing extends Item{

    // attributes
    int protection;

    public Clothing(armors theArmor){
        switch (theArmor){
            case light_clothing: weight = 10; value = 10; name = "light clothing"; protection = 5; break;
            case leather_armor: weight = 20; value = 20; name = "leather armor"; protection = 10; break;
            case chainmail_armor: weight = 30; value = 30; name = "chainmail armor"; protection = 20; break;
        }
    }

    // Getters + Setters
    public int getProtection(){
        return protection;
    }

}

class Weapon extends Item{

    // attributes
    int damage;
    int range;

    // functions
    public int getDamage(){
        return damage;
    }

    public int getRange(){
        return range;
    }
}

class Sword extends Weapon{

    public Sword(swords theSword){
        switch (theSword){
            case dagger: weight = 5; value = 5; name = "dagger"; damage = 30; range = 10; break;
            case shortsword: weight = 20; value = 20; name = "short sword"; damage = 20; range = 20; break;
            case longsword: weight = 30; value = 30; name = "long sword"; damage = 35; range = 30; break;
        }
    }
}

class Axe extends Weapon{

    public Axe(axes theAxe){
        switch (theAxe){
            case smallaxes: weight = 20; value = 20; name = "small axe"; damage = 30; range = 10; break;
            case axes: weight = 30; value = 30; name = "axe"; damage = 30; range = 20; break;
            case broad_axes: weight = 35; value = 35; name = "broad axe"; damage = 35; range = 30; break;
        }
    }
}

class Bow extends Weapon{

    public Bow(bows theBow){
        switch (theBow){
            case shortbows: weight = 5; value = 5; name = "short bow"; damage = 20; range = 10; break;
            case longbows: weight = 15; value = 15; name = "long bow"; damage = 10; range = 20; break;
            case composite_bow: weight = 20; value = 20; name = "composite bow"; damage = 15; range = 15; break;
        }
    }
}
