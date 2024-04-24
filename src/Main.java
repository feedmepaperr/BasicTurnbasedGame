import java.util.*;

public class Main {
    private static final int NUM_HEROES = 3;
    private static List<BaseHero> heroes;
    private static List<BaseEnemy> enemies;
    private static boolean gameOver = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        heroes = selectHeroes(sc); // Select heroes for the game
        enemies = initializeEnemies(); // Initialize enemies

        while (!gameOver) {
            List<CombatEntity> combatEntities = initializeCombatEntities(heroes, enemies); // Initialize combat entities
            playRound(combatEntities, sc); // Play a round of combat
        }

        sc.close();
        System.out.println();
        System.out.println("Game Over");
    }

    // Method to allow player to select heroes
    private static List<BaseHero> selectHeroes(Scanner scanner) {
        List<BaseHero> heroes = new ArrayList<>();
        List<String> chosenNames = new ArrayList<>(); // List to store chosen names
        System.out.println("Welcome to the game! Choose your heroes:");
        System.out.println("1. Fighter");
        System.out.println("2. Mage");
        System.out.println("3. Ranger");
        System.out.println("4. Tank");

        for (int i = 0; i < NUM_HEROES; i++) {
            System.out.println("Select hero " + (i + 1) + " of " + NUM_HEROES + ":");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            BaseHero hero = createHero(choice, chosenNames); // Create the hero
            heroes.add(hero);
            System.out.println("Hero " + (i + 1) + " selected, meet " + hero.getName());
        }
        sleep(1000);
        return heroes;
    }

    // Method to create a hero based on player's choice
    private static BaseHero createHero(int choice, List<String> chosenNames) {
        while (true) {
            switch (choice) {
                case 1 -> {
                    return new Fighter(pickHeroName(chosenNames) + " the Fighter");
                }
                case 2 -> {
                    return new Mage(pickHeroName(chosenNames) + " the Mage");
                }
                case 3 -> {
                    return new Ranger(pickHeroName(chosenNames) + " the Ranger");
                }
                case 4 -> {
                    return new Tank(pickHeroName(chosenNames) + " the Tank");
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // Method to randomly pick a hero name that hasn't been chosen yet
    private static String pickHeroName(List<String> chosenNames) {
        // Define a set of possible names
        String[] possibleNames = {"Joe", "Alex", "Wendy", "Jacob", "Ellie", "Ollie", "Ethan", "Darrell"};

        // Remove already chosen names from possible names
        List<String> availableNames = new ArrayList<>(Arrays.asList(possibleNames));
        availableNames.removeAll(chosenNames);

        // Randomly select a name from available names
        Random random = new Random();
        String chosenName = availableNames.get(random.nextInt(availableNames.size()));

        // Add the chosen name to the list of chosen names
        chosenNames.add(chosenName);

        return chosenName;
    }

    // Method to initialize enemies
    private static List<BaseEnemy> initializeEnemies() {
        return new ArrayList<>(Arrays.asList(new Goblin("Goblin 1"), new Highwayman("Highwayman 1"), new Kobold("Kobold 1")));
    }

    // Method to initialize combat entities (heroes and enemies) for a round
    private static List<CombatEntity> initializeCombatEntities(List<BaseHero> heroes, List<BaseEnemy> enemies) {
        List<CombatEntity> combatEntities = new ArrayList<>();
        combatEntities.addAll(heroes);
        combatEntities.addAll(enemies);
        Collections.shuffle(combatEntities);
        return combatEntities;
    }

    // Method to play a round of combat
    private static void playRound(List<CombatEntity> combatEntities, Scanner scanner) {
        // Hero and enemy turns
        gameOver = checkGameOver(); // Check if the game is over
        if (gameOver){
            return;
        }
        for (CombatEntity entity : combatEntities) {
            sleep(500); // Pause between turns
            System.out.println();
            System.out.println(entity.getName() + "'s turn:");
            handleTurn(entity, scanner); // Handle entity's turn
            if (entity instanceof BaseEnemy) {
                sleep(1000); // Longer pause after enemy's turn
            }
        }
    }

    // Method to handle a combat entity's turn (both hero and enemy)
    private static void handleTurn(CombatEntity entity, Scanner scanner) {
        if (entity instanceof BaseHero) {
            BaseEnemy targetEnemy = chooseTargetEnemy(scanner); // Choose the target enemy
            if (targetEnemy != null) {
                attackEntity(entity, targetEnemy); // Attack the target enemy
            }
        } else if (entity instanceof BaseEnemy) {
            BaseHero targetHero = randomHero(); // Randomly choose a hero as the target
            attackEntity(entity, targetHero); // Attack the target hero
        }
    }

    // Method to perform an attack on a target entity
    private static void attackEntity(CombatEntity attacker, CombatEntity target) {
        int damageDealt = attacker.attack(target); // Perform the attack
        System.out.println(attacker.getName() + " attacks " + target.getName() + "!");
        System.out.println("Dealt " + damageDealt + " damage.");
        System.out.println(target.getName() + " has " + target.getHealth() + " health remaining.");
        if (target.getHealth() <= 0) {
            if (target instanceof BaseHero) {
                heroes.remove(target); // Remove defeated hero
            } else if (target instanceof BaseEnemy) {
                enemies.remove(target); // Remove defeated enemy
            }
        }
    }

    // Method to check if the game is over
    private static boolean checkGameOver() {
        return heroes.isEmpty() || enemies.isEmpty();
    }

    // Method to choose the target enemy
    private static BaseEnemy chooseTargetEnemy(Scanner scanner) {
        System.out.println("Choose the target enemy:");
        for (int i = 0; i < enemies.size(); i++) {
            System.out.println((i + 1) + ". " + enemies.get(i).getName() + " (" + enemies.get(i).getHealth() + " health)");
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (choice >= 1 && choice <= enemies.size()) {
            return enemies.get(choice - 1);
        } else {
            System.out.println("Invalid choice. Targeting first enemy by default.");
            return enemies.getFirst();
        }
    }

    // Method to get a random hero
    private static BaseHero randomHero() {
        int index = (int) (Math.random() * heroes.size());
        return heroes.get(index);
    }

    private static void sleep(int milli){
        try {
            Thread.sleep(milli); // Pause for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
