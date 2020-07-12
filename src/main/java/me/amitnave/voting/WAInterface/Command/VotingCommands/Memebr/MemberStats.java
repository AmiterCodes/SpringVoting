package me.amitnave.voting.WAInterface.Command.VotingCommands.Memebr;

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

public class MemberStats implements VotingCommand {
    private Member member;
    private String askerID;

    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }

    public MemberStats(Member member, String askerID) {
        this.member = member;
        this.askerID = askerID;
    }

    @Override
    public MessageToSend message() throws SQLException, ParseException {
        MessageStructure structure = new MessageStructure();
        structure.addToLastRow("סטטיסטיקה עבור ");
        structure.addToLastRow(member.getName());
        Connection conn = DBHelper.getConnection();
        // our SQL SELECT query.
        // if you only need a few columns, specify them by name instead of using "*"
        String query = "SELECT voting.law.status FROM voting.law WHERE voting.law.creator=" + member.getId() + " AND voting.law.anonymousCreator=FALSE;";
        // create the java statement
        Statement st = conn.createStatement();
        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(query);
        int passed = 0;
        int failed = 0;
        int inProcess = 0;
        int invalidated = 0;
        int canceled = 0;
        while (rs.next()) {
            if (rs.getInt("status") == Law.passed)
                passed++;
            if (rs.getInt("status") == Law.failed)
                failed++;
            if (rs.getInt("status") == Law.inProcess)
                inProcess++;
            if (rs.getInt("status") == Law.invalidated)
                invalidated++;
            if (rs.getInt("status") == Law.canceled)
                canceled++;
        }
        query = "SELECT voting.vote.vote FROM voting.vote INNER JOIN voting.law ON voting.vote.member=" + member.getId() + " AND voting.vote.law=voting.law.id AND voting.law.anonymousVoting=FALSE;";
        // create the java statement
        st.close();
        conn=DBHelper.getConnection();
        st = conn.createStatement();
        // execute the query, and get a java resultset
        rs = st.executeQuery(query);
        int forVote = 0;
        int againstVote = 0;
        int neutralVote = 0;
        while (rs.next()) {
            if (rs.getInt("vote") == Vote.FOR)
                forVote++;
            if (rs.getInt("vote") == Vote.AGAINST)
                againstVote++;
            if (rs.getInt("vote") == Vote.NEUTRAL)
                neutralVote++;
        }
        st.close();
        structure.addRow("סטטיסטיקת חוקים (לא כולל חוקים ממקור אנונימי)");
        structure.addRow("חוקים שהוצעו:");
        structure.addToLastRow(passed + failed + inProcess + invalidated + canceled + "");
        structure.addRow("עברו- ");
        structure.addToLastRow(passed + "");
        structure.addRow("לא עברו- ");
        structure.addToLastRow(failed + "");
        structure.addRow("בתהליך הצבעה- ");
        structure.addToLastRow(inProcess + "");
        structure.addRow("נפסלו על ידי הנשיא- ");
        structure.addToLastRow(invalidated + "");
        structure.addRow("בוטלו על ידי היוצר- ");
        structure.addToLastRow(canceled + "");
        structure.addRow("");
        structure.addRow("סטטיסטיקת הצבעות (לא כולל אנונימיות)");
        structure.addRow("כמות הצבעות כוללת: ");
        structure.addToLastRow(forVote + againstVote + neutralVote + "");
        structure.addRow("הצבעות בעד- ");
        structure.addToLastRow(forVote + "");
        structure.addRow("הצבעות נגד- ");
        structure.addToLastRow(againstVote + "");
        structure.addRow("הצבעות נמנע- ");
        structure.addToLastRow(neutralVote + "");
        return new MessageToSend(structure.getString(), askerID);
    }
}