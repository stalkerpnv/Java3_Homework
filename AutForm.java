package homework2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AutForm extends JFrame {
    private JTextField logint;
    private JTextField passt;
    private JLabel message;

    private static Connection con = null;

    AutForm() {
        setTitle("Authorization form");
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(400, 400, 300, 250);

        JLabel loginl = new JLabel("Login");
        loginl.setBounds(70, 25, 75, 20);

        JLabel passl = new JLabel("Password");
        passl.setBounds(70, 50, 75, 20);

        logint = new JTextField();
        logint.setBounds(160, 25, 70, 20);

        passt = new JTextField();
        passt.setBounds(160, 50, 70, 20);

        JButton enter = new JButton("Enter");
        enter.setBounds(100, 80, 100, 30);

        JLabel message = new JLabel("");
        message.setBounds(90, 120, 150, 30);

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if (checklogin(logint.getText(),passt.getText())) {
                    setVisible(false);
                    new SimpleChat().setVisible(true);
                }
                else{
                    message.setText("Wrong login or password");
                }
            }
        });
        add(loginl);
        add(logint);
        add(passl);
        add(passt);
        add(enter);
        add(message);
        setVisible(true);
    }


    public static boolean checklogin(String logins, String passwords) {
        try {
            String URL = "jdbc:sqlite:C:\\sqlite\\users.db";
            con = DriverManager.getConnection(URL);
            Statement st = con.createStatement();
            String sqlQ = "SELECT * FROM users ";
            ResultSet rs = st.executeQuery(sqlQ);
            while (rs.next()){
                String login = rs.getString(2);
                String pass =rs.getString(3);
                if(login.equals(logins) && pass.equals(passwords)){
                    return true;
                }
            }

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (con!= null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return false;
    }
}
