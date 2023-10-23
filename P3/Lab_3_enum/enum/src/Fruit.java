public abstract class Fruit {
    public static Color Color;
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

    public String getWeight() {
        return weight;
    }

    public String getSugar() {
        return sugar;
    }

    // Getters for sugar, weight, color, and water

}
