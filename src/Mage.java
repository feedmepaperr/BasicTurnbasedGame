
public class Mage extends BaseHero {

    public Mage(String name) {
        super(name, 70, 5, 5, 10, 15); // adjust as needed for balance :)
    }

    // Implement attack method
    @Override
    public int attack(CombatEntity target) {
        //figure out range of magic damage
        int MAX_HIT = this.magic + (this.attack / 2) + 1;
        int MIN_HIT = this.magic - ((this.attack / 2) + 1);
        int damage = (int) (Math.random() * (MAX_HIT - MIN_HIT + 1)) + MIN_HIT;

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

}
