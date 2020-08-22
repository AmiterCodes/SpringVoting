package me.amitnave.voting.databaseObjects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Member {
    private int id;
    private String phone;
    private String name;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private boolean active;
    private double length;


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
            this.id = rs.getInt("id");
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
        String query = "SELECT * FROM voting.member where phone='" + phone + "' AND active = 1;";
        // create the java statement
        Statement st = conn.createStatement();
        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(query);
        // iterate through the java resultset
        boolean isMember = rs.next();
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
        String query = "SELECT * FROM voting.member where phone='" + phone + "';";
        // create the java statement
        Statement st = conn.createStatement();
        // execute the query, and get a java result set
        ResultSet rs = st.executeQuery(query);
        // iterate through the java result set
        while (rs.next()) {
            this.id = rs.getInt("id");
            this.phone = rs.getString("phone");
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
        String query = "SELECT * FROM voting.member where name='" + name + "';";
        // create the java statement
        Statement st = conn.createStatement();
        // execute the query, and get a java result set
        ResultSet rs = st.executeQuery(query);
        // iterate through the java result set
        while (rs.next()) {
            this.id = rs.getInt("id");
            phone = rs.getString("phone");
            this.name = rs.getString("name");
            this.length = rs.getDouble("length");
        }
        st.close();
    }

    public static List<Member> getMembers() throws SQLException {
        Connection con= DBHelper.getConnection();
        Statement st = con.createStatement();
        String sql = "SELECT * FROM member;";
        ResultSet rs = st.executeQuery(sql);
        List<Member> members = new LinkedList<>();
        while (rs.next()) {
            Member member = new Member(rs.getInt("id"), rs.getString("phone"), rs.getString("name"), rs.getDouble("length"), rs.getBoolean("active"));
            members.add(member);
        }
        st.close();
        return members;
    }

    public Member(int id, String phone, String name, double length, boolean active) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.length = length;
        this.active = active;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
