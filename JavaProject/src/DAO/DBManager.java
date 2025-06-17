package DAO;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {

    public static String url        = "jdbc:mysql://localhost:3306/";
    public static String dbName     = "esame";
    public static String driver     = "com.mysql.cj.jdbc.Driver";
    public static String userName   = "root";
    public static String password   = "admin01!";

    private static Connection conn;
    private static PreparedStatement statement;

    // Carica il driver solo quando serve
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (conn == null || conn.isClosed()) {
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
        }
        return conn;
    }

    // Chiude la connessione statica
    public static void closeConnection() throws ClassNotFoundException, SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    // Restituisce lo statement (la connessione rimane aperta fino a close(ps))
    public static PreparedStatement getPreparedStatement(String query)
            throws ClassNotFoundException, SQLException {
        conn=getConnection();
         statement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        return statement;
    }

    // Esegue SELECT, ma lascia connessione aperta: caricaDaDB e simili devono fare rs.close() + closeConnection()
    public static ResultSet selectQuery(PreparedStatement ps)
            throws ClassNotFoundException, SQLException {

        System.out.println(ps);
        return ps.executeQuery();
    }

    // Esegue INSERT/UPDATE/DELETE e chiude sempre statement+connection
    public static int updateQuery(PreparedStatement ps)
            throws ClassNotFoundException, SQLException {

        try {
            System.out.println(ps);
            return ps.executeUpdate();
        } finally {
            close(ps);
        }
    }

    // Esegue INSERT, legge chiave generata e chiude statement+connection
    public static int updateQueryReturnGeneratedKey(PreparedStatement ps)
            throws ClassNotFoundException, SQLException {

        try {
            System.out.println(ps);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } finally {
            close(ps);
        }
    }

    // Chiude statement e la connessione associata
   public static void close(PreparedStatement ps) {
        try {
            if (ps != null) ps.close();
        } catch (SQLException ignored) {}

        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException ignored) {
            ignored.printStackTrace();
        }
    }
}