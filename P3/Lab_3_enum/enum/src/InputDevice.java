import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class InputDevice {
    private InputStream inputStream;
    private Scanner scanner;

    public InputDevice(InputStream inputStream) {
        this.inputStream = inputStream;
        this.scanner = new Scanner(inputStream);
    }

    public String readLine(){
        return this.scanner.nextLine();
    }

    public List<Fruit> readFruit(List<Fruit> fruitList, String[] args) {
        Random random = new Random();
        int numFruits = 5; // Change this to the desired number of fruits

        Scanner scanner = new Scanner(inputStream);

        for (int i = 0; i < numFruits; i++) {
            int sugar = random.nextInt(100);
            int weight = random.nextInt(300);
            String color = Color.values()[random.nextInt(Color.values().length)].toString();
            int water = random.nextInt(100);

            int randomFruitType = random.nextInt(3);
            if (randomFruitType == 0) {
                fruitList.add(new Banana(sugar, weight, water));
            } else if (randomFruitType == 1) {
                fruitList.add(new Apple(sugar, weight, color, water));
            } else {
                fruitList.add(new Mango(sugar, weight, color, water));
            }
        }

        return fruitList;
    }
}
