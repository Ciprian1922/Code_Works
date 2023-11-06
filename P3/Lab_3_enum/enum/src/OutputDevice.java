import java.io.IOException;
import java.io.OutputStream;

public class OutputDevice {
    private OutputStream outputStream;

    public OutputDevice(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void writeMessage(String message) {
        try {
            outputStream.write(message.getBytes());
            outputStream.write(System.lineSeparator().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Overload method to handle integer messages
    public void writeMessage(int message) {
        writeMessage(String.valueOf(message));
    }
}
