import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int PORT = 8888;
    private List<ClientHandler> clients = new ArrayList<>();
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        new ChatServer().startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                executor.submit(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    public void sendClientInfo(ClientHandler client) {
        String clientInfo = "IP: " + client.getClientSocket().getInetAddress().getHostAddress() +
                ", Source Port: " + client.getClientSocket().getLocalPort() +
                ", Destination Port: " + client.getClientSocket().getPort() +
                ", Name: " + client.getClientName();
        client.sendMessage(clientInfo);
    }

    public void broadcastMessage(String message, ClientHandler sender) {
        String senderInfo = "IP: " + sender.getClientSocket().getInetAddress().getHostAddress() +
                ", Source Port: " + sender.getClientSocket().getLocalPort() +
                ", Destination Port: " + sender.getClientSocket().getPort() +
                ", Name: " + sender.getClientName();
        String fullMessage = senderInfo + ", Message: " + message;

        // Send the full message to the sender
        sender.sendMessage(fullMessage);
    }

//    public void broadcastMessage(String message, ClientHandler sender) {
//        String senderInfo = "IP: " + sender.getClientSocket().getInetAddress().getHostAddress() +
//                ", Source Port: " + sender.getClientSocket().getLocalPort() +
//                ", Destination Port: " + sender.getClientSocket().getPort() +
//                ", Name: " + sender.getClientName();
//        String fullMessage = senderInfo + ", Message: " + message;
//
//        // Send the full message to the sender
//        sender.sendMessage(fullMessage);
//
//        // Send only the message to the other clients
//        for (ClientHandler client : clients) {
//            if (client != sender) {
//                client.sendMessage(message);
//            }
//        }
//    }
    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}
