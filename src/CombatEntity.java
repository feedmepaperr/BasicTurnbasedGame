
public abstract class CombatEntity {

    protected String name;
    protected int health;
    protected int attack;
    protected int defense;
    protected int magic;
    protected int agility;

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    // Implement attack method
    public int attack(CombatEntity target) {
        // Calculate damage based on attacker's attack stat
        int damage = this.attack;

        damage -= target.getDefense();

        // Ensure damage is non-negative
        damage = Math.max(0, damage);

        // Calculate dodge chance based on target's agility
        double dodgeChance = calculateDodgeChance(target.getAgility());

        // Generate a random number between 0 and 1
        double randomValue = Math.random();

        // Check if the target dodges the attack
        if (randomValue < dodgeChance) {
            System.out.println(target.getName() + " dodges the attack!");
            return 0; // Deal no damage if the attack is dodged
        }

        // Apply damage to the target's health
        target.takeDamage(damage);

        return damage;
    }

    // Method to calculate dodge chance based on agility
    private double calculateDodgeChance(int agility) {
        // Assuming agility affects dodge chance linearly
        double dodgeChancePerPoint = 0.01; // 1% dodge chance per point of agility
        double dodgeChance = agility * dodgeChancePerPoint;
        return Math.min(dodgeChance, 0.99); // Cap dodge chance at 99%
    }

    protected int getDefense() {
        return defense;
    }

    protected int getAgility() {
        return agility;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }
}