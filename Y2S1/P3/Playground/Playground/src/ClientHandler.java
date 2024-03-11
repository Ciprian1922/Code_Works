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

            //receive the client name
            clientName = in.nextLine();
            System.out.println("Client connected: " + clientName);

            //inform the client about its info
            chatServer.sendClientInfo(this);

            //inform all clients about the new connection
            chatServer.broadcastMessage(clientName + " has joined the chat.", this);

            //start listening for messages
            while (in.hasNextLine()) {
                String message = in.nextLine();
                String clientInfo = "IP: " + socket.getInetAddress().getHostAddress() +
                        ", Source Port: " + socket.getLocalPort() +
                        ", Destination Port: " + socket.getPort() +
                        ", Name: " + clientName;
                System.out.println("Message from " + clientInfo + ": " + message);
                chatServer.broadcastMessage(message, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //inform all clients about the disconnection
            chatServer.broadcastMessage(clientName + " has left the chat.", this);
            chatServer.removeClient(this);

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getClientSocket() {
        return socket;
    }
}
