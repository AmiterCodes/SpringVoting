package me.amitnave.voting.databaseObjects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Member {
    private int id;
    private String phone;
    private String name;
    private boolean active;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return getId() == member.getId() &&
                active == member.active &&
                Objects.equals(getPhone(), member.getPhone()) &&
                Objects.equals(getName(), member.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPhone(), getName(), active);
    }

    public String getName() {
        return name;
    }

    public void insert() throws SQLException {
        String query = "insert into voting.member (phone, name) values ('" + phone + "','" + name + "');";
        id = DBHelper.insert(query);
    }

    public Member(String phone, String name) {
        this.phone = phone;
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }


    public Member(int id) throws SQLException {
        // create our mysql database connection
        Connection conn = DBHelper.getConnection();
        // our SQL SELECT query.
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT * FROM voting.member where id=" + id;
        // create the java statement
        Statement st = conn.createStatement();
        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(query);
        // iterate through the java resultset
        while (rs.next()) {
            this.id=rs.getInt("id");
            phone = rs.getString("phone");
            name = rs.getString("name");
            active = rs.getBoolean("active");
        }
        st.close();
    }
    public static boolean isMember(String phone) throws SQLException {
        Connection conn = DBHelper.getConnection();
        // our SQL SELECT query.
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT * FROM voting.member where phone='" + phone+"' AND active = 1;";
        // create the java statement
        Statement st = conn.createStatement();
        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(query);
        // iterate through the java resultset
        boolean isMember=rs.next();
        st.close();
        return isMember;
    }

    public int getId() {
        return id;
    }

    public Member(String phone) throws SQLException {
        // create our mysql database connection
        Connection conn = DBHelper.getConnection();
        // our SQL SELECT query.
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT * FROM voting.member where phone='" + phone+"';";
        // create the java statement
        Statement st = conn.createStatement();
        // execute the query, and get a java result set
        ResultSet rs = st.executeQuery(query);
        // iterate through the java result set
        while (rs.next()) {
            this.id=rs.getInt("id");
            phone = rs.getString("phone");
            active = rs.getBoolean("active");
            name = rs.getString("name");
        }
        st.close();
    }
    public Member(String name, boolean b) throws SQLException {
        // create our mysql database connection
        Connection conn = DBHelper.getConnection();
        // our SQL SELECT query.
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT * FROM voting.member where name='" + name+"';";
        // create the java statement
        Statement st = conn.createStatement();
        // execute the query, and get a java result set
        ResultSet rs = st.executeQuery(query);
        // iterate through the java result set
        while (rs.next()) {
            this.id=rs.getInt("id");
            phone = rs.getString("phone");
            this.name = rs.getString("name");

        }
        st.close();
    }


}
