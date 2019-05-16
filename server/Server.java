package server;

import java.io.*;
import java.sql.SQLException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Vector;
import java.util.logging.*;


public class Server {
    private static final Logger logger = Logger.getLogger(StartServer.class.getName());
    private Vector<ClientHandler> clients;
    public Server() throws SQLException {
        try {
            Handler h = new FileHandler("mylog.log");
            h.setFormatter(new SimpleFormatter());
            logger.addHandler(h);

            ForAuthorization.connectToDB();
            clients = new Vector<>();
            Socket socket = null;
            ServerSocket server = null;

            try {
                server = new ServerSocket(5000);
                logger.log(Level.INFO,"Server is running");
                while (true) {
                    socket = server.accept();
                    logger.log(Level.INFO,"Client is connected");
                    new ClientHandler(this, socket);
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE,"Server is not created",e);
            } finally {
                try {
                    socket.close();
                    logger.log(Level.INFO,"Socket is closed");
                } catch (IOException e) {
                    logger.log(Level.SEVERE,"Can't close the socket",e);
                }
                try {
                    server.close();
                    logger.log(Level.INFO,"Server is closed");
                } catch (IOException e) {
                    logger.log(Level.SEVERE,"Can't close the server",e);
                }
                ForAuthorization.disconnect();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void broadcastMsg(String msg) {
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
            logger.log(Level.SEVERE,"Client sent the message " + msg);
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}

