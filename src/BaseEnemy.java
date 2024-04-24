
public class BaseEnemy extends CombatEntity {

    public BaseEnemy(String name, int health, int attack, int defense, int agility) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.agility = agility;
    }

}