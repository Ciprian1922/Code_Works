import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ChatServer chatServer;
    private PrintWriter out;
    private Scanner in;
    private String clientName;

    public ClientHandler(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
    }

    public String getClientName() {
        return clientName;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new Scanner(socket.getInputStream());

            // Receive the client name
            clientName = in.nextLine();
            System.out.println("Client connected: " + clientName);

            // Inform all clients about the new connection
            chatServer.broadcastMessage(clientName + " has joined the chat.", this);

            // Start listening for messages
            while (in.hasNextLine()) {
                String message = in.nextLine();
                chatServer.broadcastMessage(message, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Inform all clients about the disconnection
            chatServer.broadcastMessage(clientName + " has left the chat.", this);
            chatServer.removeClient(this);

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
