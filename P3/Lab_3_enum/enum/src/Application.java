import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Application {
    private List<Fruit> fruits;
    private InputDevice inputDevice;
    private OutputDevice outputDevice;
    private String[] args;

    public Application(InputDevice inputDevice, OutputDevice outputDevice, String[] args) {
        this.fruits = new ArrayList<>();
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
        this.args = args;
    }
    //OutputDevice outputDevice = new OutputDevice(outputStream);
    public void run() {
        inputDevice.readFruit(fruits, args);

        double totalWeight = Fruit.computeWeight(fruits);
        double totalSugar = Fruit.computeSugarContent(fruits);

        Fruit.prepareFruit(fruits);

        outputDevice.writeMessage("Total Weight of Fruits: " + totalWeight + " grams");
        outputDevice.writeMessage("Total Sugar Content: " + totalSugar + " grams");

        Map<Class<? extends Fruit>, Integer> fruitCounts = Fruit.countFruit(fruits);
        for (Map.Entry<Class<? extends Fruit>, Integer> entry : fruitCounts.entrySet()) {
            outputDevice.writeMessage(entry.getKey().getSimpleName() + ": " + entry.getValue());
        }
    }
}
