public class Main {
    public static void main(String[] args) {
        // Create an array of Fruit objects
        Fruit[] fruits = new Fruit[4];
        fruits[0] = new Banana(20, 100, 80);
        fruits[1] = new Apple(15, 150, Fruit.Color.RED.toString(), 90);
        fruits[2] = new Mango(20, 200, Fruit.Color.YELLOW.toString(), 85);
        fruits[3] = new Mango(69, 195, Fruit.Color.GREEN.toString(), 80);

        // Prepare the fruits by checking if they are Peelable or SeedRemovable
        Application.prepareFruit(fruits);

        // Compute total weight and total sugar content
        double totalWeight = Application.computeWeight(fruits);
        double totalSugar = Application.computeSugarContent(fruits);

        // Print the results
        System.out.println("Total Weight of Fruits: " + totalWeight + " grams");
        System.out.println("Total Sugar Content: " + totalSugar + " grams");
    }
}
