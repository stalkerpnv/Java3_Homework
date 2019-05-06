package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    public static File file;
    public static FileWriter fw;
    public static BufferedReader br;
    String nickname;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/auth")) {
                            String[] token = str.split(" ");
                            String newNick = ForAuthorization.getNickname(
                                    token[1], token[2]);

                            if (newNick != null) {
                                sendMsg("/authok");
                                nickname = newNick;
                                server.subscribe(this);

                                // Create file of history

                                String userhistory = "history_" + nickname;
                                file = new File(userhistory);
                                if(!file.exists()) file.createNewFile();
                                printHistory(file);
                                fw = new FileWriter(file);

                                break;
                            } else {
                                sendMsg("Неверный логин/пароль");
                            }
                        }
                    }
                    while (true) {
                        String str = in.readUTF();

                        // Writing the history
                        fw.append(nickname + ": " + str + "\n");


                        if (str.equals("/end")) {
                            out.writeUTF("/end");
                            break;
                        }
                        server.broadcastMsg(nickname + ": " + str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printHistory(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file), 1024);
            int lineCount = 0;
            String s;
            while ((s = br.readLine()) != null) {
                out.writeUTF(s);
            }
        } catch (EOFException eof) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
