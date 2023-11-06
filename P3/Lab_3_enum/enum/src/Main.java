import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        InputDevice inputDevice = new InputDevice(new FileInputStream("src/sample.txt"));
        //OutputDevice outputDevice = new OutputDevice(System.out);
        OutputDevice outputDevice = new OutputDevice(new FileOutputStream("test.txt"));

        //outputDevice.writeMessage(inputDevice.readLine()); //This reads from the source
        Application app = new Application(inputDevice, outputDevice, args);
        app.run();

//        // Test the InputDevice with FileInputStream
//
//        app = new Application(inputDevice, outputDevice, args);
//        app.run();
    }
}