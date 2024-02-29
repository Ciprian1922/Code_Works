public class Main {
    public static void main(String[] args) {
        InputDevice inputdevice = new InputDevice();
        OutputDevice outputDevice = new OutputDevice();
        Application application = new Application(inputdevice, outputDevice);
        application.run();
    }
}