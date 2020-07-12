package me.amitnave.voting.databaseObjects;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.amitnave.voting.WAInterface.general.Settings.TIME_TO_PASS;

public class Law {
    private int id;
    private String description;
    private int creator;
    private int status;
    private String date;
    private boolean anonymousVoting;
    private boolean anonymousCreator;
    public static final int passed = 0;
    public static final int failed = 1;
    public static final int inProcess = 2;
    public static final int invalidated = 3;
    public static final int canceled = 4;
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Law(String description, int creator, int status, String date, boolean anonymousVoting,
               boolean anonymousCreator) {
        this.description = description;
        this.creator = creator;
        this.status = status;
        this.date = date;
        this.anonymousVoting = anonymousVoting;
        this.anonymousCreator = anonymousCreator;
    }

    public void insert() throws SQLException {
        String query =
                "insert into voting.law (description, status, `time`, creator, `anonymousVoting`, `anonymousCreator`) values ('" +
                        description + "'," + status + ",'" + date + "'," + creator + "," +
                        anonymousVoting + "," + anonymousCreator + ");";
        id = DBHelper.insert(query);

    }

    public String getDescription() {
        return description;
    }

    public int getCreator() {
        return creator;
    }

    public String getDate() {
        return date;
    }

    public boolean isAnonymousVoting() {
        return anonymousVoting;
    }

    public boolean isAnonymousCreator() {
        return anonymousCreator;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public static String now() {
        Date date = new Date(System.currentTimeMillis());
        return (dateFormat.format(date));
    }

    public static String time(Date date) {
        return (dateFormat.format(date));

    }

    public static void main(String[] args) {
        System.out.println(now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Law law = (Law) o;
        return id == law.id &&
                creator == law.creator &&
                status == law.status &&
                anonymousVoting == law.anonymousVoting &&
                anonymousCreator == law.anonymousCreator &&
                Objects.equals(description, law.description) &&
                Objects.equals(date, law.date);
    }

    public void invalidate() throws SQLException {
        status = invalidated;
        DBHelper.update("update voting.law set status=" + invalidated + " where id=" + id + ";");
    }


    public Law(int id) throws SQLException, ParseException {
        String sql = "SELECT * FROM voting.law where id=";
        String query = sql + id + ";";
        Statement st;
        Connection conn = DBHelper.getConnection();

        st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery(query);
        while (rs.next()) {
            this.id = rs.getInt("id");
            description = rs.getString("description");
            creator = rs.getInt("creator");
            status = rs.getInt("status");
            date = rs.getString("time");
            anonymousCreator = rs.getBoolean("anonymousCreator");
            anonymousVoting = rs.getBoolean("anonymousVoting");
        }
        st.close();
    }

    public String minutesLeft() throws ParseException {
        java.util.Date date = dateFormat.parse(this.date);
        java.util.Date now = dateFormat.parse(now());
        Long timeLeft=TIME_TO_PASS-(now.getTime()-date.getTime());
        return ((timeLeft/(60*1000))%60)+"";
    }
    public String hoursLeft() throws ParseException {
        java.util.Date date = dateFormat.parse(this.date);
        java.util.Date now = dateFormat.parse(now());
        Long timeLeft=TIME_TO_PASS-(now.getTime()-date.getTime());
        return (timeLeft/(60*60*1000))+"";
    }

    private static boolean oneDayPassed(String time) throws ParseException {
        java.util.Date date = dateFormat.parse(time);
        java.util.Date oneDayAgo = dateFormat.parse(now());
        oneDayAgo.setTime(oneDayAgo.getTime() - TIME_TO_PASS);
        return date.before(oneDayAgo);
    }

    public boolean updateStatus() throws SQLException, ParseException {
        if (!oneDayPassed(date) || status == failed || status == passed) {
            return false;
        }

        String sql = "SELECT * FROM voting.vote where law=" + id + ";";
        Statement st;
        Connection conn = DBHelper.getConnection();
        st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int forCounter = 0;
        int againstCounter = 0;
        while (rs.next()) {

            if (rs.getInt("vote") == Vote.FOR) {
                forCounter++;
            } else if (rs.getInt("vote") == Vote.AGAINST) {
                againstCounter++;
            }

        }
        if (forCounter > againstCounter) {
            status = passed;
            DBHelper.update("update voting.law set status="+passed+" where id="+id+";");
        }
        else{
            status = failed;
            DBHelper.update("update voting.law set status="+failed+" where id="+id+";");

        }
        st.close();
        return true;
    }
    public void cancel() throws SQLException {
        status = canceled;
        DBHelper.update("update voting.law set status="+canceled+" where id="+id+";");
    }
    public List<Vote> getVotes() throws SQLException {
        List<Vote> votes=new ArrayList<>();
        String sql = "SELECT * FROM voting.vote where law=" + id + ";";
        Statement st;
        Connection conn = DBHelper.getConnection();
        st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            votes.add(new Vote(rs.getInt("law"),rs.getInt("member"), rs.getInt("vote")));
        }
        return votes;
    }
    public static List<Law> getLawsByStatus(int status) throws SQLException, ParseException {
        List<Law> laws=new ArrayList<>();
        String sql = "SELECT * FROM voting.law where status=" + status + ";";
        Statement st;
        Connection conn = DBHelper.getConnection();
        st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {

            laws.add(new Law(rs.getInt("id")));
        }
        return laws;
    }

}
