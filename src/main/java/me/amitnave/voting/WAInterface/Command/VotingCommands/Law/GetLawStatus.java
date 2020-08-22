package me.amitnave.voting.WAInterface.Command.VotingCommands.Law;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.DBHelper;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;
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

    public String voteDisplay(ResultSet rs) throws SQLException {
        String info = rs.getString("info").strip();
        if(info.equals("")) return rs.getString("name");
        else
        return "" + rs.getString("name") + ": '" +  info + "'";
}

    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        Connection conn = DBHelper.getConnection();
        // our SQL SELECT query.
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT voting.vote.vote, voting.member.name, voting.vote.info FROM voting.vote INNER JOIN voting.member ON voting.vote.law = " + law.getId() + " AND voting.member.id=voting.vote.member;";
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
                forVotes.add(voteDisplay(rs));
            if (rs.getInt("vote") == Vote.AGAINST)
                againstVotes.add(voteDisplay(rs));
            if (rs.getInt("vote") == Vote.NEUTRAL)
                neutralVotes.add(voteDisplay(rs));
        }

        st.close();
        String prediction;

        String status;
        status = status(law);

        if(forVotes.size() > againstVotes.size()) prediction = "*לעבור*";
        else prediction = "*לא לעבור*";

        MessageStructure structure = new MessageStructure();
        structure.addToLastRow("*");
        structure.addToLastRow("הצעת חוק ");
        structure.addToLastRow("#" + law.getId());
        structure.addToLastRow("*");

        structure.addRow("הוגה החוק: ");
        if(law.isAnonymousCreator() || law.isHasFakeName())
            structure.addToLastRow(law.anonName());
        else {
            structure.addToLastRow(new Member(law.getCreator()).getName());
        }
        structure.addRow("מצב החוק:");
        structure.addToLastRow(" ");
        structure.addToLastRow("*" + status + "*");

        if (law.getStatus() == Law.inProcess) {
            structure.addRow("זמן שנותר: ");
            structure.addToLastRow(law.hoursLeft());
            structure.addToLastRow(" שעות ");
            structure.addToLastRow(law.minutesLeft());
            structure.addToLastRow(" דקות.");
        }

        if(!law.isAnonymousVoting() && law.getStatus() == Law.inProcess) {
            structure.addRow("החוק במצבו הנוכחי צפוי " + prediction);
        }

        if (!law.isAnonymousVoting()) {
            structure.addRow("*בעד*");
            structure.addToLastRow(" - ");
            structure.addToLastRow("" + forVotes.size());
            for (String member : forVotes
            ) {
                structure.addRow("      -");
                structure.addToLastRow(member);
            }
            structure.addRow("*נגד*");
            structure.addToLastRow(" - ");
            structure.addToLastRow("" + againstVotes.size());
            for (String member : againstVotes
            ) {
                structure.addRow("      -");
                structure.addToLastRow(member);
            }
            structure.addRow("*נמנע*");
            structure.addToLastRow(" - ");
            structure.addToLastRow("" + neutralVotes.size());
            for (String member : neutralVotes
            ) {
                structure.addRow("      -");
                structure.addToLastRow(member);

            }
        }

        if(law.isAnonymousVoting() && (law.getStatus() == Law.failed || law.getStatus() == Law.passed)) {
            structure.addRow("*בעד*");
            structure.addToLastRow(" - ");
            structure.addToLastRow("" + forVotes.size());
            structure.addRow("*נגד*");
            structure.addToLastRow(" - ");
            structure.addToLastRow("" + againstVotes.size());
            structure.addRow("*נמנע*");
            structure.addToLastRow(" - ");
            structure.addToLastRow("" + neutralVotes.size());
        }

        structure.addRow("");

        structure.addRow("*תיאור החוק:*");
        structure.addRow(law.getDescription());
        structure.addRow("");
        structure.addRow("תאריך הצעה: ");
        structure.addToLastRow(law.getDate().toString());
        String res = structure.getString();
        return List.of(new MessageToSend(res, askerNum));
    }

    public static String status(Law law) {
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
        return status;
    }


    public GetLawStatus(Law law, String askerNum) {
        this.law = law;
        this.askerNum = askerNum;
    }


}
