
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.Scanner;

public class ChatForm {
    private JFrame windowChat;

    // Elements for Authorization
    private JTextField logint;
    private JTextField passt;
    private JLabel loginl;
    private JLabel passl;
    private JButton enter;

    // Elements for Chat
    private JTextArea chatArea;
    private JTextField message;
    private JButton send;


    private static String loginStr;
    private boolean isAuthorized;

    private Socket socket;
    private final String IP_ADDRESS = "127.0.0.1";
    private final int PORT = 5000;
    private DataOutputStream out;
    private DataInputStream in;

    ChatForm() {

        windowChat = new JFrame("SimpleChat");
        windowChat.setResizable(false);
        windowChat.setLayout(null);
        windowChat.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        windowChat.setBounds(400, 400, 400, 400);

        loginl = new JLabel("Login");
        loginl.setBounds(100, 25, 75, 20);

        passl = new JLabel("Password");
        passl.setBounds(100, 50, 75, 20);

        logint = new JTextField();
        logint.setBounds(200, 25, 70, 20);

        passt = new JTextField();
        passt.setBounds(200, 50, 70, 20);

        enter = new JButton("Enter");
        enter.setBounds(150, 80, 100, 30);

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryToAuth();
            }
        });

        chatArea = new JTextArea("");
        chatArea.setBounds(0,0,400,320);

        message = new JTextField();
        message.setBounds(20,330,250,25);

        message.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });
        send  = new JButton("Send");
        send.setBounds(280,330,100,25);

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });


        windowChat.add(loginl);
        windowChat.add(logint);
        windowChat.add(passl);
        windowChat.add(passt);
        windowChat.add(enter);

        windowChat.add(chatArea);
        windowChat.add(message);
        windowChat.add(send);

        setAuthorized(false);
        windowChat.setVisible(true);
    }

    public void setAuthorized(boolean isAuthorized){
        this.isAuthorized = isAuthorized;
        if (!isAuthorized) {
            logint.setVisible(true);
            passt.setVisible(true);
            loginl.setVisible(true);
            passl.setVisible(true);
            enter.setVisible(true);

            chatArea.setVisible(false);
            message.setVisible(false);
            send.setVisible(false);

        } else {
            logint.setVisible(false);
            passt.setVisible(false);
            loginl.setVisible(false);
            passl.setVisible(false);
            enter.setVisible(false);

            chatArea.setVisible(true);
            message.setVisible(true);
            send.setVisible(true);
        }
    }

    public void connect(){
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/authok")) {
                                setAuthorized(true);
                                break;
                            }
                            chatArea.append(str + "\n");
                        }

                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/end")) {
                                break;
                            }

                            chatArea.append(str + "\n");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setAuthorized(false);
                    }
                }
            }).start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendMsg() {
        try {
            out.writeUTF(message.getText());
            message.setText("");
            message.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth() {
        if((socket == null)||(socket.isClosed())){
            connect();
        }

        try {
            out.writeUTF("/auth " + logint.getText() + " " + passt.getText());
            logint.setText("");
            passt.setText("");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}