package me.amitnave.voting.databaseObjects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

public class Appeal {
    private int id;
    private int law;
    private int appealer;
    private String reason;
    private int status;
    private String comment;
    public static final int approved=0;
    public static final int rejected=1;
    public static final int inProcess=2;

    public Appeal(int law, int appealer, String reason) {
        this.law = law;
        this.appealer = appealer;
        this.reason = reason;
    }
    public void insert() throws SQLException {
        String query = "insert into voting.appeal (law, appealer, reason, status) values (" +law + "," + appealer + ",'"+reason+"',"+inProcess+");";
        id = DBHelper.insert(query);
    }

    public int getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public int getId() {
        return id;
    }

    public int getLaw() {
        return law;
    }

    public int getAppealer() {
        return appealer;
    }

    public String getReason() {
        return reason;
    }

    public void presidentDecision(boolean approved, String explanation) throws SQLException, ParseException {
        String sql="update voting.appeal set status="+fromBoolean(approved)+", comment='"+explanation+"' where id="+id+";";
        DBHelper.update(sql);
        if (approved){
            status= Appeal.approved;
            (new Law(law)).cancel();
        }
        else status=rejected;
        comment=explanation;
    }
    private static int fromBoolean(boolean approved){
        if (approved) return Appeal.approved; return rejected;
    }
    public Appeal(int id) throws SQLException {
        // create our mysql database connection
        Connection conn = DBHelper.getConnection();
        // our SQL SELECT query.
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT * FROM voting.appeal where id=" + id;
        // create the java statement
        Statement st = conn.createStatement();
        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(query);
        // iterate through the java resultset
        while (rs.next()) {
            this.id=rs.getInt("id");
            law = rs.getInt("law");
            appealer = rs.getInt("appealer");
            reason=rs.getString("reason");
            status=rs.getInt("status");
            comment=rs.getString("comment");
        }
        st.close();
    }
}
