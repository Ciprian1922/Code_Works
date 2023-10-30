public class Main {
    public static void main(String[] args) {
        InputDevice inputDevice = new InputDevice();
        OutputDevice outputDevice = new OutputDevice();
        Application app = new Application(inputDevice, outputDevice, args);
        app.run();
    }
}
