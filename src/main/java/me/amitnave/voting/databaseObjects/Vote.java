package me.amitnave.voting.databaseObjects;

import me.amitnave.voting.WAInterface.Message.MessageToSend;

import javax.management.Query;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Vote {
    public static final int FOR = 0;
    public static final int AGAINST = 1;
    public static final int NEUTRAL = 2;
    private int lawID;
    private String info;
    private int memberID;
    private int vote;

    public static boolean AlreadyVoted(int memberID, int lawID) throws SQLException {
        Connection conn = DBHelper.getConnection();
        // our SQL SELECT query.
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT * FROM voting.vote WHERE member = " + memberID + " AND law = " + lawID + ";";
        // create the java statement
        Statement st = conn.createStatement();
        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(query);
        // iterate through the java resultset
        boolean isMember = rs.next();
        st.close();
        return isMember;
    }

    public static List<Vote> votesForLawsByStatus(int status) throws SQLException {
        Connection connection = DBHelper.getConnection();
        Statement st = connection.createStatement();
        String query = "SELECT * FROM vote INNER JOIN law ON vote.law = law.id WHERE law.status =" + status + ";";
        ResultSet rs = st.executeQuery(query);
        List<Vote> votes = new LinkedList<>();
        while (rs.next()) {
            votes.add(new Vote(rs.getInt("law"), rs.getInt("member"), rs.getInt("id"), rs.getString("info")));
        }
        return votes;
    }

    public Vote(int lawID, int memberID, int vote, String info) {
        this.lawID = lawID;
        this.memberID = memberID;
        this.vote = vote;
        this.info = info;
    }

    public void insert() throws SQLException {
        String sql = "insert into voting.vote (law, member,vote, info) values (" + lawID + "," + memberID + "," + vote + ",'"+ info+ "');";
        if (Vote.AlreadyVoted(memberID, lawID)) {
            sql = "UPDATE voting.vote SET vote = " + vote + ", info = '"+info+"' WHERE member = " + memberID + " AND law = " + lawID + ";";
            DBHelper.update(sql);
            return;
        }
        DBHelper.insert(sql);
    }

    public int getVote() {
        return vote;
    }

    public Vote(int id) throws SQLException {
        String query = "select * from voting.vote where id=" + id + ";";
        Statement st;
        Connection conn = DBHelper.getConnection();

        st = conn.createStatement();

        ResultSet rs = st.executeQuery(query);
        rs.next();
        lawID = rs.getInt("law");
        memberID = rs.getInt("member");
        vote = rs.getInt("vote");

        st.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vote vote1 = (Vote) o;
        return lawID == vote1.lawID &&
                memberID == vote1.memberID &&
                vote == vote1.vote;
    }


    public int getLawID() {
        return lawID;
    }

    public int getMemberID() {
        return memberID;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
