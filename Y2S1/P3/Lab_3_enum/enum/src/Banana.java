public class Banana extends Fruit implements Peelable {
    private boolean hasPeel = true;

    public Banana(int sugar, int weight, int water) {
        super(sugar, weight, Color.YELLOW.toString(), water);
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
        return false;
    }

    @Override
    public void removeSeeds() {

    }
}
