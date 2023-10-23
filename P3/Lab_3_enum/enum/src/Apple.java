public class Apple extends Fruit implements SeedRemovable {
    private boolean hasSeeds = true;

    public Apple(int sugar, int weight, String color, int water) {
        super(sugar, weight, color, water);
    }

    @Override
    public boolean hasSeeds() {
        return hasSeeds;
    }

    @Override
    public void removeSeeds() {
        hasSeeds = false;
    }
}