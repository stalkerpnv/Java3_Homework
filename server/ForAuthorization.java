package server;
import java.sql.*;

public class ForAuthorization {
    private static Connection conn;
    private static Statement stat;

    public static void connectToDB() throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\sqlite\\users.db");
            System.out.println("SQL connection is created");
            stat = conn.createStatement();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static void disconnect() throws SQLException {
        conn.close();
    }

    public static String getNickname(String login, String password){
        String sql = "SELECT * FROM users";
        try {
            ResultSet rs = stat.executeQuery(sql);

            while (rs.next()) {
                String logint = rs.getString(2);
                String passt = rs.getString(3);
                if (logint.equals(login) && passt.equals(password)) {
                    return rs.getString(4);
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
