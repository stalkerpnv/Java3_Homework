package server;

import java.io.*;
import java.sql.SQLException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Vector;

public class Server {
    private Vector<ClientHandler> clients;

    public Server() throws SQLException {

        ForAuthorization.connectToDB();
        clients = new Vector<>();
        Socket socket = null;
        ServerSocket server = null;

        try {
            server = new ServerSocket(5000);
            System.out.println("Server is running");
            while (true) {
                socket = server.accept();
                System.out.println("Client is connected");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ForAuthorization.disconnect();
        }
    }

    public void broadcastMsg(String msg) {
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}

