public class Mango extends Fruit implements Peelable, SeedRemovable {
    private boolean hasPeel = true;
    private boolean hasSeeds = true;

    public Mango(int sugar, int weight, String color, int water) {
        super(sugar, weight, color, water);
    }

    @Override
    public boolean hasPeel() {
        return hasPeel;
    }

    @Override
    public void peelOff() {
        hasPeel = false;
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