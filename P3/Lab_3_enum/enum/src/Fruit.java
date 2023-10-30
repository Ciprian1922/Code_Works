import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Fruit {
    private String sugar;
    private String weight;
    private String color;
    private String water;

    public Fruit(int sugar, int weight, String color, int water) {
        this.sugar = String.valueOf(sugar);
        this.weight = String.valueOf(weight);
        this.color = color;
        this.water = String.valueOf(water);
    }

    public static Map<Class<? extends Fruit>, Integer> countFruit(List<Fruit> fruits) {
        Map<Class<? extends Fruit>, Integer> countMap = new HashMap<>();

        for (Fruit fruit : fruits) {
            Class<? extends Fruit> fruitClass = fruit.getClass();
            countMap.put(fruitClass, countMap.getOrDefault(fruitClass, 0) + 1);
        }

        return countMap;
    }

    public String getSugar() {
        return sugar;
    }

    public String getWeight() {
        return weight;
    }

    public String getColor() {
        return color;
    }

    public String getWater() {
        return water;
    }

    public abstract boolean hasPeel();

    public abstract void peelOff();

    public abstract boolean hasSeeds();

    public abstract void removeSeeds();

    public static double computeWeight(List<Fruit> fruits) {
        double totalWeight = 0;
        for (Fruit fruit : fruits) {
            double weight = Double.parseDouble(fruit.getWeight());
            totalWeight += weight;
        }
        return totalWeight;
    }

    public static double computeSugarContent(List<Fruit> fruits) {
        double totalSugar = 0;
        for (Fruit fruit : fruits) {
            double sugar = Double.parseDouble(fruit.getSugar());
            totalSugar += sugar;
        }
        return totalSugar;
    }
    public static void prepareFruit(List<Fruit> fruits) {
        for (Fruit fruit : fruits) {
            if (fruit.hasPeel()) {
                fruit.peelOff();
            }
            if (fruit.hasSeeds()) {
                fruit.removeSeeds();
            }
        }
    }
}
