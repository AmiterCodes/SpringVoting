package me.amitnave.voting.databaseObjects;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Poll {

    private int id;
    private String description;
    private Timestamp time;
    private String creator;
    private String groupChat;

    public Poll(int id) throws SQLException {
        Connection connection = DBHelper.getConnection();
        Statement st = connection.createStatement();
        String query = "SELECT * FROM poll WHERE id =" + id + ";";
        ResultSet rs = st.executeQuery(query);
        rs.next();

        this.id= id;
        description = rs.getString("description");
        time = rs.getTimestamp("time");
        creator = rs.getString("creator");
        groupChat = rs.getString("gc");
    }

    public void vote() {

    }

    public Poll(int id, String description, Timestamp time, String creator, String groupChat) {
        this.id = id;
        this.description = description;
        this.time = time;
        this.creator = creator;
        this.groupChat = groupChat;
    }

    public static List<Option> OptionsByPoll(int poll) throws SQLException {
        Connection connection = DBHelper.getConnection();
        Statement st = connection.createStatement();
        String query = "SELECT * FROM poll_options WHERE poll_id =" + poll + ";";
        ResultSet rs = st.executeQuery(query);
        List<Option> options = new LinkedList<>();
        while (rs.next()) {
            options.add(new Option(rs));
        }
        return options;
    }

    public static List<PollAnswer> AnswersByPoll(int poll) throws SQLException {
        Connection connection = DBHelper.getConnection();
        Statement st = connection.createStatement();
        String query = "SELECT * FROM poll_answers WHERE poll_id =" + poll + ";";
        ResultSet rs = st.executeQuery(query);
        List<PollAnswer> answers = new LinkedList<>();
        while (rs.next()) {
            answers.add(new PollAnswer(rs.getInt("id"), rs.getString("phone"), rs.getInt("answer"), rs.getInt("poll_id")));
        }
        return answers;
    }

    public static class Option {
        private int id;
        private int option;
        private int pollId;
        private String optionDescription;

        public static Option OptionByAnswer(PollAnswer answer) throws SQLException {
            Connection connection = DBHelper.getConnection();
            Statement st = connection.createStatement();
            String query = "SELECT * FROM poll_options WHERE poll_id =" + answer.getPollId() + " AND option_id = "+ answer.getAnswer()+";";
            ResultSet rs = st.executeQuery(query);

            return new Option(rs);
        }

        public Option(ResultSet rs) throws SQLException {
            this(rs.getInt("id"), rs.getInt("option"), rs.getInt("poll_id"), rs.getString("option_desc"));
        }

        public Option(int id, int option, int pollId, String optionDescription) {
            this.id = id;
            this.option = option;
            this.pollId = pollId;
            this.optionDescription = optionDescription;
        }

        public String getOptionDescription() {
            return optionDescription;
        }

        public void setOptionDescription(String optionDescription) {
            this.optionDescription = optionDescription;
        }

        public int getPollId() {
            return pollId;
        }

        public void setPollId(int pollId) {
            this.pollId = pollId;
        }

        public int getOption() {
            return option;
        }

        public void setOption(int option) {
            this.option = option;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getGroupChat() {
        return groupChat;
    }

    public void setGroupChat(String groupChat) {
        this.groupChat = groupChat;
    }
}
