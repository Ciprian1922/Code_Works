import java.util.Random;

public class InputDevice {
    public Fruit[] readFruit() {
        // Simulate reading fruit data from an input source (e.g., user input or a file)
        Random random = new Random();
        int numFruits = 5; // Change this to the desired number of fruits
        Fruit[] fruits = new Fruit[numFruits];

        for (int i = 0; i < numFruits; i++) {
            int sugar = random.nextInt(100); // Random sugar content
            int weight = random.nextInt(300); // Random weight
            String color = Fruit.Color.values()[random.nextInt(Fruit.Color.values().length)].toString(); // Random color
            int water = random.nextInt(100); // Random water content

            // Randomly create Banana, Apple, or Mango
            int randomFruitType = random.nextInt(3);
            if (randomFruitType == 0) {
                fruits[i] = new Banana(sugar, weight, water);
            } else if (randomFruitType == 1) {
                fruits[i] = new Apple(sugar, weight, color, water);
            } else {
                fruits[i] = new Mango(sugar, weight, color, water);
            }
        }

        return fruits;
    }
}
