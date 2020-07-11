package me.amitnave.voting.databaseObjects;

import com.mysql.cj.jdbc.StatementImpl;

import java.sql.*;

public class DBHelper {


    private static final String localhost = "jdbc:mysql://localhost:3307/voting";
    private static final String root = "root";
    private static final String password = "12345";

    public static void main() throws SQLException, ClassNotFoundException {


        // create a mysql database connection

        ///Class.forName("com.mysql.cj.jdbc.Driver");

        final String localhost = DBHelper.localhost;
        final String root = DBHelper.root;
        String password = DBHelper.password;
        String sql = "update voting.member set id=1 where id=4;";
        update(sql);
        return;
    }

    public static void update(String sql) throws SQLException {
        Connection conn = getConnection();
        StatementImpl statement = (StatementImpl) conn.createStatement();
        statement.execute(sql);
        conn.close();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(localhost, root, password);
    }

    public static int insert(String sql) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        statement.execute();
        ResultSet rs=statement.getGeneratedKeys();
        rs.next();
        int id=rs.getInt(1);
        conn.close();
        return id;
    }

    public static ResultSet selectByID(String sql, Statement st) throws SQLException {
        Connection conn = DBHelper.getConnection();

        st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery(sql);

        return rs;
    }
    public static int index() throws SQLException {
        String sql="SELECT LAST_INSERT_ID();";
        Connection conn = DBHelper.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery(sql);
        rs.next();
        return rs.getInt("LAST_INSERT_ID()");
    }

}