package me.amitnave.voting.WAInterface.Command.VotingCommands.Law;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.DBHelper;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Vote;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GetLawStatus implements VotingCommand {
    private Law law;
    private String askerNum;

    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }

    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        Connection conn = DBHelper.getConnection();
        // our SQL SELECT query.
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT voting.vote.vote, voting.member.name FROM voting.vote INNER JOIN voting.member ON voting.vote.law = " + law.getId() + " AND voting.member.id=voting.vote.member;";
        // create the java statement
        Statement st = conn.createStatement();
        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(query);
        // iterate through the java resultset
        List<String> forVotes = new ArrayList<>();
        List<String> againstVotes = new ArrayList<>();
        List<String> neutralVotes = new ArrayList<>();
        while (rs.next()) {
            if (rs.getInt("vote") == Vote.FOR)
                forVotes.add(rs.getString("name"));
            if (rs.getInt("vote") == Vote.AGAINST)
                againstVotes.add(rs.getString("name"));
            if (rs.getInt("vote") == Vote.NEUTRAL)
                neutralVotes.add(rs.getString("name"));
        }
        st.close();
        String status;
        if (law.getStatus() == Law.passed) {
            status = "עבר";
        } else if (law.getStatus() == Law.failed) {
            status = "לא עבר";
        } else if (law.getStatus() == Law.inProcess) {
            status = "בתהליך הצבעה";
        } else if (law.getStatus() == Law.invalidated) {
            status = "נפסל על ידי הנשיא";
        } else {
            status = "בוטל על ידי ההוגה";
        }
        MessageStructure structure = new MessageStructure();
        structure.addToLastRow("מצב החוק:");
        structure.addToLastRow(status);

        if (law.getStatus() == Law.inProcess) {
            structure.addRow("זמן שנותר: ");
            structure.addToLastRow(law.hoursLeft());
            structure.addToLastRow(" שעות ");
            structure.addToLastRow(law.minutesLeft());
            structure.addToLastRow(" דקות.");
        }
        if (!law.isAnonymousVoting()) {
            structure.addRow("בעד");
            for (String member : forVotes
            ) {
                structure.addRow("      -");
                structure.addToLastRow(member);
            }
            structure.addRow("נגד");
            for (String member : againstVotes
            ) {
                structure.addRow("      -");
                structure.addToLastRow(member);
            }
            structure.addRow("נמנע");
            for (String member : neutralVotes
            ) {
                structure.addRow("      -");
                structure.addToLastRow(member);

            }
        }

        structure.addRow(law.getDescription());
        String res = structure.getString();
        return (List<MessageToSend>) new MessageToSend(res, askerNum);
    }


    public GetLawStatus(Law law, String askerNum) {
        this.law = law;
        this.askerNum = askerNum;
    }


}
