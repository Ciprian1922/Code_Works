import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 0000);

            // Scanner to read from console
            Scanner consoleScanner = new Scanner(System.in);

            // Scanner and PrintWriter to communicate with the server
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Get the username from the user
            System.out.print("Enter your username: ");
            String username = consoleScanner.nextLine();

            // Send the username to the server
            out.println(username);

            // Start a new thread to listen for incoming messages from the server
            Thread messageListener = new Thread(() -> {
                try {
                    while (in.hasNextLine()) {
                        String message = in.nextLine();
                        System.out.println(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            messageListener.start();

            // Allow the user to type messages and send them to the server
            System.out.println("Start typing messages. Press 'q' to quit.");
            while (true) {
                String message = consoleScanner.nextLine();
                out.println(message);

                if (message.equalsIgnoreCase("q")) {
                    // Close the socket and exit the program when the user types 'q'
                    socket.close();
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
