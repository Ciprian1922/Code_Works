public class Application {
    public static void prepareFruit(Fruit[] fruits) {
        for (Fruit fruit : fruits) {
            if (fruit instanceof Peelable) {
                Peelable peelableFruit = (Peelable) fruit;
                if (peelableFruit.hasPeel()) {
                    peelableFruit.peelOff();
                }
            }
            if (fruit instanceof SeedRemovable) {
                SeedRemovable seedRemovableFruit = (SeedRemovable) fruit;
                if (seedRemovableFruit.hasSeeds()) {
                    seedRemovableFruit.removeSeeds();
                }
            }
        }
    }

    public static double computeWeight(Fruit[] fruits) {
        double totalWeight = 0;

        for (Fruit fruit : fruits) {
            // Parse the weight from the String to a double
            double weight = Double.parseDouble(fruit.getWeight());
            totalWeight += weight;
        }

        return totalWeight;
    }

    public static double computeSugarContent(Fruit[] fruits) {
        double totalSugarContent = 0;

        for (Fruit fruit : fruits) {
            // Parse the sugar content from the String to a double
            double sugarContent = Double.parseDouble(fruit.getSugar());
            totalSugarContent += sugarContent;
        }

        return totalSugarContent;
    }
}
